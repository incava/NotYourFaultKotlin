package com.incava.notyourfaultkotlin.data


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("cpctCnt")
    val cpctCnt: Int?,
    @SerializedName("crtrYmd")
    val crtrYmd: String?,
    @SerializedName("ctpvNm")
    val ctpvNm: String?,
    @SerializedName("emlAddr")
    val emlAddr: String?,
    @SerializedName("etrPrdCn")
    val etrPrdCn: String?,
    @SerializedName("etrTrgtCn")
    val etrTrgtCn: String?,
    @SerializedName("expsrYn")
    val expsrYn: Boolean?,
    @SerializedName("fcltNm")
    val fcltNm: String?,
    @SerializedName("fcltTypeNm")
    val fcltTypeNm: String?,
    @SerializedName("fxno")
    val fxno: String?,
    @SerializedName("hmpgAddr")
    val hmpgAddr: String?,
    @SerializedName("lat")
    val lat: Int?,
    @SerializedName("lot")
    val lot: Int?,
    @SerializedName("lotnoAddr")
    val lotnoAddr: String?,
    @SerializedName("nrbBusStnNm")
    val nrbBusStnNm: String?,
    @SerializedName("nrbSbwNm")
    val nrbSbwNm: String?,
    @SerializedName("operModeCn")
    val operModeCn: String?,
    @SerializedName("rmrkCn")
    val rmrkCn: String?,
    @SerializedName("roadNmAddr")
    val roadNmAddr: String?,
    @SerializedName("rprsTelno")
    val rprsTelno: String?,
    @SerializedName("sggNm")
    val sggNm: String?
)