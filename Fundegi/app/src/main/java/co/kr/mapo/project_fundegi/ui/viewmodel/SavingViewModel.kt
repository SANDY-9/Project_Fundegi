package co.kr.mapo.project_fundegi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.mapo.project_fundegi.data.*
import co.kr.mapo.project_fundegi.data.repository.SavingRepositoryImpl
import co.kr.mapo.project_fundegi.system.ConvertUtils
import co.kr.mapo.project_fundegi.system.Fundegi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-12
 * @desc 저축 뷰모델
 */
class SavingViewModel : ViewModel() {

    private val dao = AppDatabase.instance!!.savingDAO()
    private val today = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA).format(Calendar.getInstance().time)
    val savings = MutableLiveData<MutableList<InformSavingEntity>>()    // viewPager
    val allSaving = MutableLiveData<MutableList<InformSavingEntity>>()  // recyclerView
    val totalSavingMoney = MutableLiveData<String>()
    val totalMoney = MutableLiveData<Int>()
    val goalMoney = MutableLiveData<Int>()
    val studyTime = MutableLiveData<String>()
    val timeLine = MutableLiveData<List<SavingEntity>>()
    val startDate = MutableLiveData<String?>()
    val day = MutableLiveData<String>()

    // observer
    private val allSavingObserver = Observer<List<InformSavingEntity>> {
        allSaving.value = it.toMutableList()
    }
    private val informSavingEntityObserver = Observer<List<InformSavingEntity>> {
        savings.value = it.toMutableList()
    }
    private val totalSavingMoneyObserver = Observer<Int> {
        totalSavingMoney.value = if(it != null) ConvertUtils.convertMoney(it) else "0"
    }
    private val studyTimeObserver = Observer<Int> {
        studyTime.value = if(it != null) ConvertUtils.convertHour(it) else "0시간"
    }
    private val goalMoneyObserver = Observer<Int> {
        goalMoney.value = it ?: 0
    }
    private val totalMoneyObserver = Observer<Int> {
        totalMoney.value = it ?: 0
    }
    private val startDateObserver = Observer<String> {
        startDate.value = it
        calculateDay(it)
    }
    private val timeLineObserver = Observer<List<SavingEntity>> {
        timeLine.value = it
    }

    init {
        savings.value = Fundegi.getInstance().savingList
        goalMoney.value = 0
        totalMoney.value = 0
        with(dao) {
            getAllSavingsList().observeForever(allSavingObserver)
            getAllSaving().observeForever(informSavingEntityObserver)
            getTotalSavingMoney().observeForever(totalSavingMoneyObserver)
        }
    }

    fun getSavingInform(id: Int) {
        with(dao) {
            //observe
            getTimeLine(id).observeForever(timeLineObserver)
            getGoalMoney(id).observeForever(goalMoneyObserver)
            getSavingMoney(id).observeForever(totalMoneyObserver)
            getStartDate(id).observeForever(startDateObserver)
            if(id == 3) getStudyTime(today).observeForever(studyTimeObserver)
        }
    }

    fun startSaving(id : Int, goal: Int, money: Int) {
        val savingRepository = SavingRepositoryImpl(dao)
        with(savingRepository) {
            when(id) {
                1 -> startDietSaving(goal, money)
                2 -> startWakeUpSaving(goal, money)
                3 -> startStudySaving(goal, money)
                4 -> start365Saving(goal)
            }
        }
    }

    fun removeData(index : Int) {
        val repository = SavingRepositoryImpl(dao)
        val savings = allSaving.value!!
        // remove saving
        with(savings) {
            repository.deleteSaving(this[index].id, this[index].seq, this[index].total)
            removeAt(index)
        }
    }

    // goal money = saved money
    fun endSaving(id: Int) {
        val savingRepository = SavingRepositoryImpl(dao)
        savingRepository.stopEndSaving(id)
    }

    fun continueSaving(id: Int, goal: Int, money: Int) {
        val savingRepository = SavingRepositoryImpl(dao)
        savingRepository.continueEndSaving(id, money, goal)
    }

    private fun calculateDay(date: String?) {
        day.value = if(date != null) {
            val date = date.substring(0, 10).replace("/", "").toInt()
            val today = today.replace("/", "").toInt()
            "${today - date + 1}"
        } else "0"
    }

    public override fun onCleared() {
        super.onCleared()
        with(dao) {
            getAllSavingsList().removeObserver(allSavingObserver)
            getAllSaving().removeObserver(informSavingEntityObserver)
            getTotalSavingMoney().removeObserver(totalSavingMoneyObserver)
            getStudyTime(today).removeObserver(studyTimeObserver)
            for (id in 1 until 5) {
                getTimeLine(id).removeObserver(timeLineObserver)
                getGoalMoney(id).removeObserver(goalMoneyObserver)
                getSavingMoney(id).removeObserver(totalMoneyObserver)
                getStartDate(id).removeObserver(startDateObserver)
            }
        }
    }

}