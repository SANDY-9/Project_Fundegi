package co.kr.mapo.project_fundegi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.InformSavingEntity
import co.kr.mapo.project_fundegi.databinding.ItemProgressRuleBinding
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel


class ViewPagerAdapter(
    viewModel: SavingViewModel,
    private var list: MutableList<InformSavingEntity>?) : RecyclerView.Adapter<ViewPagerAdapter.HolderView>() {

    private val observer = Observer<MutableList<InformSavingEntity>> {
        list = it
        notifyDataSetChanged()
    }

    init {
        viewModel.savings.observeForever(observer)
    }

    inner class HolderView(val binding: ItemProgressRuleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBind(item: InformSavingEntity?) {
            with(binding) {
                binding.saving = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.HolderView {
        val binding : ItemProgressRuleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_progress_rule,
            parent,
            false
        )
        return HolderView(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.HolderView, position: Int) {
        //Log.e("[함수가 실행되는가]", position.toString())
        if(list == null) {
            //Log.e("[어느 블럭이 실행되는가]", "1")
            holder.binding.saving = null
        }
        if(list?.isNotEmpty() == true) {
            //Log.e("[어느 블럭이 실행되는가]", "2")
            //Log.e("[데이터 확인$position]", "${list?.get(position)}")
            holder.setBind(list!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (list == null || list?.size == 0) 1 else list!!.size
    }

}