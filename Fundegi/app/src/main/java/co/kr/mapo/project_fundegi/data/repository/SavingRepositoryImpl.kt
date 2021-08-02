package co.kr.mapo.project_fundegi.data.repository
import co.kr.mapo.project_fundegi.data.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class SavingRepositoryImpl(private val dao: SavingDAO) : SavingRepository {

    private val calendar = Calendar.getInstance()
    private val today = SimpleDateFormat("yyyy/MM/dd/hh:mm:ss", Locale.KOREA).format(calendar.time)

    override fun startDietSaving(goal: Int, money: Int) {
       CoroutineScope(Dispatchers.IO).launch {
           dao.startSaving(
               InformSavingEntity(
               id = 1,
               startDate = today,
               money = money,
               goal = goal,
               total = 0
           ))
       }
    }

    override fun startWakeUpSaving(goal: Int, money: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.startSaving(InformSavingEntity(
                id = 2,
                startDate = today,
                money = money,
                goal = goal,
                total = 0
            ))
        }
    }

    override fun startStudySaving(goal: Int, money: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.startSaving(InformSavingEntity(
                id = 3,
                startDate = today,
                money = money,
                goal = goal,
                total = 0
            ))
        }
    }

    override fun start365Saving(goal: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.startSaving(InformSavingEntity(
                id = 4,
                startDate = today,
                money = 0,
                goal = goal,
                total = 0
            ))
        }
    }

    override fun saveDaily() {
        CoroutineScope(Dispatchers.IO).launch {
            val today = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA).format(calendar.time)
            with(dao) {
                var money = 0
                var studyTime = 0
                val id = getAllSavingIDs()
                for(i in id) {
                    money = if(i != 4) getSaveMoney(i) else SimpleDateFormat("MMdd").format(calendar.time).toInt()
                    studyTime = if(i == 3) getTodayStudyTime(today) else 0
                    saveSaving(SavingEntity(
                        id = i,
                        date = today,
                        money = money,
                        studyTime = studyTime
                    ))
                    updateSaveMoney(i, money)
                    addSavingMoney(money)
                }
            }
        }
    }

    override fun saveStamp(isStamped: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val today = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA).format(calendar.time)
            dao.saveStamp(StampEntity(
                today,
                isStamped
            ))
        }
    }

    override fun saveStudyTime(time: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val today = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA).format(calendar.time)
            dao.saveStudy(StudyEntity(
                today,
                time
            ))
        }
    }

    override fun stopEndSaving(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            with(dao) {
                val startDate = getSavingStart(id)
                val seq = getSeq(id, startDate)
                deleteSaving(id)
                updateEndSaving(seq, today)
            }
        }
    }

    override fun continueEndSaving(id: Int, money: Int, goal: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            with(dao) {
                val startDate = getSavingStart(id)
                val seq = getSeq(id, startDate)
                updateSaving(seq, money, goal)
            }
        }
    }

    override fun deleteSaving(id: Int, seq: Int, total: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            with(dao) {
                if(total > 0) deleteSaving(id)
                deleteSetting(seq)
            }
        }
    }


}