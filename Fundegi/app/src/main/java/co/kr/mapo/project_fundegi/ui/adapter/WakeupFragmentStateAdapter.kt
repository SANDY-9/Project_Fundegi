package co.kr.mapo.project_fundegi.ui.adapter

import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.kr.mapo.project_fundegi.ui.fragment.CalendarFragment
import kotlin.properties.Delegates

class WakeupFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val wakeupFragmentPosition = Int.MAX_VALUE / 2

    //var clicked : Boolean = false
    lateinit var calendarFragment: CalendarFragment

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        calendarFragment = CalendarFragment()
        calendarFragment.pageIndex = position
        return calendarFragment

    }

    fun onClicked(clicked: Boolean) {
        calendarFragment.onClicked(clicked)
        Log.e("test","캘린더 뷰페이저 : $clicked")
    }

}