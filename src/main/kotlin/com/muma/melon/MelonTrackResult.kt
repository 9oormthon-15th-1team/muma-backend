package com.muma.melon

import com.fasterxml.jackson.annotation.JsonProperty
import com.muma.spotify.dto.SpotifyTrack

data class MelonTrackResult(
    @field:JsonProperty("playlist_id") val playlistId: String,
    val position: Int,
    @field:JsonProperty("melon_song_id") val melonSongId: String,
    val title: String,
    @field:JsonProperty("artists_text") val artistsText: String,
    @field:JsonProperty("melon_artist_ids") val melonArtistIds: String,
    @field:JsonProperty("album_title") val albumTitle: String,
    @field:JsonProperty("melon_album_id") val melonAlbumId: String,
    @field:JsonProperty("melon_likes") val melonLikes: Int,
    @field:JsonProperty("melon_song_url") val melonSongUrl: String,
    val results: List<SpotifyTrack>,
)
