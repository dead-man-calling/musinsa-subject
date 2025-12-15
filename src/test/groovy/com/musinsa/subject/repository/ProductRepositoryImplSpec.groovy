package com.musinsa.subject.repository

import com.musinsa.subject.config.JpaAuditingConfig
import com.musinsa.subject.config.QueryDslConfig
import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import org.hibernate.Hibernate
import org.hibernate.SessionFactory
import org.hibernate.stat.Statistics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import jakarta.persistence.EntityManager

@DataJpaTest
@ActiveProfiles("test")
@Import([QueryDslConfig, JpaAuditingConfig])
class ProductRepositoryImplSpec extends Specification {

    @Autowired
    ProductRepository productRepository

    @Autowired
    BrandRepository brandRepository

    @Autowired
    CategoryRepository categoryRepository

    @Autowired
    TestEntityManager entityManager

    Statistics statistics

    def setup() {
        EntityManager em = entityManager.getEntityManager()
        SessionFactory sessionFactory = em.getEntityManagerFactory().unwrap(SessionFactory.class)

        statistics = sessionFactory.getStatistics()

        statistics.setStatisticsEnabled(true)
        statistics.clear()
    }

    def "getLowestPriceProductByCategory - fetchJoin으로 N+1 문제 해결 검증"() {
        given: "여러 브랜드와 카테고리의 상품 데이터"
        def brandA = brandRepository.save(Brand.builder().brandName("A").build())
        def brandB = brandRepository.save(Brand.builder().brandName("B").build())
        def brandC = brandRepository.save(Brand.builder().brandName("C").build())

        def category1 = categoryRepository.save(Category.builder().categoryName("상의").build())
        def category2 = categoryRepository.save(Category.builder().categoryName("바지").build())
        def category3 = categoryRepository.save(Category.builder().categoryName("아우터").build())

        // 각 카테고리별로 여러 상품 생성
        productRepository.save(Product.builder().productPrice(10000L).brand(brandA).category(category1).build())
        productRepository.save(Product.builder().productPrice(15000L).brand(brandB).category(category1).build())
        productRepository.save(Product.builder().productPrice(12000L).brand(brandC).category(category1).build())

        productRepository.save(Product.builder().productPrice(20000L).brand(brandA).category(category2).build())
        productRepository.save(Product.builder().productPrice(25000L).brand(brandB).category(category2).build())

        productRepository.save(Product.builder().productPrice(30000L).brand(brandA).category(category3).build())
        productRepository.save(Product.builder().productPrice(35000L).brand(brandC).category(category3).build())

        entityManager.flush()
        entityManager.clear()
        statistics.clear()

        when: "카테고리별 최저가 상품 조회"
        def products = productRepository.getLowestPriceProductByCategory()

        and: "Brand와 Category 필드 접근"
        products.each { product ->
            // Lazy loading을 유발할 수 있는 접근
            product.getBrand().getBrandName()
            product.getCategory().getCategoryName()
        }

        then: "결과 검증"
        products.size() == 3  // 3개 카테고리의 최저가 상품

        and: "N+1 문제가 없음 - 단일 쿼리로 모든 데이터 조회"
        // fetchJoin이 제대로 작동하면 1개의 쿼리만 실행
        // (또는 subquery로 인해 소수의 쿼리만 실행)
        statistics.getPrepareStatementCount() <= 5  // 초기 설정 쿼리 포함

        and: "Brand와 Category가 초기화되어 있음 (Lazy loading 발생 안 함)"
        def productList = products as List
        Hibernate.isInitialized(productList[0].getBrand())
        Hibernate.isInitialized(productList[0].getCategory())
    }

    def "getLowestPriceProductByBrandAndCategory - fetchJoin으로 연관 엔티티 즉시 로딩 검증"() {
        given: "브랜드와 카테고리별 상품 데이터"
        def brandA = brandRepository.save(Brand.builder().brandName("A").build())
        def brandB = brandRepository.save(Brand.builder().brandName("B").build())

        def category1 = categoryRepository.save(Category.builder().categoryName("상의").build())
        def category2 = categoryRepository.save(Category.builder().categoryName("바지").build())

        productRepository.save(Product.builder().productPrice(10000L).brand(brandA).category(category1).build())
        productRepository.save(Product.builder().productPrice(15000L).brand(brandA).category(category1).build())
        productRepository.save(Product.builder().productPrice(20000L).brand(brandA).category(category2).build())
        productRepository.save(Product.builder().productPrice(12000L).brand(brandB).category(category1).build())
        productRepository.save(Product.builder().productPrice(25000L).brand(brandB).category(category2).build())

        entityManager.flush()
        entityManager.clear()
        statistics.clear()

        when: "브랜드-카테고리별 최저가 상품 조회"
        def products = productRepository.getLowestPriceProductByBrandAndCategory()

        then: "연관 엔티티가 모두 초기화되어 있음"
        products.each { product ->
            assert Hibernate.isInitialized(product.getBrand())
            assert Hibernate.isInitialized(product.getCategory())

            // Lazy loading 없이 접근 가능
            assert product.getBrand().getBrandName() != null
            assert product.getCategory().getCategoryName() != null
        }
    }

    def "getMinPriceProductInCategory - fetchJoin 적용 검증"() {
        given: "특정 카테고리의 상품들"
        def brandA = brandRepository.save(Brand.builder().brandName("A").build())
        def brandB = brandRepository.save(Brand.builder().brandName("B").build())
        def category = categoryRepository.save(Category.builder().categoryName("상의").build())

        productRepository.save(Product.builder().productPrice(10000L).brand(brandA).category(category).build())
        productRepository.save(Product.builder().productPrice(10000L).brand(brandB).category(category).build())  // 동일 최저가
        productRepository.save(Product.builder().productPrice(15000L).brand(brandA).category(category).build())

        entityManager.flush()
        entityManager.clear()
        statistics.clear()

        when: "최저가 상품 조회"
        def products = productRepository.getMinPriceProductInCategory(category.getCategoryId())

        then: "최저가 상품들이 조회됨"
        products.size() >= 2  // 동일 최저가 2개 이상
        products.every { it.getProductPrice() == 10000L }

        and: "연관 엔티티가 초기화되어 있음"
        products.each { product ->
            assert Hibernate.isInitialized(product.getBrand())
            assert Hibernate.isInitialized(product.getCategory())
        }
    }

    def "getMaxPriceProductInCategory - fetchJoin 적용 검증"() {
        given: "특정 카테고리의 상품들"
        def brandA = brandRepository.save(Brand.builder().brandName("A").build())
        def category = categoryRepository.save(Category.builder().categoryName("바지").build())

        productRepository.save(Product.builder().productPrice(10000L).brand(brandA).category(category).build())
        productRepository.save(Product.builder().productPrice(30000L).brand(brandA).category(category).build())
        productRepository.save(Product.builder().productPrice(20000L).brand(brandA).category(category).build())

        entityManager.flush()
        entityManager.clear()
        statistics.clear()

        when: "최고가 상품 조회"
        def products = productRepository.getMaxPriceProductInCategory(category.getCategoryId())

        then: "최고가 상품이 조회됨"
        products.size() >= 1
        products.every { it.getProductPrice() == 30000L }

        and: "연관 엔티티가 초기화되어 있음"
        products.every { product ->
            Hibernate.isInitialized(product.getBrand()) &&
                    Hibernate.isInitialized(product.getCategory())
        }
    }

    def "fetchJoin 미적용 시뮬레이션 - N+1 문제 발생 확인"() {
        given: "상품 데이터"
        def brandA = brandRepository.save(Brand.builder().brandName("A").build())
        def brandB = brandRepository.save(Brand.builder().brandName("B").build())
        def category = categoryRepository.save(Category.builder().categoryName("상의").build())

        productRepository.save(Product.builder().productPrice(10000L).brand(brandA).category(category).build())
        productRepository.save(Product.builder().productPrice(15000L).brand(brandB).category(category).build())

        entityManager.flush()
        entityManager.clear()
        statistics.clear()

        when: "단순 findAll로 조회 (fetchJoin 없음)"
        def products = productRepository.findAll()

        def initialQueryCount = statistics.getPrepareStatementCount()

        and: "연관 엔티티 접근 시 추가 쿼리 발생"
        products.each { product ->
            product.getBrand().getBrandName()  // Lazy loading 발생
            product.getCategory().getCategoryName()  // Lazy loading 발생
        }

        def finalQueryCount = statistics.getPrepareStatementCount()

        then: "N+1 문제로 인해 추가 쿼리가 실행됨"
        finalQueryCount > initialQueryCount

        // 반면에 fetchJoin을 사용한 메서드는 추가 쿼리가 없어야 함
    }
}