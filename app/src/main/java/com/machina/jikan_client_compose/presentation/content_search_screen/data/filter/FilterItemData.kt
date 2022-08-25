package com.machina.jikan_client_compose.presentation.content_search_screen.data.filter

data class FilterItemData(
  val key: String,
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
    fun getContentRatingList(): List<FilterItemData> {
      return listOf(
        FilterItemData("g", "All ages", "G"),
        FilterItemData("pg", "Children", "PG"),
        FilterItemData("pg13", "Teens 13 or older", "PG - 13"),
        FilterItemData("r17", "Violence & profanity", "R - 17+"),
        FilterItemData("r", "Mild nudity", "R+"),
        FilterItemData("rx", "Hentai", "Rx")
      )
    }
  }
}