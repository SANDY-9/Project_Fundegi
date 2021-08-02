package co.kr.mapo.project_fundegi.system

import android.app.Application
import android.content.Context

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-06-28
 * @desc 자동로그인 및 PIN번호 관리 object
 */

object AppPrefs {

    private const val LOGIN_STATE = "login"
    private const val WAKED = "waked"

    fun savePin(context: Context, pin: String) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(PIN, pin)
            putBoolean(LOGIN_STATE, true)
        }.apply()
    }

    fun getPin(context: Context) : String? {
        return context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
            .getString(PIN, "")
    }

    fun loginApp(context: Context) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(LOGIN_STATE, true)
            .apply()
    }

    fun logoutApp(context: Context) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(LOGIN_STATE, false)
            .apply()
    }

    fun isLogined(context: Context) : Boolean {
        return context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
            .getBoolean(LOGIN_STATE, false)
    }

    fun saveWaked(context: Context) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(WAKED, true)
            .apply()
    }

    fun getWaked(context: Context) : Boolean {
        return context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
            .getBoolean(WAKED, false)
    }

    fun removeWaked(context: Context) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(WAKED, false)
            .apply()
    }

}