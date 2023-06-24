package com.nqt.changelanguage

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale
class LanguageManager(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LANG", Context.MODE_PRIVATE)
    fun update(code: String){
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources :Resources = context.resources

        val configuration: Configuration = resources.configuration

        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        setLang(code )
    }

    fun getLang(): String? {
        return sharedPreferences.getString("lang", "en")
    }
    private fun setLang(code:String){
        val editor = sharedPreferences.edit()
        editor.run {
            putString("lang", code)
            apply()
        }
    }
}