package co.kr.mapo.project_fundegi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.SavingEntity
import co.kr.mapo.project_fundegi.databinding.RecyclerTimelineBinding
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

class TimelineAdapter(viewModel: SavingViewModel) : RecyclerView.Adapter<TimelineAdapter.HolderView>() {

    private var list = viewModel.timeLine.value
    private val observer = Observer<List<SavingEntity>> {
        list = it
        notifyDataSetChanged()
    }

    init {
        viewModel.timeLine.observeForever(observer)
    }

    inner class HolderView(val binding: RecyclerTimelineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBind(item : SavingEntity) {
            with(binding) {
                saving = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        val binding: RecyclerTimelineBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_timeline,
            parent,
            false)
        return HolderView(binding)
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.setBind(list!![position])
    }

    override fun getItemCount(): Int = list?.size ?: 0

}