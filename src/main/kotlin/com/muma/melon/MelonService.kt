package com.muma.melon

import com.muma.spotify.SpotifySearchAdapter
import com.muma.spotify.SpotifySearchStrategy
import com.muma.spotify.SpotifyTrackFilterStrategy
import com.muma.spotify.dto.SpotifyTrack
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class MelonService(
    private val spotifySearchAdapter: SpotifySearchAdapter,
) {

    private val searchStrategies: List<SpotifySearchStrategy> = listOf(
        SpotifySearchStrategy { title, artists -> spotifySearchAdapter.searchTracks(title, artists) },
        SpotifySearchStrategy { title, _ -> spotifySearchAdapter.searchTracks(title, "") },
        // 제목 끝의 괄호 부분을 제거하고 앞 부분만 검색 (예: "니가 나라면 (Feat. 유회승)" → "니가 나라면")
        SpotifySearchStrategy { title, _ ->
            val strippedTitle = title.replace(Regex("\\s*\\([^)]*\\)$"), "").trim()
            if (strippedTitle == title) emptyList()
            else spotifySearchAdapter.searchTracks(strippedTitle, "")
        },
        // ':' 뒷부분만 검색 (예: "Saint-Saens : Danse Macabre Op.40" → "Danse Macabre Op.40")
        SpotifySearchStrategy { title, _ ->
            val afterColon = title.substringAfter(":", "").trim()
            if (afterColon.isEmpty()) emptyList()
            else spotifySearchAdapter.searchTracks(afterColon, "")
        },
    )

    private val filterStrategies: List<SpotifyTrackFilterStrategy> = listOf(
        // title 과 artists 가 정확히 일치하는 track 이 있으면 첫 번째 일치 항목만 남긴다
        SpotifyTrackFilterStrategy { tracks, title, artists ->
            val exactMatch = tracks.firstOrNull { track ->
                track.name.equals(title, ignoreCase = true) &&
                    track.artists.joinToString(", ") { it.name }.equals(artists, ignoreCase = true)
            }
            if (exactMatch != null) listOf(exactMatch) else tracks
        },
    )

    fun preview(tracks: List<MelonTrackRequest>): List<MelonTrackResult> {
        return tracks.map { track ->
            val title = track.title.take(100)
            val artists = if (track.artistsText.length >= 100) {
                track.artistsText.split(",").take(2).joinToString(",")
            } else {
                track.artistsText
            }
            MelonTrackResult(
                playlistId = track.playlistId,
                position = track.position,
                melonSongId = track.melonSongId,
                title = title,
                artistsText = artists,
                melonArtistIds = track.melonArtistIds,
                albumTitle = track.albumTitle,
                melonAlbumId = track.melonAlbumId,
                melonLikes = track.melonLikes,
                melonSongUrl = track.melonSongUrl,
                results = searchWithFallback(title, artists).applyFilters(title, artists),
            )
        }
    }

    private fun searchWithFallback(title: String, artists: String): List<SpotifyTrack> {
        for (strategy in searchStrategies) {
            val results = strategy.search(title, artists)
            if (results.isNotEmpty()) return results
        }
        return emptyList()
    }

    private fun List<SpotifyTrack>.applyFilters(title: String, artists: String): List<SpotifyTrack> =
        filterStrategies.fold(this) { tracks, strategy -> strategy.filter(tracks, title, artists) }

    companion object : KLogging()
}
