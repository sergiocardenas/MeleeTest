package com.sc.data.response

data class MLItemResponse(
    val id: String,
    val title : String,
    val condition: String,
    val thumbnail: String,
    val catalog_product_id: String?,
    val listing_type_id: String,
    val permalink: String,
    val buying_mode: String,
    val site_id: String,
    val category_id: String,
    val domain_id: String,
    val currency_id: String,
    val price: Float,
    val original_price: Float?,
    val sold_quantity: Int,
    val available_quantity: Int,
    val accepts_mercadopago: Boolean,
    val tags: List<String>?,
)
