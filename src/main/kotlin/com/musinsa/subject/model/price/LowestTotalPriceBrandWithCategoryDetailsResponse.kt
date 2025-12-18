package com.musinsa.subject.model.price

class LowestTotalPriceBrandWithCategoryDetailsResponse(
    var lowestPrice: LowestPrice? = null
) {
    data class CategoryDetail(
        val categoryName: String,
        val productPrice: String
    )

    class LowestPrice(
        var brandName: String? = null,
        var categories: List<CategoryDetail>? = null,
        var totalPrice: String? = null
    )
}
