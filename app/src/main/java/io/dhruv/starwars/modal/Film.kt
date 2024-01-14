package io.dhruv.starwars.modal

data class Film(
    val director: String,
    val episode_id: Int,
    val opening_crawl: String,
    val producer: String,
    val release_date: String,
    val title: String
)