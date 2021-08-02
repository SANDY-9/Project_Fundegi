package co.kr.mapo.project_fundegi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.ui.WakeUp
import co.kr.mapo.project_fundegi.ui.Calendar
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val context: Context,
    val date: Date,
    val month: Int) :
    RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder>() {

    var dataList: MutableList<WakeUp> = arrayListOf()
    private val cal = java.util.Calendar.getInstance()
    lateinit var dateString: String
    var dateInt: Int = 0

    // Calendar을 이용하여 날짜 리스트 세팅
    var calendar = Calendar(date)
    val todayIndex : Int =
        with(cal) {
            val date = get(java.util.Calendar.DATE)
            set(java.util.Calendar.DAY_OF_MONTH, 1)
            val dayOfWeek = get(java.util.Calendar.DAY_OF_WEEK)
            return@with (date - 1) + (dayOfWeek - 1)
        }

    init {
        calendar.initBaseCalendar()
        dataList = calendar.dateList
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_list, parent, false)
        return CalendarItemHolder(view)
    }


    override fun onBindViewHolder(holder: CalendarItemHolder, position: Int) {
        holder.bind(dataList[position].date, position)
        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class CalendarItemHolder(itemVeiw: View?) : RecyclerView.ViewHolder(itemVeiw!!) {

        var itemCalendarDateText: TextView = itemView.findViewById(R.id.item_calendar_date_text)
        var stampImageView : ImageView = itemVeiw!!.findViewById(R.id.stamp)

        fun bind(data: Int, position: Int) {
            stampImageView.visibility = View.INVISIBLE

            val firstDateIndex = calendar.prevTail
            val lastDateIndex = dataList.size - calendar.nextHead - 1

            // 날짜 표시
            itemCalendarDateText.text = data.toString()

            // 오늘 날짜 처리
            if(month == cal.get(java.util.Calendar.MONTH)+1) {
                dateString = SimpleDateFormat("dd", Locale.KOREA).format(date)
                dateInt = dateString.toInt()
                if(position == todayIndex && AppPrefs.getWaked(context)) {
                    Log.e("[TEST]", position.toString())
                    Log.e("[TEST]", todayIndex.toString())
                    Log.e("[TEST]", AppPrefs.getWaked(context).toString())
                    stampImageView.visibility = View.VISIBLE
                }
                if (dataList[position].date == dateInt) {
                    itemCalendarDateText.setTypeface(itemCalendarDateText.typeface, Typeface.BOLD)
                    itemCalendarDateText.setTextColor(
                        Color.parseColor("#4DB6AC")
                    )
                }
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextAppearance(R.style.calendar_text3)
            }

            if (dataList[position].isWaked) {
                stampImageView.visibility = View.VISIBLE
            }
        }
    }

    fun isWaked(waked: Boolean) {
        dataList[todayIndex].isWaked = waked
        notifyDataSetChanged()
    }

}
