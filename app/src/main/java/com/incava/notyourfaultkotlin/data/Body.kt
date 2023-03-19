package com.incava.notyourfaultkotlin.data


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val items: Items?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("resultType")
    val resultType: String?,
    @SerializedName("totalCount")
    val totalCount: Int?
)