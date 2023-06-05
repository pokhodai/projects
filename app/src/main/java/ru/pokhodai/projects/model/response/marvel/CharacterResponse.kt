package ru.pokhodai.projects.model.response.marvel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterResponse(
    @SerialName("data") val data: Data
) {
    @Serializable
    class Data(
        @SerialName("offset") val offset: Int = 0,
        @SerialName("limit") val limit: Int = 0,
        @SerialName("total") val total: Int = 0,
        @SerialName("count") val count: Int = 0,
        @SerialName("results") val results: List<Result> = emptyList()
    ) {
        @Serializable
        class Result(
          @SerialName("id") val id: Int = -1,
          @SerialName("name") val name: String? = null,
          @SerialName("description") val description: String? = null,
          @SerialName("modified") val modified: String? = null,
          @SerialName("thumbnail") val thumbnail: Thumbnail? = null
        ) {
            @Serializable
            class Thumbnail(
                @SerialName("path") val path: String? = null,
                @SerialName("extension") val extension: String? = null
            )
        }
    }
}

//data class CharacterResponse(
//    @SerializedName("data") val data: CharacterDataResponse
//) {
//    data class CharacterDataResponse(
//        @SerializedName("offset") val offset: Int = 0,
//        @SerializedName("limit") val limit: Int = 0,
//        @SerializedName("total") val total: Int = 0,
//        @SerializedName("count") val count: Int = 0,
//        @SerializedName("results") val results: List<CharacterResultResponse> = emptyList()
//    ) {
//        data class CharacterResultResponse(
//            @SerializedName("id") val id: Int = 0,
//            @SerializedName("name") val name: String = "",
//            @SerializedName("description") val description: String = "",
//            @SerializedName("modified") val modified: String = "",
//            @SerializedName("thumbnail") val thumbnail: CharacterThumbnailResponse
//        ) {
//            data class CharacterThumbnailResponse(
//                @SerializedName("path") val path: String = "",
//                @SerializedName("extension") val extension: String = ""
//            )
//        }
//    }
//}