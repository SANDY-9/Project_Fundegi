package co.kr.mapo.project_fundegi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.AppDatabase
import co.kr.mapo.project_fundegi.data.SavingMoneyEntity
import co.kr.mapo.project_fundegi.data.repository.SavingRepositoryImpl
import co.kr.mapo.project_fundegi.databinding.ActivitySplashBinding
import co.kr.mapo.project_fundegi.system.AppPrefs
import co.kr.mapo.project_fundegi.system.Fundegi
import co.kr.mapo.project_fundegi.ui.activity.login.LOGIN_1
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SPLASH_ : AppCompatActivity() {

    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.invoke(this)!!
        DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        CoroutineScope(Dispatchers.IO).launch {
            val fundegi = Fundegi.getInstance()
            val dao = db.savingDAO()
            fundegi.savingList = dao.getAllSavings()
            if(dao.getMoneyTable().isEmpty()) dao.insertMoney(SavingMoneyEntity(0))
        }
        Handler(Looper.getMainLooper()).postDelayed({
            // settingEntitiy 가져오기

            var intent = if (AppPrefs.isLogined(this)) Intent(this, PIN_::class.java)
                                else Intent(this, LOGIN_1::class.java)
            startActivity(intent)
            finish()
        }, 1400L)
    }

}