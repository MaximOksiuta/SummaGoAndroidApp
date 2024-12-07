package com.sirius.siriussummago.data.localstorage

import android.content.Context

class SharedPrefs(context: Context) {

    companion object {
        private const val SHAREDPREFSKEY = "Suprisa INFO"
    }


    private val sharedPrefs = context.getSharedPreferences(SHAREDPREFSKEY, Context.MODE_PRIVATE)


    var token: String
        get() = sharedPrefs.getString("token", "") ?: ""
        set(value) {
            sharedPrefs.edit().putString("token", value).apply()
        }
}