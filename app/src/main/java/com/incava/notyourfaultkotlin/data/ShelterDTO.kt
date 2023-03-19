package com.incava.notyourfaultkotlin.data


import com.google.gson.annotations.SerializedName

data class ShelterDTO(
    @SerializedName("response")
    val response: Response?
)