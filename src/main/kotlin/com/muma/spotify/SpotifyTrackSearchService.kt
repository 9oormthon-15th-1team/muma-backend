package com.muma.spotify

import com.muma.spotify.dto.SpotifyTrack
import org.springframework.stereotype.Service

@Service
class SpotifyTrackSearchService(
    private val spotifySearchAdapter: SpotifySearchAdapter,
) {

    fun searchTracks(title: String, artists: String): List<SpotifyTrack> {
        return spotifySearchAdapter.searchTracks(title, artists)
    }
}
