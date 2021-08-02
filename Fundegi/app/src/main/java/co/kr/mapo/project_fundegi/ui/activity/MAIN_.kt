package co.kr.mapo.project_fundegi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityMainBinding
import co.kr.mapo.project_fundegi.system.Fundegi
import co.kr.mapo.project_fundegi.ui.Balloon
import co.kr.mapo.project_fundegi.ui.activity.my.MY_
import co.kr.mapo.project_fundegi.ui.adapter.ViewPagerAdapter
import co.kr.mapo.project_fundegi.ui.fragment.*
import co.kr.mapo.project_fundegi.ui.fragment.THEME_diet.Companion.newInstance
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import com.google.android.material.tabs.TabLayout
import com.skydoves.balloon.createBalloon
import com.skydoves.balloon.showAlignTop

class MAIN_ : AppCompatActivity() {

    private val pageNum = 4
    private lateinit var binding : ActivityMainBinding
    private val savingViewModel : SavingViewModel by viewModels()
    private lateinit var viewPagerAdapter : ViewPagerAdapter
    private var selectedPosition = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.contents, THEME_diet.newInstance())
                .commit()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        with(binding) {
            saving = savingViewModel
            lifecycleOwner = this@MAIN_

            btnQuestion.setOnClickListener {
                var tip = when(selectedPosition) {
                    1 -> "하루 동안 외식·배달 내역이 없을 때,설정한 금액이 저축됩니다."
                    2 -> "하루에 한번씩 얼리버드 아이콘을 누르면 설정한 금액이 저축됩니다."
                    3 -> "스톱워치로 측정된 시간이 1시간 이상이면 설정한 금액이 저축됩니다."
                    else -> "매일 오늘 날짜의 금액을 저축합니다."
                }
                val balloon = Balloon(tip,this@MAIN_).mainTip
                btnQuestion.showAlignTop(balloon)
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    selectedPosition = tab.position+1
                    savingViewModel.getSavingInform(selectedPosition)
                    when (tab.position) {
                        0 -> {
                            btnTheme.setText(R.string.theme_diet)
                            with(labelFirst) {
                                text = "운동"
                                visibility = View.VISIBLE
                            }
                            with(labelSecond) {
                                text = "커피"
                                visibility = View.VISIBLE
                            }
                            ft.replace(R.id.contents, newInstance())
                        }
                        1 -> {
                            with(labelFirst) {
                                text = "금연"
                                visibility = View.VISIBLE
                            }
                            with(labelSecond) {
                                text = "금주"
                                visibility = View.VISIBLE
                            }
                            btnTheme.setText(R.string.theme_earlyBird)
                            ft.replace(R.id.contents, THEME_wakeup.newInstance())
                        }
                        2 -> {
                            labelFirst.visibility = View.GONE
                            labelSecond.visibility = View.GONE
                            btnTheme.setText(R.string.theme_study)
                            ft.replace(R.id.contents, THEME_study_progress.newInstance())
                        }
                        3 -> {
                            labelFirst.visibility = View.GONE
                            labelSecond.visibility = View.GONE
                            btnTheme.setText(R.string.theme_daily)
                            ft.replace(R.id.contents, THEME_daily_progress.newInstance())
                        }
                    }
                    ft.commit()
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewPagerAdapter = ViewPagerAdapter(savingViewModel, savingViewModel.savings.value)
        savingViewModel.getSavingInform(selectedPosition)
        with(binding) {
            // viewpager
            with(pager) {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                //pager.offscreenPageLimit = 4
                adapter = viewPagerAdapter
            }
            with(indicator) {
                visibility = if(viewPagerAdapter.itemCount == 1) View.GONE else View.VISIBLE
                createIndicators(pageNum,0)
                setViewPager(pager)
            }
        }
    }

    fun myPageButton(v: View) {
        startActivity(Intent(this, MY_::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)                  // 태스크를 백그라운드로 이동
        finishAndRemoveTask()                 // 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid()) // 앱 프로세스 종료
    }

}
