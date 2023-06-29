package com.example.careium.core.database.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(
        "UserSPFile", Context.MODE_PRIVATE
    )


    @SuppressLint("CommitPrefEdits")
    fun write(email: String) {
        val editor = preference.edit()
        editor.putString("UserEmail", email)
        editor.apply()
    }

    fun writeProgressCalo(email: String, calo: Int) {
        val editor = preference.edit()
        editor.putInt(email, calo)
        editor.apply()
    }

    fun readProgressCalo(email: String): Int {
        return this.preference.getInt(email, 0)
    }

    @SuppressLint("CommitPrefEdits")
    fun delete() {
        val editor = this.preference.edit()
        editor.clear()
        editor.apply()
    }

    fun isSPHasValue(): Boolean {
        val email: String? = this.preference.getString("UserEmail", "Failed")
        if (email.isNullOrEmpty() || email == "Failed")
            return false

        return true
    }

}