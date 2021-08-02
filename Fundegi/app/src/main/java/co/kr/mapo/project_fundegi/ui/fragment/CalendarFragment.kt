package co.kr.mapo.project_fundegi.ui.fragment

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.FragmentCalendarBinding
import co.kr.mapo.project_fundegi.ui.WakeUp
import co.kr.mapo.project_fundegi.ui.activity.MAIN_
import co.kr.mapo.project_fundegi.ui.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

@RequiresApi(Build.VERSION_CODES.N)
class CalendarFragment : Fragment() {

    var pageIndex = 0

    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        pageIndex -= (Int.MAX_VALUE / 2)

        with(binding) {

            // 날짜
            val date = Calendar.getInstance().run {
                add(Calendar.MONTH, pageIndex)
                time
            }

            // 포맷
            val month = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(date.time)
            calendarYearMonthText.apply {
                text = month
            }

            //  calendarAdapter
            calendarAdapter = CalendarAdapter(requireContext(), date, month.substring(6, 8).toInt())
            calendarView.adapter = calendarAdapter

            return root
        }
    }

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    fun onClicked(clicked: Boolean) {
        calendarAdapter.isWaked(clicked)
    }
}