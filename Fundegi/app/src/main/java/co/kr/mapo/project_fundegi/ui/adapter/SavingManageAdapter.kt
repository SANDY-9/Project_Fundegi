package co.kr.mapo.project_fundegi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.InformSavingEntity
import co.kr.mapo.project_fundegi.databinding.RecyclerSavingBinding
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-12
 * @desc
 */
class SavingManageAdapter(private val savingViewModel: SavingViewModel) : RecyclerView.Adapter<SavingManageAdapter.HolderView>() {

    private var list = savingViewModel.allSaving.value
    private val observer = Observer<MutableList<InformSavingEntity>> {
        list = it
        notifyDataSetChanged()
    }

    init {
        savingViewModel.allSaving.observeForever(observer)
    }

    inner class HolderView(private val binding: RecyclerSavingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBinding(item: InformSavingEntity) {
            with(binding) {
                saving = item
                executePendingBindings()
                buttonClose.setOnClickListener {
                    savingViewModel.removeData(adapterPosition)
                    notifyDataSetChanged()//삭제 로직 완성되면 지우기
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        val binding: RecyclerSavingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_saving,
            parent,
            false)
        return HolderView(binding)
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.setBinding(list!![position])
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}