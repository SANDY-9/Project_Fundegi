package co.kr.mapo.project_fundegi.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityLogin13Binding
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.system.PIN
import co.kr.mapo.project_fundegi.ui.activity.MAIN_
import co.kr.mapo.project_fundegi.ui.viewmodel.PinNumberViewModel

class LOGIN_1_3 : AppCompatActivity() {

    private val pinViewModel : PinNumberViewModel by viewModels()
    private lateinit var binding : ActivityLogin13Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login1_3)
        with(binding) {
            owner = this@LOGIN_1_3
            pin = pinViewModel
            lifecycleOwner = this@LOGIN_1_3
        }
        with(pinViewModel) {
            shuffleNumber()
            //when input 6 numbers
            pin_num6.observe(this@LOGIN_1_3, {
                if(it != null) {
                    if (intent.getStringExtra(PIN) == pinViewModel.getPin()) {
                        AppPrefs.savePin(this@LOGIN_1_3, pinViewModel.getPin())
                        AppPrefs.loginApp(this@LOGIN_1_3)
                        val intent = Intent(this@LOGIN_1_3, MAIN_::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LOGIN_1_3, "PIN 번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            resetPin() }, 100L)
                        increaseFailCount()
                    }
                }
            })
            //over failCount 5
            failCount.observe(this@LOGIN_1_3, {
                if(it == 5) {
                    showToast()
                    resetPin()
                    Handler(Looper.getMainLooper()).postDelayed({
                        clearFailCount() }, 30000L)
                }
            })
        }
    }

    fun showToast() {
        Toast.makeText(this, "30초 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
    }

}