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
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.DialogCompleteBinding
import co.kr.mapo.project_fundegi.databinding.FragmentThemeStudyProgressBinding
import co.kr.mapo.project_fundegi.system.Fundegi
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.activity.THEME_study_watch
import co.kr.mapo.project_fundegi.ui.activity.Timeline
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_diet
import co.kr.mapo.project_fundegi.ui.activity.register.REGISTER_study
import co.kr.mapo.project_fundegi.ui.adapter.TimelineAdapter
import co.kr.mapo.project_fundegi.ui.adapter.ViewPagerAdapter
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel


class THEME_study_progress : Fragment() {

    private lateinit var binding : FragmentThemeStudyProgressBinding
    private val savingViewModel : SavingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme_study_progress, container, false)
        with(binding) {
            saving = savingViewModel
            lifecycleOwner = this@THEME_study_progress
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            startButton.setOnClickListener {
                startActivity(Intent(requireContext(), REGISTER_study::class.java))
            }
            //?????? ???????????? ??????
            btStarStopwatch.setOnClickListener {
                startActivity(Intent(requireContext(), THEME_study_watch::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(savingViewModel) {
            totalMoney.value?.let {
                Log.e("[??????]", it.toString())
                if(it> 0 && it >= goalMoney.value!!) showCompleteDialog()
            }
        }
    }

    private fun showCompleteDialog() {
        //???????????????
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
                    tvSavingMoney.text = "$it ???"
                })
                savingName.text = "?????? ??????"
                btClose.setOnClickListener { dismiss() }
                btContinue.setOnClickListener {
                    dismiss()
                    val intent = Intent(requireContext(), REGISTER_study::class.java).apply {
                        putExtra(REVISE, true)
                        putExtra(GOAL, savingViewModel.totalMoney.value)
                    }
                    startActivity(intent)
                }
                //????????????
                btComplete.setOnClickListener {
                    savingViewModel.endSaving(3)
                    Toast.makeText(requireContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    //?????? ??????
    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> null
            time == 0 -> "00:00:00"
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$01d?????? %2$01d??? %3$01d???".format(h, m, s)
            }
        }
    }

    companion object {
        fun newInstance(): THEME_study_progress {
            return THEME_study_progress()
        }
    }
}