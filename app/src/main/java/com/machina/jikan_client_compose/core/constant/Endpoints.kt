package com.machina.jikan_client_compose.core.constant

import com.machina.jikan_client_compose.core.helper.DateHelper

object Endpoints {

    const val BASE_URL = "https://api.jikan.moe/v3/"

    const val ANIME_TOP = "/top/anime"
    const val ANIME_DETAILS = "/anime"
    const val ANIME_SEARCH = "/anime"
    const val ANIME_SCHEDULES = "/schedules"
    const val ANIME_CHARACTERS = "/characters"
    const val ANIME_RECOMMENDATIONS = "/recommendations"
    const val ANIME_GENRES = "genres/anime"

    const val MANGA_SEARCH = "/manga"
    const val MANGA_DETAILS = "/manga"

    const val HOST_V3 = "api.jikan.moe/v3"
    const val HOST_V4 = "api.jikan.moe/v4"

    fun getAnimeCharactersEndpoint(malId: Int): String {
        return "$ANIME_DETAILS/$malId$ANIME_CHARACTERS"
    }

    fun getAnimeRecommendationEndpoint(malId: Int): String {
        return "$ANIME_DETAILS/$malId$ANIME_RECOMMENDATIONS"
    }

    fun getMangaDetailsEndpoint(malId: Int): String {
        return "$MANGA_DETAILS/$malId"
    }

    fun getTodayScheduleAnimeEndpoints(): String {
        return ANIME_SCHEDULES + "/" + DateHelper.getTodayDayNameInString()
    }
}