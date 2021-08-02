package co.kr.mapo.project_fundegi.system

import android.util.Log
import java.text.DecimalFormat

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-20
 * @desc
 */
object ConvertUtils {

    fun timeToText(time: Int): String? {
        return when {
            time < 0 -> {
                null
            }
            time == 0 -> {
                "00:00:00"
            }
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$02d:%2$02d:%3$02d".format(h, m, s)
            }
        }
    }

    fun convertHour(time: Int): String? {
        return when {
            time < 0 -> {
                null
            }
            time == 0 -> {
                "0시간"
            }
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$2d시간 %2$2d분 %3$2d초".format(h, m, s)
            }
        }
    }

    fun convertMoney(money: Int) : String {
        val dec = DecimalFormat("#,###")
        return dec.format(money).toString()
    }
}