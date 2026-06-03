package com.muma.spotify

import com.muma.spotify.dto.SpotifyTrack

fun interface SpotifyTrackFilterStrategy {
    fun filter(tracks: List<SpotifyTrack>, title: String, artists: String): List<SpotifyTrack>
}
