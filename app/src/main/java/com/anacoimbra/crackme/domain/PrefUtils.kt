package com.anacoimbra.crackme.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson

fun defaultPref(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    return EncryptedSharedPreferences.create(
        context,
        "secret_keys",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

operator fun SharedPreferences.set(key: String, value: Any?) =
    when (value) {
        is String? -> edit { putString(key, value) }
        is Int -> edit { putInt(key, value) }
        is Boolean -> edit { putBoolean(key, value) }
        is Float -> edit { putFloat(key, value) }
        is Long -> edit { putLong(key, value) }
        is Collection<*> -> edit { putStringSet(key, value.map { v -> v.toString() }.toSet()) }
        is java.io.Serializable -> edit { putString(key, Gson().toJson(value)) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T? =
    when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        Set::class -> getStringSet(key, emptySet()) as T?
        java.io.Serializable::class -> Gson().fromJson(
            getString(key, defaultValue as? String),
            T::class.java
        )
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

private inline fun SharedPreferences.edit(operation: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}