package com.muma.melon

import com.fasterxml.jackson.annotation.JsonProperty

data class MelonTrackRequest(
    @JsonProperty("playlist_id") val playlistId: String,
    val position: Int,
    @JsonProperty("melon_song_id") val melonSongId: String,
    val title: String,
    @JsonProperty("artists_text") val artistsText: String,
    @JsonProperty("melon_artist_ids") val melonArtistIds: String,
    @JsonProperty("album_title") val albumTitle: String,
    @JsonProperty("melon_album_id") val melonAlbumId: String,
    @JsonProperty("melon_likes") val melonLikes: Int,
    @JsonProperty("melon_song_url") val melonSongUrl: String,
)
