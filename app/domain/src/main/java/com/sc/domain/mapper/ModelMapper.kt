package com.sc.domain.mapper

import com.sc.data.response.MLItemResponse
import com.sc.domain.model.MLItemModel


fun MLItemResponse.toModel() = MLItemModel(
    id = this.id,
    title = this.title,
    condition = this.condition,
    thumbnail = this.thumbnail,
    catalog_product_id  = this.catalog_product_id ?: "",
    listing_type_id  = this.listing_type_id,
    permalink  = this.permalink,
    buying_mode  = this.buying_mode,
    site_id  = this.site_id,
    category_id  = this.category_id,
    domain_id  = this.domain_id,
    currency_id = this.currency_id,
    price = this.price,
    original_price = this.original_price ?: 0f,
    sold_quantity = this.sold_quantity,
    available_quantity = this.available_quantity,
    accepts_mercadopago = this.accepts_mercadopago,
    tags = this.tags ?: listOf(),
)