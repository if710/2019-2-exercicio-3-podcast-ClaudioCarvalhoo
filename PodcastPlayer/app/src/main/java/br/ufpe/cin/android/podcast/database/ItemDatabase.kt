package br.ufpe.cin.android.podcast.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room.databaseBuilder as databaseBuilder

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

    companion object {
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase? {
            if (INSTANCE == null) {
                synchronized(ItemDatabase::class) {
                    INSTANCE = databaseBuilder(context.applicationContext,
                        ItemDatabase::class.java, "item.db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}