package co.kr.mapo.project_fundegi.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityLogin11Binding
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.ui.activity.PIN_
import com.bumptech.glide.Glide

class LOGIN_1_1 : AppCompatActivity() {

    private lateinit var binding : ActivityLogin11Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin11Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun goNext(v: View) {
        with(binding) {
            if(emailEdit.text.isNotBlank() && pwEdit.text.isNotBlank()) {
                gif.visibility = View.VISIBLE
                // wheather pin_number exist
                Handler(Looper.getMainLooper()).postDelayed({
                    if(AppPrefs.getPin(this@LOGIN_1_1)!!.isNotBlank()) {
                        startActivity(Intent(this@LOGIN_1_1, PIN_::class.java))
                    } else {
                        startActivity(Intent(this@LOGIN_1_1, LOGIN_1_2::class.java))
                    }
                }, 300L)
            } else Toast.makeText(this@LOGIN_1_1, "이메일 주소, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            emailEdit.text = null
            pwEdit.text = null
            gif.visibility = View.GONE
        }
    }

}