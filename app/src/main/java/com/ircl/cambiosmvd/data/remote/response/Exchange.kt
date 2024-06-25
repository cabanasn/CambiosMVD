package com.ircl.cambiosmvd.data.remote.response


import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("website")
    val website: String? = null
)