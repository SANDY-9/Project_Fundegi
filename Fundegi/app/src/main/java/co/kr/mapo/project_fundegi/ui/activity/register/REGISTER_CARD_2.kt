package co.kr.mapo.project_fundegi.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.kr.mapo.project_fundegi.R

class REGISTER_CARD_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_card_2)
    }

    fun goScan(v:View) {
        startActivity(Intent(this, REGISTER_CARD_3::class.java))
    }
}