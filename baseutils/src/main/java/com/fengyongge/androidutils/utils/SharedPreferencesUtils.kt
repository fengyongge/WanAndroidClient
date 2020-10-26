package com.fengyongge.androidutils.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.IllegalArgumentException
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SharedPreferencesUtils(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {

        context.getSharedPreferences("ksp", Context.MODE_PRIVATE)
    }

    fun put(key: String, value: Any) = with(sharedPreferences.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("This type of value cannot be save!")
        }.apply()
    }

    fun <T> get(key: String, defaultValue: T): T = with(sharedPreferences) {
        val value = when (defaultValue) {
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("This type of value cannot be get!")
        }

        return value as T
    }

    fun isContains(key: String) {
        sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}