package com.hesham.ecommerceapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.hesham.ecommerceapp.utils.Constants.Companion.LANG_AR
import com.hesham.ecommerceapp.utils.Constants.Companion.LANG_EN
import java.util.Locale

object LocalUtil {

    fun onAttach(context: Context): Context {
        val language = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(Constants.LANGUAGE_KEY, LANG_EN)
            ?: LANG_EN
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    fun changeLanguage(activity: Activity) {
        val language = PreferenceManager.getDefaultSharedPreferences(activity)
            .getString(Constants.LANGUAGE_KEY, LANG_EN)
            ?: LANG_EN
        val locale = Locale(language)
        val activityRes: Resources = activity.resources
        val activityConfig = activityRes.configuration
        activityConfig.setLocale(locale)

        activityRes.updateConfiguration(activityConfig, activityRes.displayMetrics)
        val appRes: Resources = activity.applicationContext.resources
        val appConfig = appRes.configuration
        appConfig.setLocale(locale)
        appRes.updateConfiguration(
            appConfig,
            appRes.displayMetrics
        )

        when (language) {
            LANG_AR -> {
                activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }

            LANG_EN -> {
                activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
            }

            else -> View.LAYOUT_DIRECTION_LOCALE
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


}