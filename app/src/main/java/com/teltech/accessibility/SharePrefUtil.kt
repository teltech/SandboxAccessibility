package com.teltech.accessibility

import android.content.Context
import android.content.SharedPreferences

object SharePrefUtil {

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(Constants.prefsFilename, Context.MODE_PRIVATE)
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences?.let {
            it.edit().putBoolean(key, value).commit()
        }
    }

    fun getBoolean(key: String): Boolean {
        sharedPreferences?.let {
            return it.getBoolean(key, false)
        }
        return false
    }

    fun setString(key: String, value: String) {
        sharedPreferences?.let {
            it.edit().putString(key, value).commit()
        }
    }

    fun getString(key: String): String {
        sharedPreferences?.let { it ->
            val resolvedValue = it.getString(key, "")
            resolvedValue?.let {
                return it
            }
            return ""
        }
        return ""
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences?.let {
            it.edit().putInt(key, value).commit()
        }
    }

    fun getInt(key: String): Int {
        sharedPreferences?.let {
            return it.getInt(key, 0)
        }
        return 0
    }

    fun setLong(key: String, value: Long){
        sharedPreferences?.let {
            it.edit().putLong(key, value).apply()
        }
    }

    fun getLong(key: String): Long {
        sharedPreferences?.let {
            return it.getLong(key, 0)
        }
        return 0
    }

    fun clearAllData() {
        sharedPreferences?.let {
            it.edit().clear().commit()
        }
    }
}
