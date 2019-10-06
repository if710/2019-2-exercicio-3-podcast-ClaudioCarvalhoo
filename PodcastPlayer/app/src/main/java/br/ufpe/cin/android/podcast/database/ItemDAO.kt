package br.ufpe.cin.android.podcast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ItemDAO {
    @Query("SELECT * FROM items")
    fun getAll(): List<ItemModel>

    @Query("SELECT * FROM items WHERE title = :title")
    fun getByTitle(title: String): ItemModel

    @Query("SELECT count(*) FROM items WHERE title = :title")
    fun existsByTitle(title: String): Long

    @Insert(onConflict = REPLACE)
    fun insert(itemModel: ItemModel)
}