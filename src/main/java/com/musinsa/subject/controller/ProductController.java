package com.musinsa.subject.controller;

import com.musinsa.subject.model.common.Single;
import com.musinsa.subject.model.common.Success;
import com.musinsa.subject.model.product.ProductRequest;
import com.musinsa.subject.model.product.ProductResponse;
import com.musinsa.subject.service.facade.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Product API", description = "상품 API")
@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade facade;

    @Operation(
            summary = "상품 생성",
            description = "상품을 생성하는 API"
    )
    @PostMapping
    public ResponseEntity<Success> createProduct(@Valid @RequestBody ProductRequest request) {
        Long productId = facade.createProduct(request).getProductId();

        URI location = URI.create("/products/" + productId);

        return ResponseEntity.created(location).body(new Success());
    }

    @Operation(
            summary = "상품 조회",
            description = "상품 ID로 상품 정보를 조회하는 API"
    )
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Single<ProductResponse> getProduct(@Positive @PathVariable long productId) {
        return new Single<>(facade.getProduct(productId));
    }

    @Operation(
            summary = "상품 정보 갱신",
            description = "상품 정보를 갱신하는 API"
    )
    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Single<ProductResponse> updateProduct(@Positive @PathVariable long productId, @Valid @RequestBody ProductRequest request) {
        return new Single<>(facade.updateProduct(productId, request));
    }

    @Operation(
            summary = "상품 삭제",
            description = "상품 정보를 삭제하는 API"
    )
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@Positive @PathVariable long productId) {
        facade.deleteProduct(productId);
    }

}
