package com.musinsa.subject.model.price

class MinMaxPriceBrandsByCategoryNameResponse(
    var categoryName: String? = null,
    var minPriceBrands: List<Brand>? = null,
    var maxPriceBrands: List<Brand>? = null
) {
    data class Brand(
        val brandName: String,
        val productPrice: String
    )
}
