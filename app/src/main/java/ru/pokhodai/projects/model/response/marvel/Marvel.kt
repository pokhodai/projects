package ru.pokhodai.projects.model.response.marvel

abstract class MarvelResponse {
    abstract val data: MarvelData
}

abstract class MarvelData {
    abstract val offset: Int
    abstract val limit: Int
    abstract val total: Int
    abstract val count: Int
    abstract val results: List<MarvelResult>
}

abstract class MarvelResult {
    abstract val id: Int?
    abstract val title: String?
    abstract val description: String?
    abstract val modified: String?
    abstract val thumbnail: MarvelThumbnail?
}

abstract class MarvelThumbnail {
    abstract val path: String?
    abstract val extension: String?
}