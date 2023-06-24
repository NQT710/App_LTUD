package com.nqt.changelanguage

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class AppCompat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val languageManager = LanguageManager(this)

        languageManager.getLang()?.let { languageManager.update(it) }
    }
}