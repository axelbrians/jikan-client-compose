package com.machina.jikan_client_compose.presentation.content_search_screen.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalDataModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularLoading
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape

@Composable
fun ItemAnimeSearch(
	modifier: Modifier = Modifier,
	data: AnimeHorizontalDataModel,
	onItemClick: (Int, ContentType) -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onItemClick(data.malId, ContentType.Anime) }
			.padding(horizontal = 12.dp, vertical = 6.dp)
	) {
		SubcomposeAsyncImage(
			modifier = Modifier
				.width(120.dp)
				.height(160.dp)
				.fillMaxSize()
				.clip(MyShape.Rounded12),
			model = data.imageUrl,
			contentDescription = "Content thumbnail",
			contentScale = ContentScale.Crop,
			loading = {
				CenterCircularLoading(
					strokeWidth = 2.dp,
					size = 20.dp,
					color = MyColor.Yellow500
				)
			}
		)

		Column(
			modifier = Modifier
				.weight(1f)
				.height(160.dp)
				.padding(8.dp, 0.dp, 0.dp, 4.dp),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Column {
				Text(
					text = data.title,
					style = TextStyle(
						color = MyColor.OnDarkSurface,
						fontSize = 14.sp,
						fontWeight = FontWeight.Bold
					),
					modifier = Modifier.padding(top = 2.dp)
				)

				Text(
					text = data.synopsis,
					maxLines = 4,
					overflow = TextOverflow.Ellipsis,
					style = TextStyle(
						color = MyColor.OnDarkSurface,
						fontSize = 12.sp,
						fontWeight = FontWeight.Normal
					),
					modifier = Modifier
						.padding(bottom = 3.dp)
				)
			}

			Column {
				Text(
					text = "${data.score}",
					style = TextStyle(
						color = MyColor.OnDarkSurface,
						fontSize = 13.sp,
						fontWeight = FontWeight.Normal
					),
				)
				val episodes = if (data.episodesCount == 0) {
					"airing"
				} else {
					"${data.episodesCount} episodes"
				}
				Text(
					text = episodes,
					style = TextStyle(
						color = MyColor.OnDarkSurface,
						fontSize = 13.sp,
						fontWeight = FontWeight.Normal
					),
					modifier = Modifier.padding(bottom = 2.dp)
				)
			}
		}
	}
}

@Preview(widthDp = 340)
@Composable
fun Preview_ItemAnimeSearch() {
	ItemAnimeSearch(
		modifier = Modifier,
		data = AnimeHorizontalDataModel(
			malId = 21,
			title = "One Piece",
			url = "https://myanimelist.net/anime/21/One_Piece",
			imageUrl = "https://cdn.myanimelist.net/images/anime/6/73245l.jpg",
			synopsis = """Gol D. Roger was known as the "Pirate King," the strongest and most infamous being to have sailed the Grand SegmentType.Line. The capture and execution of Roger by the World Government brought a change throughout the world. His last words before his death revealed the existence of the greatest treasure in the world, One Piece. It was this revelation that brought about the Grand Age of Pirates, men who dreamed of finding One Piece—which promises an unlimited amount of riches and fame—and quite possibly the pinnacle of glory and the title of the Pirate King. Enter Monkey D. Luffy, a 17-year-old boy who defies your standard definition of a pirate. Rather than the popular persona of a wicked, hardened, toothless pirate ransacking villages for fun, Luffy's reason for being a pirate is one of pure wonder: the thought of an exciting adventure that leads him to intriguing people and ultimately, the promised treasure. Following in the footsteps of his childhood hero, Luffy and his crew travel across the Grand Line, experiencing crazy adventures, unveiling dark mysteries and battling strong enemies, all in order to reach the most coveted of all fortunes—One Piece. [Written by MAL Rewrite]""",
			score = 8.67,
			episodesCount = 0
		),
		onItemClick = { _, _ -> }
	)
}