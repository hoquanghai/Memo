package com.login

import android.content.Context
import com.google.gson.Gson

class TokenHelper(context: Context) {

    private val sharedPrefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    fun set(token: String) {
        val editor = sharedPrefs!!.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun get(): String? {
        return sharedPrefs.getString("token", null)
    }

    fun reset() {
        set("")
    }
}

class UserHelper(context: Context) {
    val gson = Gson()
    private val sharedPrefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    fun set(user: User?) {
        val editor = sharedPrefs!!.edit()
        editor.putString("user", gson.toJson(user))
        editor.apply()
    }

    fun get(): User? {
        val userString = sharedPrefs.getString("user", null)
        return  gson.fromJson(userString,User::class.java)
    }

    fun reset() {
        set(null)
    }
}