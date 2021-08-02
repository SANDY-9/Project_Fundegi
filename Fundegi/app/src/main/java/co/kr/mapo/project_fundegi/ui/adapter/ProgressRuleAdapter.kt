package co.kr.mapo.project_fundegi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import java.text.DecimalFormat

/*
class ProgressRuleAdapter(savingList: MutableList<ThemeSaving>) : RecyclerView.Adapter<ProgressRuleAdapter.PagerViewHolder>() {
    var item = savingList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_progress_rule, parent, false)){

        private val savingCategory:TextView = itemView.findViewById(R.id.saving_category_textView)
        // 포맷
        val dec = DecimalFormat("#,###")

        fun onBind(data: ThemeSaving){
            savingCategory.text = data.s1

            var decPrice = dec.format(data.i)
        }
    }
}
 */