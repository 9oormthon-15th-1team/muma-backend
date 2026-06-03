package com.muma.spotify

import com.muma.spotify.dto.SpotifyTrack

fun interface SpotifySearchStrategy {
    fun search(title: String, artists: String): List<SpotifyTrack>
}
