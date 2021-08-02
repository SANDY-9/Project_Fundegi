package co.kr.mapo.project_fundegi.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterDailyBinding
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.CustomTextWatcher
import co.kr.mapo.project_fundegi.ui.activity.MAIN_
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import java.text.SimpleDateFormat
import java.util.*

class REGISTER_daily : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterDailyBinding
    private val savingViewModel : SavingViewModel by viewModels()
    private var revise = false
    private var goalInit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDailyBinding.inflate(layoutInflater)
        revise = intent.getBooleanExtra(REVISE, false)
        goalInit = intent.getIntExtra(GOAL, 0)
        with(binding) {
            setContentView(root)
            if(revise) title.text = "데일리 저축 수정하기"
            if(goalInit > 0) goalEdit.hint = goalInit.toString()
            //EditText 입력시 원단위 콤마 표시
            goalEdit.addTextChangedListener(CustomTextWatcher(goalEdit))
            btStart.setOnClickListener {
                val goal = goalEdit.text.toString().trim().trim().replace(",", "")
                if(goal.isNotBlank()) {
                    if(goal.toInt() > goalInit) {
                        if(revise) {
                            savingViewModel.continueSaving(
                                4,
                                goal.toInt(),
                                0
                            )
                        } else savingViewModel.startSaving(
                            4,
                            goal.toInt(),
                            0
                        )
                    } else {
                        showToast("${goalInit}원 보다 목표금액이 높아야 합니다.")
                        return@setOnClickListener
                    }
                    showToast("데일리 저축을 시작합니다.")
                    finish()
                } else showToast("빈 칸을 모두 채워주세요.")
            }
            btBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}