package com.anacoimbra.crackme.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Fact(
    @SerializedName("deleted")
    val deleted: Boolean?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("status")
    val status: Status?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("_v")
    val v: Int?
)