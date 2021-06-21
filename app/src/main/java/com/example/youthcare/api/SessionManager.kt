package com.example.youthcare.api


import android.content.Context
import android.content.SharedPreferences
import com.example.youthcare.R

class SessionManager(context: Context?) {
    private var prefs: SharedPreferences? = context?.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val  USER_ID = "user_id"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs?.edit()
        editor?.putString(USER_TOKEN, token)
        editor?.apply()
    }

    fun saveIdSportsman(id: String) {
        val editor = prefs?.edit()
        editor?.putString(USER_ID, id)
        editor?.apply()
    }

    fun fetchUserId() : String? {
        return prefs?.getString(USER_ID, null)
    }

    fun fetchAuthToken(): String? {
        return prefs?.getString(USER_TOKEN, null)
    }
}