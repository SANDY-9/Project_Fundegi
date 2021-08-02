package co.kr.mapo.project_fundegi.ui

import android.util.Log
import java.util.*
import java.util.Calendar

class Calendar(date: Date) {

    companion object {
        const val DAY_OF_WEEK = 7
        const val LOW_OF_CALENDAR = 6
    }

    val calendar = Calendar.getInstance()

    var prevTail = 0    // 이전 달 끝부분 요일
    var nextHead = 0    // 다음 달 앞부분
    var currentMaxDate = 0  // 마지막 일

    var dateList = mutableListOf<WakeUp>()

    init {
        calendar.time = date
    }

    fun initBaseCalendar() {
        makeMonthDate()
    }

    private fun makeMonthDate() {
        dateList.clear()
        calendar.set(Calendar.DATE, 1) // 1일 셋팅
        currentMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // 현재 날짜 기준 최대 수
        prevTail = calendar.get(Calendar.DAY_OF_WEEK) - 1 // 해당 월 시작 요일 -1

        makePrevTail(calendar.clone() as Calendar)
        makeCurrentMonth(calendar)

        nextHead = LOW_OF_CALENDAR * DAY_OF_WEEK - (prevTail + currentMaxDate)
        makeNextHead()
    }

    private fun makePrevTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevTail

        for (i in 1..prevTail) dateList.add(WakeUp(++maxOffsetDate,false))
    }

    private fun makeCurrentMonth(calendar: Calendar){
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) dateList.add(WakeUp(i,false))
    }

    private fun makeNextHead(){
        var date = 1
        for (i in 1..nextHead) dateList.add( WakeUp(date++,false))
    }
}