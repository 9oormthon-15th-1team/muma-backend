package com.muma.spotify

import com.muma.spotify.client.SpotifyClient
import org.springframework.stereotype.Service

@Service
class SpotifyTrackSearchService(
    private val spotifyClient: SpotifyClient,
) {

    fun searchTrackIds(title: String, artists: String): List<String> {
        val response = spotifyClient.search(
            query = "track:$title artist:$artists",
            type = "track",
        )
        return response.tracks?.items?.map { it.id } ?: emptyList()
    }
}
