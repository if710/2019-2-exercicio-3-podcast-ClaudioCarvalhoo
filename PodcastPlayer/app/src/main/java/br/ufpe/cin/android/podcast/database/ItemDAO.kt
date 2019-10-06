package br.ufpe.cin.android.podcast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ItemDAO {
    @Query("SELECT * FROM items")
    fun getAll(): List<ItemModel>

    @Insert(onConflict = REPLACE)
    fun insert(itemModel: ItemModel)
}