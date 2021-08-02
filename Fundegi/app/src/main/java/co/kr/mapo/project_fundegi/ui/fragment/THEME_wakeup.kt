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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.DialogCompleteBinding
import co.kr.mapo.project_fundegi.databinding.FragmentThemeWakeupBinding
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.activity.MAIN_
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_diet
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_wakeup
import co.kr.mapo.project_fundegi.ui.adapter.CalendarAdapter
import co.kr.mapo.project_fundegi.ui.adapter.WakeupFragmentStateAdapter
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

class THEME_wakeup : Fragment() {

    private lateinit var binding: FragmentThemeWakeupBinding
    private val viewModel: SavingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theme_wakeup, container, false)
        with(binding) {
            saving = viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val wakeupFragmentStateAdapter = WakeupFragmentStateAdapter(requireActivity())
            with(calendarPager) {
                // viewpager
                adapter = wakeupFragmentStateAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                wakeupFragmentStateAdapter.apply {
                    setCurrentItem(this.wakeupFragmentPosition, false)
                }

                // early Button
                earlyBtn.isEnabled = !AppPrefs.getWaked(requireContext())
                earlyBtn.setOnClickListener {
                    wakeupFragmentStateAdapter.onClicked(true)
                    with(earlyBtn){
                        isEnabled = false
                        setBackgroundColor(ContextCompat.getColor(context, R.color.register2))
                    }
                    AppPrefs.saveWaked(requireContext())
                }

                nextButton.setOnClickListener {
                    startActivity(Intent(requireContext(), REGISTER_wakeup::class.java))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(viewModel) {
            totalMoney.value?.let {
                Log.e("[얼리버드]", it.toString())
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
                viewModel.totalMoney.observe(viewLifecycleOwner, {
                    tvSavingMoney.text = "$it 원"
                })
                savingName.text = "얼리버드 저축"
                btClose.setOnClickListener { dismiss() }
                btContinue.setOnClickListener {
                    dismiss()
                    val intent = Intent(requireContext(), REGISTER_wakeup::class.java).apply {
                        putExtra(REVISE, true)
                        putExtra(GOAL, viewModel.totalMoney.value)
                    }
                    startActivity(intent)
                }
                //저축완료
                btComplete.setOnClickListener {
                    viewModel.endSaving(2)
                    Toast.makeText(requireContext(), "저축을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    AppPrefs.removeWaked(requireContext())
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(): THEME_wakeup {
            return THEME_wakeup()
        }
    }

}

