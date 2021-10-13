package com.machina.jikan_client_compose.core.error

import com.google.gson.annotations.SerializedName

data class GeneralError(
    @SerializedName("message")
    val message: String
)