package co.kr.mapo.project_fundegi.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.repository.SavingRepositoryImpl
import co.kr.mapo.project_fundegi.databinding.DialogCompleteBinding
import co.kr.mapo.project_fundegi.databinding.DialogStopwatchBinding
import co.kr.mapo.project_fundegi.databinding.FragmentThemeDietBinding
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_diet
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

class THEME_diet : Fragment() {

    private lateinit var binding: FragmentThemeDietBinding
    private val savingViewModel : SavingViewModel by activityViewModels()
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme_diet, container,false)
        with(binding) {
            saving = savingViewModel
            fragment = this@THEME_diet
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    private fun showCompleteDialog() {
        //다이얼로그
        val bindingDialog = DialogCompleteBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext()).create()
        with(builder) {
            apply {
                setView(bindingDialog.root)
                setCancelable(true)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }.show()
            with(bindingDialog) {
                savingViewModel.totalMoney.observe(viewLifecycleOwner, {
                    tvSavingMoney.text = "$it 원"
                })
                savingName.text = "다이어트 저축"
                btClose.setOnClickListener { dismiss() }
                btContinue.setOnClickListener {
                    dismiss()
                    val intent = Intent(requireContext(), REGISTER_diet::class.java).apply {
                        putExtra(REVISE, true)
                        putExtra(GOAL, savingViewModel.totalMoney.value)
                    }
                    startActivity(intent)
                }
                //저축완료
                btComplete.setOnClickListener {
                    savingViewModel.endSaving(1)
                    Toast.makeText(requireContext(), "저축을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    fun onStartSaving() {
        startActivity(Intent(requireContext(), REGISTER_diet::class.java))
    }

    override fun onResume() {
        super.onResume()
        with(savingViewModel) {
            totalMoney.value?.let {
                if(it> 0 && it >= goalMoney.value!!) showCompleteDialog()
            }
        }
    }

    companion object {
        fun newInstance(): THEME_diet {
            return THEME_diet()
        }
    }

}