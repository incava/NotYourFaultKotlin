package com.incava.notyourfaultkotlin.data


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>?
)