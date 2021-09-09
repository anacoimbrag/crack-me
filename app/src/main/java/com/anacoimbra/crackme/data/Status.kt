package com.anacoimbra.crackme.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Status(
    @SerializedName("sentCount")
    val sentCount: Int?,
    @SerializedName("verified")
    val verified: Any?
)