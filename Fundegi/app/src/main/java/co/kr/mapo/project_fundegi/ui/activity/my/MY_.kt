package co.kr.mapo.project_fundegi.ui.activity.my

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.AppDatabase
import co.kr.mapo.project_fundegi.data.repository.SavingRepositoryImpl
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.ui.activity.login.LOGIN_1
import co.kr.mapo.project_fundegi.ui.activity.login.LOGIN_1_2

class MY_ : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
    }

    fun onClick(v: View) {
        var intent : Intent? = null
        when(v.id) {
            R.id.button_menu1 -> {
                intent= Intent(this, MY_manage::class.java)
            }
            R.id.button_menu2 -> {
                //오늘저축하기
                SavingRepositoryImpl(AppDatabase.instance!!.savingDAO()).saveDaily()
                Toast.makeText(this, "저축 데이터가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            R.id.button_menu3 -> {
                return
            }
            R.id.button_menu4 -> {
                intent = Intent(this, LOGIN_1_2::class.java)
            }
            R.id.button_logout -> {
                AppPrefs.logoutApp(this)
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                intent = Intent(this, LOGIN_1::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                finish()
            }
        }
        startActivity(intent)
    }

    fun goBack(v: View) {
        finish()
    }
}