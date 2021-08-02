package co.kr.mapo.project_fundegi.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterCard1Binding

class REGISTER_CARD_1 : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterCard1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCard1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        with(binding) {
            backButton.setOnClickListener {
                finish()
            }
            nextButton.setOnClickListener {
                if(checkBox1.isChecked && checkBox2.isChecked && checkBox3.isChecked &&checkBox4.isChecked) {
                    startActivity(Intent(this@REGISTER_CARD_1, REGISTER_CARD_2::class.java))
                    finish()
                } else Toast.makeText(this@REGISTER_CARD_1, "필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
            checkBoxAll.setOnClickListener {
                if(checkBoxAll.isChecked) {
                    checkBox1.isChecked = true
                    checkBox2.isChecked = true
                    checkBox3.isChecked = true
                    checkBox4.isChecked = true
                    checkBox5.isChecked = true
                } else {
                    checkBox1.isChecked = false
                    checkBox2.isChecked = false
                    checkBox3.isChecked = false
                    checkBox4.isChecked = false
                    checkBox5.isChecked = false
                }
            }
        }
    }
}