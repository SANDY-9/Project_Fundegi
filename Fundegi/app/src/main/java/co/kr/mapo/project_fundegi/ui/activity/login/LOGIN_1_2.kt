package co.kr.mapo.project_fundegi.ui.activity.login

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityLogin12Binding
import co.kr.mapo.project_fundegi.system.PIN
import co.kr.mapo.project_fundegi.ui.viewmodel.PinNumberViewModel

//Pin번호 만들기

class LOGIN_1_2 : AppCompatActivity() {

    private val pinViewModel : PinNumberViewModel by viewModels()
    private lateinit var binding : ActivityLogin12Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login1_2)
        with(binding) {
            owner = this@LOGIN_1_2
            pin = pinViewModel
            lifecycleOwner = this@LOGIN_1_2
            displayButton.setOnClickListener {
                pinNumber.visibility = if(pinNumber.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
    }

    fun showDialog() {
        if(pinViewModel.pin_num6.value != null) {
            val intent = Intent(this@LOGIN_1_2, LOGIN_1_3::class.java)
            intent.putExtra(PIN, pinViewModel.getPin())
            startActivity(intent)
        } else Toast.makeText(this, "6자리를 입력해주세요", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        pinViewModel.onCleared()
    }

}