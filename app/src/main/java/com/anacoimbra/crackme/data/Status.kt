package com.anacoimbra.crackme.data


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("sentCount")
    val sentCount: Int?,
    @SerializedName("verified")
    val verified: Any?
)