package com.musinsa.subject.controller;

import com.musinsa.subject.model.brand.BrandRequest;
import com.musinsa.subject.model.brand.BrandResponse;
import com.musinsa.subject.model.common.Single;
import com.musinsa.subject.model.common.Success;
import com.musinsa.subject.service.facade.BrandFacade;
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

@Tag(name = "Brand API", description = "브랜드 API")
@Validated
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandFacade facade;

    @Operation(
            summary = "브랜드 생성",
            description = "브랜드를 생성하는 API"
    )
    @PostMapping
    public ResponseEntity<Success> createBrand(@Valid @RequestBody BrandRequest request) {
        Long brandId = facade.createBrand(request).getBrandId();

        URI location = URI.create("/brands/" + brandId);

        return ResponseEntity.created(location).body(new Success());
    }

    @Operation(
            summary = "브랜드 조회",
            description = "브랜드 ID로 브랜드 정보를 조회하는 API"
    )
    @GetMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public Single<BrandResponse> findBrand(@Positive @PathVariable long brandId) {
        return new Single<>(facade.getBrand(brandId));
    }

    @Operation(
            summary = "브랜드 정보 갱신",
            description = "브랜드 정보를 갱신하는 API"
    )
    @PatchMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public Single<BrandResponse> updateBrand(@Positive @PathVariable long brandId, @Valid @RequestBody BrandRequest request) {
        return new Single<>(facade.updateBrand(brandId, request));
    }

    @Operation(
            summary = "브랜드 삭제",
            description = "브랜드를 삭제하는 API"
    )
    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@Positive @PathVariable long brandId) {
        facade.deleteBrand(brandId);
    }

}
