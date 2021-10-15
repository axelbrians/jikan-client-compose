package com.machina.jikan_client_compose.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.machina.jikan_client_compose.domain.model.ContentSearch

data class ContentSearchResponse(
    @SerializedName("request_hash")
    val requestHash: String,

    @SerializedName("request_cached")
    val requestCached: Boolean,

    @SerializedName("request_cache_expiry")
    val requestCacheExpiry: Int,

    @SerializedName("last_page")
    val lastPage: Int,

    @SerializedName("results")
    val results: List<ContentSearchDto>
)