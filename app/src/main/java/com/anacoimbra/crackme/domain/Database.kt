package com.anacoimbra.crackme.domain

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anacoimbra.crackme.data.Bookmark
import net.sqlcipher.database.SupportFactory
import java.security.KeyPairGenerator

@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}

fun getDatabase(context: Context): AppDatabase {
    val builder = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "secured.db"
    ).allowMainThreadQueries()

    val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_EC,
        "AndroidKeyStore"
    )
    val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
        "db_pass",
        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
    ).run {
        setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
        build()
    }

    kpg.initialize(parameterSpec)

    val kp = kpg.generateKeyPair()
    Log.d("Key", kp.public.encoded.decodeToString())
    val factory = SupportFactory(kp.public.encoded)
    builder.openHelperFactory(factory)
    return builder.build()
}