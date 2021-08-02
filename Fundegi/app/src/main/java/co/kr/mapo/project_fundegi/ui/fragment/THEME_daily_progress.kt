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
import co.kr.mapo.project_fundegi.databinding.DialogCompleteBinding
import co.kr.mapo.project_fundegi.databinding.FragmentThemeDailyProgressBinding
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_daily
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_study
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

class THEME_daily_progress : Fragment() {

    private lateinit var binding : FragmentThemeDailyProgressBinding
    private val savingViewModel : SavingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme_daily_progress, container, false)
        with(binding) {
            saving = savingViewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            registerButton.setOnClickListener {
                startActivity(Intent(requireContext(), REGISTER_daily::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(savingViewModel) {
            totalMoney.value?.let {
                if(it> 0 && it >= goalMoney.value!!) showCompleteDialog()
            }
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
                savingName.text = "데일리 저축"
                btClose.setOnClickListener { dismiss() }
                btContinue.setOnClickListener {
                    dismiss()
                    val intent = Intent(requireContext(), REGISTER_daily::class.java).apply {
                        putExtra(REVISE, true)
                        putExtra(GOAL, savingViewModel.totalMoney.value)
                    }
                    startActivity(intent)
                }
                //저축완료
                btComplete.setOnClickListener {
                    savingViewModel.endSaving(4)
                    Toast.makeText(requireContext(), "저축을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(): THEME_daily_progress {
            return THEME_daily_progress()
        }
    }

}