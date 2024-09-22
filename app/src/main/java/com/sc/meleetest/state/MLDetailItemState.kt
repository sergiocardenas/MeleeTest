package com.sc.meleetest.state

data class MLDetailItemState(
    val id: String = "",
    val title : String = "",
    val condition: String = "",
    val thumbnail: String = "",
    val currency_id: String = "",
    val price: Float = 0f,
    val original_price: Float = 0f,
    val sold_quantity: Int = 0,
    val available_quantity: Int = 0,
    val tags: List<String> = listOf(),
){
}
