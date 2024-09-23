package com.sc.meleetest.mapper

import com.sc.domain.model.MLItemModel
import com.sc.meleetest.state.MLDetailItemState
import com.sc.meleetest.state.MLSearchItemState

fun MLItemModel.toListState() = MLSearchItemState(
    id = this.id,
    title = this.title,
    thumbnail = this.thumbnail,
    currency_id = this.currency_id,
    price = this.price,
    original_price = this.original_price,
)

fun MLItemModel.toDetailState() = MLDetailItemState(
    id = this.id,
    title = this.title,
    condition = this.condition,
    thumbnail = this.thumbnail,
    currency_id = this.currency_id,
    price = this.price,
    original_price = this.original_price,
    sold_quantity = this.sold_quantity,
    available_quantity = this.available_quantity,
    tags = this.tags,
)