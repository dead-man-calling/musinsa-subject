package com.musinsa.subject.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable

@Entity
@Table(
    name = "product",
    indexes = [
        Index(name = "idx_product_brand", columnList = "brand_id"),
        Index(name = "idx_product_category", columnList = "category_id")
    ]
)
@DynamicInsert
@DynamicUpdate
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var productId: Long? = null,

    @Column(name = "product_price", nullable = false)
    var productPrice: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: Brand,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category
) : BaseTimeEntity(), Serializable
