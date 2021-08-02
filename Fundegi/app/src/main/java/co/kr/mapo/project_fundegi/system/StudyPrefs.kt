package co.kr.mapo.project_fundegi.system

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import java.util.Date as Long

class StudyPrefs(context: Context) {
    private val studyPrefs: SharedPreferences =
        //context.getSharedPreferences("study_prefs", Context.MODE_PRIVATE)
        context.getSharedPreferences("study_prefs", 0)

/*
    fun getRecord(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
 */

//GET
    // 공부 누적 시간
    fun getRecord(key: String, time: Int): Int {
        return studyPrefs.getInt(key, time)
    }
    // 날짜
    fun getDate(key: String, date: String): String {
        return studyPrefs.getString(key, date).toString()
    }
    // 저축 목표 금액
    fun getTargetAmount(key: String, amount: Int): Int {
        return studyPrefs.getInt(key, amount)
    }
    // 일일 저축 금액
    fun getSavingAmount(key: String, amount: Int): Int {
        return studyPrefs.getInt(key, amount)
    }
    // 누적 저축 금액
    fun getAmount(key: String, amount: Int): Int {
        return studyPrefs.getInt(key, amount)
    }

//SET
    // 공부 누적 시간 저장
    fun setRecord(key: String, time: Int) {
        studyPrefs.edit().putInt(key, time).apply()
    }
    // 날짜 저장
    fun setDate(key: String, date: String) {
        studyPrefs.edit().putString(key, date).apply()
    }
    // 목표 금액 설정
    fun setTargetAmount(key: String, amount: Int) {
        studyPrefs.edit().putInt(key, amount).apply()
    }
    // 일일 저축 금액 설정
    fun setSavingAmount(key: String,  amount: Int) {
        studyPrefs.edit().putInt(key, amount).apply()
    }
    // 누적 저축 금액
    fun setAmount(key: String,  amount: Int) {
        studyPrefs.edit().putInt(key, amount).apply()
    }

}