package co.kr.mapo.project_fundegi.system

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import java.util.Date as Long

class DailyPrefs(context: Context) {
    private val dailyPrefs: SharedPreferences =
        context.getSharedPreferences("daily_prefs", 0)

//GET
    // 저축 시작 날짜
    fun getDate(key: String, date: String): String {
        return dailyPrefs.getString(key, date).toString()
    }
    // 저축한 날짜
    fun getSavingDay(key: String, date: String): String {
        return dailyPrefs.getString(key, date).toString()
    }
    // 저축 목표 금액
    fun getTargetAmount(key: String, amount: Int): Int {
        return dailyPrefs.getInt(key, amount)
    }
    // 저축 목표 금액
    fun getSavingAmount(key: String, amount: Int): Int {
        return dailyPrefs.getInt(key, amount)
    }


//SET
    // 날짜 저장
    fun setDate(key: String, date: String) {
    dailyPrefs.edit().putString(key, date).apply()
    }
    // 저축한 날짜
    fun setSavingDay(key: String, date: String) {
        dailyPrefs.edit().putString(key, date).apply()
    }
    // 목표 금액 설정
    fun setTargetAmount(key: String, amount: Int) {
        dailyPrefs.edit().putInt(key, amount).apply()
    }
    // 저축 금액 누적
    fun setSavingAmount(key: String, amount: Int) {
        dailyPrefs.edit().putInt(key, amount).apply()
    }

}