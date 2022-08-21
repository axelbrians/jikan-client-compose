package com.machina.jikan_client_compose.presentation.content_search_screen.data.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class ContentRating(
  val name: String,
  val description: String,
  val isChecked: Boolean = false
) {
  companion object {
//  rating: g pg pg13 r17 r rx
//  g all ages
//  pg children
//  pg13 teens older 13
//  r17 violence
//  r mild nudity
//  rx hentai
    fun getContentRatingList(): List<ContentRating> {
      return listOf(
        ContentRating("G", "All ages"),
        ContentRating("PG", "Children"),
        ContentRating("PG-13", "Teens 13 or older"),
        ContentRating("R-17+", "Violence & profanity"),
        ContentRating("R+", "Mild nudity"),
        ContentRating("Rx", "Hentai")
      )
    }
  }
}

@Composable
fun rememberFilterContentRatingList(): MutableState<List<ContentRating>> {
  return remember { mutableStateOf(ContentRating.getContentRatingList()) }
}