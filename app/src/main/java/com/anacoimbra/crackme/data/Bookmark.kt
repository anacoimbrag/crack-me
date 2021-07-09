package com.anacoimbra.crackme.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("text"), unique = true)])
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val text: String
)