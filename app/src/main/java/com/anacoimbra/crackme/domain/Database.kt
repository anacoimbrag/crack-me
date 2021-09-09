package com.anacoimbra.crackme.domain

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anacoimbra.crackme.BuildConfig
import com.anacoimbra.crackme.data.Bookmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.sqlcipher.database.SupportFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore

@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}

fun getDatabase(context: Context): AppDatabase {
    val builder = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "secured.db"
    ).allowMainThreadQueries()

//    return withContext(Dispatchers.IO) {
//        val keyStore = KeyStore.getInstance("AndroidKeyStore")
//        keyStore.load(null)
//        val secretKeyEntry = keyStore
//            .getEntry("db_pass", null) as? KeyStore.SecretKeyEntry
//
//        val secretKey = secretKeyEntry?.secretKey?.encoded ?: generateKeyPair()?.private?.encoded
        val factory = SupportFactory(BuildConfig.AUTH_TOKEN.toByteArray())
        builder.openHelperFactory(factory)
//    }.build()
    return builder.build()
}

private fun generateKeyPair(): KeyPair? {
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

    return kpg.generateKeyPair()
}