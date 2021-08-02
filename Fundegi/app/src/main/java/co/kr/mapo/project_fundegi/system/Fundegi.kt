package co.kr.mapo.project_fundegi.system

import android.app.Application
import android.util.Log
import android.widget.Toast
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.InformSavingEntity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-06-28
 * @desc
 */

class Fundegi : Application() {

    var savingList = mutableListOf<InformSavingEntity>()

    override fun onCreate() {
        super.onCreate()
        /*
        Log.e("[아이디]", R.raw.fundegi_if_no_acount.toString())
        Log.e("[아이디]", R.raw.fundegi_loading.toString())
        Log.e("[아이디]", R.raw.fundegi_splash.toString())
        Log.e("[아이디]", R.raw.fundegi_complete.toString())
         */
    }

    companion object {
        private var _fundegi : Fundegi? = null
        private val fundegi : Fundegi get() = _fundegi!!
        fun getInstance() : Fundegi {
            return if(_fundegi != null) fundegi
            else {
                _fundegi = Fundegi()
                fundegi
            }
        }
    }

}