package co.kr.mapo.project_fundegi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityPinBinding
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.ui.viewmodel.PinNumberViewModel
import kotlinx.coroutines.delay
import java.util.*

class PIN_ : AppCompatActivity() {

    private val pinViewModel : PinNumberViewModel by viewModels()
    private lateinit var binding : ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pin)
        with(binding) {
            owner = this@PIN_
            pin = pinViewModel
            lifecycleOwner = this@PIN_
        }
        with(pinViewModel) {
            shuffleNumber()

            //when input 6 numbers
            pin_num6.observe(this@PIN_, {
                if (it != null) {
                    val pin = AppPrefs.getPin(this@PIN_)
                    if (pin == getPin()) {
                        AppPrefs.loginApp(this@PIN_)
                        startActivity(Intent(this@PIN_, MAIN_::class.java))
                        finish()
                    } else {
                        increaseFailCount()
                        Toast.makeText(this@PIN_, "등록하신 PIN번호와 일치하지 않습니다. [${failCount.value}/5]",
                            Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            //over failCount 5
                            if(failCount.value == 5) {
                                showToast()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    clearFailCount() }, 30000L)
                            }
                            resetPin()
                        }, 100L)
                    }
                }
            })
        }
    }

    fun showToast() {
        Toast.makeText(this, "30초 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
    }
}