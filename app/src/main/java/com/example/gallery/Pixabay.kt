package com.example.gallery

import com.google.gson.annotations.SerializedName

data class Pixabay(
    val totalhits:Int,
    val hits:Array<PhotoItem>,
    val total:Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixabay

        if (totalhits != other.totalhits) return false
        if (!hits.contentEquals(other.hits)) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalhits
        result = 31 * result + hits.contentHashCode()
        result = 31 * result + total
        return result
    }
}

data class PhotoItem(
    @SerializedName("id") val photoID:Int,
    @SerializedName("webformatURL")val previewUrl:String, //json中的标注,把原来的名称作为序列化的名称，把变量名称改为符合语法的名称
    @SerializedName("largeImageURL")val fullURL:String
)