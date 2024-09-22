package com.sc.meleetest.mock

import com.sc.domain.model.MLItemModel
import com.sc.meleetest.state.MLDetailItemState
import com.sc.meleetest.state.MLSearchItemState

fun getTestItem() = MLItemModel(
    id = "1",
    title = "Motorola",
    condition = "new",
    thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
    currency_id = "USD",
    price = 10.0f,
    original_price = 10.0f,
    sold_quantity = 1,
    available_quantity = 0,
    tags = listOf("phone"),
)

fun getTestSearchItem() = MLSearchItemState(
    id = "1",
    title = "Motorola",
    thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
    currency_id = "USD",
    price = 10.0f,
    original_price = 10.0f,
)

fun getTestEmptyIDSearchItem() = MLSearchItemState(
    id = "",
    title = "Motorola",
    thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
    currency_id = "USD",
    price = 10.0f,
    original_price = 10.0f,
)

fun getTestDetailItem() = MLDetailItemState(
    id = "1",
    title = "Motorola",
    condition = "new",
    thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
    currency_id = "USD",
    price = 10.0f,
    original_price = 10.0f,
    sold_quantity = 1,
    available_quantity = 0,
    tags = listOf("phone"),
)