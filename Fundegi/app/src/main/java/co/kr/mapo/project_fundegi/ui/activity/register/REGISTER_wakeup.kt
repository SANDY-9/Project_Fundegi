package co.kr.mapo.project_fundegi.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterWakeupBinding
import co.kr.mapo.project_fundegi.system.GOAL
import co.kr.mapo.project_fundegi.system.REVISE
import co.kr.mapo.project_fundegi.ui.Balloon
import co.kr.mapo.project_fundegi.ui.CustomTextWatcher
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.balloon.showAlignTop

class REGISTER_wakeup : AppCompatActivity() {

    private var _binding: ActivityRegisterWakeupBinding? = null
    private val binding get() = _binding!!
    private val savingViewModel : SavingViewModel by viewModels()
    private var revise = false
    private var goalInit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterWakeupBinding.inflate(layoutInflater)
        revise = intent.getBooleanExtra(REVISE, false)
        goalInit = intent.getIntExtra(GOAL, 0)
        with(binding) {
            setContentView(root)
            if(revise) title.text = "얼리버드 저축 수정하기"
            if(goalInit > 0) goal.hint = goalInit.toString()
            //EditText 입력시 원단위 콤마 표시
            goal.addTextChangedListener(CustomTextWatcher(goal))
            money.addTextChangedListener(CustomTextWatcher(money))
            ibTooltip.setOnClickListener {
                val tip = "매일 얼리버드 버튼을 누르면 입력한 금액이 저축됩니다."
                val balloon = Balloon(tip, this@REGISTER_wakeup).resisterTip
                ibTooltip.showAlignTop(balloon)
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClick(v: View) {
        with(binding) {
            when(v.id) {
                R.id.startButton -> {
                    val goal = goal.text.toString().trim().replace(",", "")
                    val money = money.text.toString().trim().replace(",", "")
                    if(goal.isNotBlank() && money.isNotBlank()) {
                        if(goal.toInt() > goalInit) {
                            if(revise) {
                                savingViewModel.continueSaving(
                                    2,
                                    goal.toInt(),
                                    money.toInt()
                                )
                            } else savingViewModel.startSaving(
                                2,
                                goal.toInt(),
                                money.toInt()
                            )
                        } else {
                            showToast("${goalInit}원 보다 목표금액이 높아야 합니다.")
                            return
                        }
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w("확인 : ", "Fetching FCM registration token failed", task.exception)
                                    return@OnCompleteListener
                                }
                                // Get new FCM registration token
                                val token = task.result
                            })
                        showToast("얼리버드 저축을 시작합니다.")
                        finish()
                    } else showToast("빈 칸을 모두 채워주세요.")
                }
                R.id.back_btn -> finish()
            }
        }
    }

}