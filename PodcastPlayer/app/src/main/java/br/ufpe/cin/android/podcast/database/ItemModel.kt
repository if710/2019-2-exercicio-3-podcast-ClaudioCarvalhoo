package br.ufpe.cin.android.podcast.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemModel(@PrimaryKey @ColumnInfo(name = "title") var title: String,
                     @ColumnInfo(name = "link") var link: String,
                     @ColumnInfo(name = "pub_date") var pubDate: String,
                     @ColumnInfo(name = "description") var description: String,
                     @ColumnInfo(name = "download_link") var downloadLink: String

){
    constructor():this("", "", "", "", "")
}