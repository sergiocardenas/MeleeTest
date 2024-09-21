package com.sc.domain.model

data class MLItemModel(
    val id: String = "",
    val title : String = "",
    val condition: String = "",
    val thumbnail: String = "",
    val catalog_product_id: String = "",
    val listing_type_id: String = "",
    val permalink: String = "",
    val buying_mode: String = "",
    val site_id: String = "",
    val category_id: String = "",
    val domain_id: String = "",
    val currency_id: String = "",
    val price: Float = 0f,
    val original_price: Float = 0f,
    val sold_quantity: Int = 0,
    val available_quantity: Int = 0,
    val accepts_mercadopago: Boolean = false,
    val tags: List<String> = listOf(),
) {
}
