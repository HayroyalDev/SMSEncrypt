package com.hayroyal.mavericks.smsencrypt.Helper

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by mavericks on 3/6/18.
 */
class AppPreference(cont:Context){
    internal var context: Context = cont
    internal var spref: SharedPreferences = context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)

    fun setAtFirstRun(value: Boolean) {
        val editor = spref.edit()
        editor.putBoolean("atFirstRun", value).apply()
    }

    fun getAtFirstRun(): Boolean {
        return spref.getBoolean("atFirstRun", true)
    }

    fun setPassword(value: String) {
        val editor = spref.edit()
        editor.putString("password", value).apply()
    }

    fun getPassword(): String {
        return spref.getString("password", null)
    }
}