package ru.pokhodai.projects.model.response.marvel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ComicsResponse(
    @SerialName("data") override val data: Data
): MarvelResponse() {
    @Serializable
    class Data(
        @SerialName("offset") override val offset: Int = 0,
        @SerialName("limit") override val limit: Int = 0,
        @SerialName("total") override val total: Int = 0,
        @SerialName("count") override val count: Int = 0,
        @SerialName("results") override val results: List<Result> = emptyList()
    ): MarvelData() {

        @Serializable
        class Result(
            @SerialName("id") override val id: Int = -1,
            @SerialName("title") override val title: String? = null,
            @SerialName("description") override val description: String? = null,
            @SerialName("modified") override val modified: String? = null,
            @SerialName("thumbnail") override val thumbnail: Thumbnail? = null
        ): MarvelResult() {

            @Serializable
            class Thumbnail(
                @SerialName("path") override val path: String? = null,
                @SerialName("extension") override val extension: String? = null
            ): MarvelThumbnail()
        }
    }
}