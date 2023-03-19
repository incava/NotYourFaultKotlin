package com.incava.notyourfaultkotlin.data


import com.google.gson.annotations.SerializedName
import com.incava.notyourfaultkotlin.data.Body
import com.incava.notyourfaultkotlin.data.Header

data class ShelterDTO(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)