package com.muma.melon

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "멜론 곡 정보")
data class MelonTrackRequest(
    @Schema(description = "멜론 플레이리스트 ID", example = "446121958")
    @JsonProperty("playlist_id") val playlistId: String,

    @Schema(description = "플레이리스트 내 순서", example = "1")
    val position: Int,

    @Schema(description = "멜론 곡 ID", example = "1644933")
    @JsonProperty("melon_song_id") val melonSongId: String,

    @Schema(description = "곡 제목", example = "언젠가 이곳이")
    val title: String,

    @Schema(description = "아티스트명", example = "이수")
    @JsonProperty("artists_text") val artistsText: String,

    @Schema(description = "멜론 아티스트 ID", example = "261143")
    @JsonProperty("melon_artist_ids") val melonArtistIds: String,

    @Schema(description = "앨범 제목", example = "뮤지컬 대장금 OST '언젠가 이곳이'")
    @JsonProperty("album_title") val albumTitle: String,

    @Schema(description = "멜론 앨범 ID", example = "352036")
    @JsonProperty("melon_album_id") val melonAlbumId: String,

    @Schema(description = "멜론 좋아요 수", example = "36")
    @JsonProperty("melon_likes") val melonLikes: Int,

    @Schema(description = "멜론 곡 URL", example = "https://www.melon.com/song/detail.htm?songId=1644933")
    @JsonProperty("melon_song_url") val melonSongUrl: String,
)
