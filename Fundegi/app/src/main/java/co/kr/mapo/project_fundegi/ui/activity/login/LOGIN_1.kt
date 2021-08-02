package co.kr.mapo.project_fundegi.ui.activity.login

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityLogin1Binding
import com.bumptech.glide.Glide

class LOGIN_1 : AppCompatActivity() {

    private lateinit var binding:ActivityLogin1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login1)
    }

    fun goNext(v:View) {
        startActivity(Intent(this, LOGIN_1_1::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)						// 태스크를 백그라운드로 이동
        finishAndRemoveTask()						// 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid())	// 앱 프로세스 종료
    }
}