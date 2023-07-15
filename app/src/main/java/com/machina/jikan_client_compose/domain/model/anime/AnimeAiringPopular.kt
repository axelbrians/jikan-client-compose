package com.machina.jikan_client_compose.domain.model.anime

data class AnimeAiringPopular(
	val malId: Int,
	val rank: Int,
	val title: String,
	val url: String,
	val imageUrl: String,
	val type: String,
	val episodesCount: Int?,
	val members: Int,
	val score: Double,
)
