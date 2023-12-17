package com.example.testquiztask.model

import android.content.Context
import android.content.SharedPreferences

class ThemeManager(private val context: Context) {

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("ThemePreferences", Context.MODE_PRIVATE)

    fun getSelectedTheme(): Int{
        return sharedPreferences.getInt("selectedTheme", 0)
    }

    fun setSelectedTheme(theme: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("selectedTheme", theme)
        editor.apply()
    }

}