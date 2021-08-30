package com.anacoimbra.crackme.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anacoimbra.crackme.data.Bookmark

@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}

fun getDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "crack-me.db"
).build()