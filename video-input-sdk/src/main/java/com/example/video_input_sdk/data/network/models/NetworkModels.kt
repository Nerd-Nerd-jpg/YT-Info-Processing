package com.example.ytinfoprocessing.data.network.models

import com.google.gson.annotations.SerializedName

//region response
data class YoutubeVideoDataResponse(
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("items") var items: ArrayList<Items> = arrayListOf(),
)

data class Items(
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("etag") var etag: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("snippet") var snippet: Snippet? = Snippet(),
)

data class Snippet(
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("channelId") var channelId: String? = null,
    @SerializedName("title") var title: String? = null,
//    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnails") var thumbnails: Thumbnails? = Thumbnails(),
    @SerializedName("channelTitle") var channelTitle: String? = null,
//    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
//    @SerializedName("categoryId") var categoryId: String? = null,
//    @SerializedName("liveBroadcastContent") var liveBroadcastContent: String? = null,
    @SerializedName("localized") var localized: Localized? = Localized(),
)

//region Thumbnails
data class Thumbnails(
    @SerializedName("default") var default: Default? = Default(),
    @SerializedName("medium") var medium: Medium? = Medium(),
)

data class Default(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
)

data class Medium(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
)
//endregion

data class Localized(
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
)
//endregion
