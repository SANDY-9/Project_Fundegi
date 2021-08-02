package co.kr.mapo.project_fundegi.data.repository

import androidx.lifecycle.LifecycleOwner
import co.kr.mapo.project_fundegi.data.SavingEntity
import kotlinx.coroutines.Deferred
import java.util.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-13
 * @desc
 */
interface SavingRepository {

    // start saving
    fun startDietSaving(goal:Int, money:Int)
    fun startWakeUpSaving(goal:Int, money:Int)
    fun startStudySaving(goal:Int, money:Int)
    fun start365Saving(goal: Int)

    // saving
    fun saveDaily()
    fun saveStamp(isStamped : Boolean)
    fun saveStudyTime(time: Int)

    // end saving(total money=goal money)
    fun stopEndSaving(id: Int)
    fun continueEndSaving(id: Int, money: Int, goal: Int)

    // delete Saving
    fun deleteSaving(id: Int, seq: Int, total: Int)

}