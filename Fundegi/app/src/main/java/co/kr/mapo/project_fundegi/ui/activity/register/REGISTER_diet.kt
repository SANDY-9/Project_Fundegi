package co.kr.mapo.project_fundegi.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterDietBinding
import co.kr.mapo.project_fundegi.system.CARD_NUMBER
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.Balloon
import co.kr.mapo.project_fundegi.ui.CustomTextWatcher
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import com.skydoves.balloon.showAlignTop
import kotlin.properties.Delegates

class REGISTER_diet : AppCompatActivity() {

    private val savingViewModel : SavingViewModel by viewModels()
    private lateinit var binding : ActivityRegisterDietBinding
    private var revise = false
    private var goalInit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_diet)
        revise = intent.getBooleanExtra(REVISE, false)
        goalInit = intent.getIntExtra(GOAL, 0)
        with(binding) {
            saving = savingViewModel
            lifecycleOwner = this@REGISTER_diet
            if(revise) title.text = "다이어트 저축 수정하기"
            if(goalInit > 0) editGoal.hint = goalInit.toString()
            //EditText 입력시 원단위 콤마 표시
            editGoal.addTextChangedListener(CustomTextWatcher(editGoal))
            editMoney.addTextChangedListener(CustomTextWatcher(editMoney))
            ibTooltip.setOnClickListener {
                val tip = "외식·배달 내역이 없을 때,입력한 금액이 저축됩니다."
                val balloon = Balloon(tip, this@REGISTER_diet).resisterTip
                ibTooltip.showAlignTop(balloon)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClick(v: View) {
        with(binding) {
            when(v.id) {
                R.id.startButton -> {
                    val goal = editGoal.text.toString().trim().replace(",", "")
                    val money = editMoney.text.toString().trim().replace(",", "")
                    if(goal.isNotBlank() && money.isNotBlank()) {
                        if(goal.toInt() > goalInit) {
                            if(revise) {
                                savingViewModel.continueSaving(
                                    1,
                                    goal.toInt(),
                                    money.toInt()
                                )
                            } else savingViewModel.startSaving(
                                1,
                                goal.toInt(),
                                money.toInt()
                            )
                        } else {
                            showToast("${goalInit}원 보다 목표금액이 높아야 합니다.")
                            return
                        }
                        showToast("다이어트 저축을 시작합니다.")
                        finish()
                    } else showToast("빈 칸을 모두 채워주세요.")
                }
                R.id.cardButton -> {
                    val intent = Intent(this@REGISTER_diet, REGISTER_CARD_1::class.java)
                    startActivity(intent)
                }
                R.id.backButton -> {
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val cardNumber = intent.getStringExtra(CARD_NUMBER)
        if(cardNumber != null) binding.cardNumberText.text = cardNumber
    }

}