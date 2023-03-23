package com.incava.notyourfaultkotlin.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Item(
    @PrimaryKey
    @SerializedName("fcltNm")
    val fcltNm: String,
    @SerializedName("cpctCnt")
    val cpctCnt: String?,
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
    val expsrYn: String?,
    @SerializedName("fcltTypeNm")
    val fcltTypeNm: String?,
    @SerializedName("fxno")
    val fxno: String?,
    @SerializedName("hmpgAddr")
    val hmpgAddr: String?,
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("lot")
    val lot: String?,
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