package com.musinsa.subject.controller

import com.musinsa.subject.model.common.Single
import com.musinsa.subject.model.common.Success
import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.model.product.ProductResponse
import com.musinsa.subject.service.facade.ProductFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@Tag(name = "Product API", description = "상품 API")
@Validated
@RestController
@RequestMapping("/products")
class ProductController(
    private val facade: ProductFacade
) {

    @Operation(
        summary = "상품 생성",
        description = "상품을 생성하는 API"
    )
    @PostMapping
    fun createProduct(@Valid @RequestBody request: ProductRequest): ResponseEntity<Success> {
        val productId = facade.createProduct(request).productId
        val location = URI.create("/products/$productId")
        return ResponseEntity.created(location).body(Success())
    }

    @Operation(
        summary = "상품 조회",
        description = "상품 ID로 상품 정보를 조회하는 API"
    )
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getProduct(@Positive @PathVariable productId: Long): Single<ProductResponse> {
        return Single(facade.getProduct(productId))
    }

    @Operation(
        summary = "상품 정보 갱신",
        description = "상품 정보를 갱신하는 API"
    )
    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateProduct(
        @Positive @PathVariable productId: Long,
        @Valid @RequestBody request: ProductRequest
    ): Single<ProductResponse> {
        return Single(facade.updateProduct(productId, request))
    }

    @Operation(
        summary = "상품 삭제",
        description = "상품 정보를 삭제하는 API"
    )
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@Positive @PathVariable productId: Long) {
        facade.deleteProduct(productId)
    }
}
