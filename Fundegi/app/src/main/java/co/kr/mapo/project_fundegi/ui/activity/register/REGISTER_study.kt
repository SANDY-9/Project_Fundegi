package co.kr.mapo.project_fundegi.ui.activity.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterStudyBinding
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.databinding.BalloonDescBinding
import co.kr.mapo.project_fundegi.ui.Balloon
import co.kr.mapo.project_fundegi.ui.CustomTextWatcher
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import com.skydoves.balloon.*

class REGISTER_study : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStudyBinding
    private val viewModel: SavingViewModel by viewModels()
    private var revise = false
    private var goalInit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStudyBinding.inflate(layoutInflater)
        revise = intent.getBooleanExtra(REVISE, false)
        goalInit = intent.getIntExtra(GOAL, 0)
        with(binding) {
            setContentView(root)
            if (revise) title.text = "공부 저축 수정하기"
            if (goalInit > 0) goal.hint = goalInit.toString()
            //EditText 입력시 원단위 콤마 표시
            goal.addTextChangedListener(CustomTextWatcher(goal))
            money.addTextChangedListener(CustomTextWatcher(money))
            ibTooltip.setOnClickListener {
                val tip = "한시간 이상 공부하면 입력한 금액만큼 저축됩니다."
                val balloon = Balloon(tip, this@REGISTER_study).resisterTip
                ibTooltip.showAlignTop(balloon)
            }
            setContentView(binding.root)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClick(v: View) {
        with(binding) {
            when (v.id) {
                R.id.startButton -> {
                    val goal = goal.text.toString().trim().replace(",", "")
                    val money = money.text.toString().trim().replace(",", "")
                    if (goal.isNotBlank() && money.isNotBlank()) {
                        if (goal.toInt() > goalInit) {
                            if (revise) {
                                viewModel.continueSaving(
                                    3,
                                    goal.toInt(),
                                    money.toInt()
                                )
                            } else {
                                viewModel.startSaving(
                                    3,
                                    goal.toInt(),
                                    money.toInt()
                                )
                            }
                        } else {
                            showToast("${goalInit}원 보다 목표금액이 높아야 합니다.")
                            return
                        }
                        showToast("공부 저축을 시작합니다.")
                        finish()
                    } else showToast("빈 칸을 모두 채워주세요.")
                }
                R.id.bt_back -> finish()
                else -> return
            }
        }
    }
}