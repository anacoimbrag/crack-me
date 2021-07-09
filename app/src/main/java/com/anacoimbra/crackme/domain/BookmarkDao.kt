package com.anacoimbra.crackme.domain

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anacoimbra.crackme.data.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM Bookmark")
    fun getAllBookmarked(): LiveData<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: Bookmark)

    @Delete
    fun removeBookmark(bookmark: Bookmark)
}