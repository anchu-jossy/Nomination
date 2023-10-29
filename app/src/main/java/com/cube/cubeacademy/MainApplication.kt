package com.cube.cubeacademy

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        // Function to save a boolean flag in SharedPreferences
        fun saveFlagToSharedPreferences(context: Context, flag: Boolean) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(Companion.PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(Companion.FLAG_KEY_NOMINATION_SUBMITTED, flag)
            editor.apply()
        }

        // Function to retrieve a boolean flag from SharedPreferences
        fun getFlagFromSharedPreferences(context: Context): Boolean {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(Companion.PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(
                Companion.FLAG_KEY_NOMINATION_SUBMITTED,
                false
            ) // Default value is false if the flag is not found
        }

        // Define a constant for the flag's key
        private const val PREFS_NAME = "MyAppPrefs"
        private const val FLAG_KEY_NOMINATION_SUBMITTED = "isNominationSubmittedOnce"
    }
}