package com.musinsa.subject.model.price

class LowestPriceByCategoryResponse(
    var categoryLowestPrices: List<Category>? = null,
    var totalPrice: String? = null
) {
    data class Category(
        val categoryName: String,
        val brandName: String,
        val productPrice: String
    )
}
