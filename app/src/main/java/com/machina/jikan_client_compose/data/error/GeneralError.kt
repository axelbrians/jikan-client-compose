package com.machina.jikan_client_compose.data.error

import com.google.gson.annotations.SerializedName

data class GeneralError(
    @SerializedName("message")
    val message: String
)