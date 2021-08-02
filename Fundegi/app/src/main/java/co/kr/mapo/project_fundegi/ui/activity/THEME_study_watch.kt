package co.kr.mapo.project_fundegi.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import co.kr.mapo.project_fundegi.data.AppDatabase
import co.kr.mapo.project_fundegi.data.repository.SavingRepositoryImpl
import co.kr.mapo.project_fundegi.databinding.ActivityThemeStudyWatchBinding
import co.kr.mapo.project_fundegi.databinding.DialogStopwatchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class THEME_study_watch : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var timeValue = 0 // 실시간
    private var recordValue = 0 // 누적시간
    private lateinit var runTime : Runnable
    private lateinit var runRecord : Runnable

    private lateinit var binding : ActivityThemeStudyWatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeStudyWatchBinding.inflate(layoutInflater)

        //오늘 공부 시간 누적
        val date = Calendar.getInstance().time
        val today = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA).format(date)
        val dao = AppDatabase.instance!!.savingDAO()

        with(binding) {
            setContentView(root)
            //이전에 저장한 공부 시간 가져오기
            CoroutineScope(Dispatchers.IO).launch {
                val savedTime = dao.getTodayStudyTime(today)
                launch(Dispatchers.Main) {
                    savedTime?.let {  recordValue = savedTime }
                    tvRecord.text = if(savedTime != null) timeToText(savedTime) else timeToText(0)
                }
            }

            //오늘 날짜 출력
            tvDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(date)

            // 1초씩 증가하는 핸들러
            runTime = object : Runnable {
                override fun run() {
                    timeValue++
                    //스톱워치 시간 변환 실행
                    timeToText(timeValue)?.let {
                        tvWatch.text = it
                    }
                    handler.postDelayed(this, 1000) // 1초 딜레이
                }
            }

            runRecord = object : Runnable {
                override fun run() {
                    recordValue++
                    //스톱워치 시간 변환 실행
                    timeToText(recordValue)?.let {
                        tvRecord.text = it
                    }
                    handler.postDelayed(this, 1000) // 1초 딜레이
                }
            }

            // 스톱워치 시작 및 일시정지
            btStart.setOnClickListener {
                //스톱워치 시작
                with(handler) {
                    if (btStart.isChecked) {
                        Log.e("[TEST]", "시작")
                        post(runTime)
                        post(runRecord)
                    } else {
                        Log.e("[TEST]", "정지")
                        removeCallbacks(runTime)
                        removeCallbacks(runRecord)
                    }
                }
            }

            //종료
            btClose.setOnClickListener {
                //스레드 종료
                with(handler) {
                    removeCallbacks(runTime)
                    removeCallbacks(runRecord)
                }

                //다이얼로그
                val bindingDialog = DialogStopwatchBinding.inflate(LayoutInflater.from(this@THEME_study_watch))
                val builder = AlertDialog.Builder(this@THEME_study_watch).create()
                with(builder) {
                    apply {
                        setView(bindingDialog.root)
                        setCancelable(true)
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }.show()
                    with(bindingDialog) {
                        dialogRecord.text = timeToText(timeValue)
                        tvYes.setOnClickListener {
                            //누적 공부 시간 저장
                            val savingRepository = SavingRepositoryImpl(dao)
                            savingRepository.saveStudyTime(recordValue)
                            Toast.makeText(this@THEME_study_watch, "공부한 시간을 추가합니다.", Toast.LENGTH_SHORT).show()
                            dismiss()
                            finish()
                        }
                        tvNo.setOnClickListener {
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    //시간 변환
    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> {
                null
            }
            time == 0 -> {
                "00:00:00"
            }
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$02d:%2$02d:%3$02d".format(h, m, s)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //스레드 종료
        handler.removeCallbacks(runTime)
        handler.removeCallbacks(runRecord)
    }
}


