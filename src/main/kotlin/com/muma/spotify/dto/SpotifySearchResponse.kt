package com.muma.spotify.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifySearchResponse(
    val tracks: SpotifyPaging<SpotifyTrack>?,
    val albums: SpotifyPaging<SpotifyAlbum>?,
    val artists: SpotifyPaging<SpotifyArtist>?,
    val playlists: SpotifyPaging<SpotifyPlaylist>?,
)

data class SpotifyPaging<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
)

data class SpotifyTrack(
    val id: String,
    val name: String,
    @field:JsonProperty("duration_ms") val durationMs: Int,
    val explicit: Boolean,
    val popularity: Int?,
    val artists: List<SpotifyArtistSimple>,
    val album: SpotifyAlbumSimple,
    @field:JsonProperty("external_urls") val externalUrls: SpotifyExternalUrls,
    @field:JsonProperty("preview_url") val previewUrl: String?,
)

data class SpotifyAlbum(
    val id: String,
    val name: String,
    @field:JsonProperty("album_type") val albumType: String,
    @field:JsonProperty("release_date") val releaseDate: String,
    @field:JsonProperty("total_tracks") val totalTracks: Int,
    val artists: List<SpotifyArtistSimple>,
    val images: List<SpotifyImage>,
    @JsonProperty("external_urls") val externalUrls: SpotifyExternalUrls,
)

data class SpotifyArtist(
    val id: String,
    val name: String,
    val genres: List<String>,
    val popularity: Int?,
    val images: List<SpotifyImage>,
    val followers: SpotifyFollowers?,
    @field:JsonProperty("external_urls") val externalUrls: SpotifyExternalUrls,
)

data class SpotifyPlaylist(
    val id: String,
    val name: String,
    val description: String?,
    val public: Boolean?,
    val owner: SpotifyUser,
    val images: List<SpotifyImage>,
    val tracks: SpotifyPlaylistTracksRef,
    @field:JsonProperty("external_urls") val externalUrls: SpotifyExternalUrls,
)

data class SpotifyAlbumSimple(
    val id: String,
    val name: String,
    @field:JsonProperty("album_type") val albumType: String,
    @field:JsonProperty("release_date") val releaseDate: String,
    val images: List<SpotifyImage>,
)

data class SpotifyArtistSimple(
    val id: String,
    val name: String,
    @field:JsonProperty("external_urls") val externalUrls: SpotifyExternalUrls,
)

data class SpotifyImage(
    val url: String,
    val height: Int?,
    val width: Int?,
)

data class SpotifyExternalUrls(
    val spotify: String,
)

data class SpotifyFollowers(
    val total: Int,
)

data class SpotifyUser(
    val id: String,
    @field:JsonProperty("display_name") val displayName: String?,
)

data class SpotifyPlaylistTracksRef(
    val href: String,
    val total: Int,
)

data class SpotifyCreatePlaylistRequest(
    val name: String,
    val public: Boolean? = null,
    val collaborative: Boolean? = null,
    val description: String? = null,
)

data class SpotifyAddPlaylistItemsRequest(
    val uris: List<String>,
    val position: Int? = null,
)

data class SpotifySnapshotResponse(
    @field:JsonProperty("snapshot_id") val snapshotId: String,
)

data class SpotifyPlaylistItem(
    @field:JsonProperty("added_at") val addedAt: String?,
    @field:JsonProperty("added_by") val addedBy: SpotifyUser?,
    val track: SpotifyTrack?,
)
