package co.kr.mapo.project_fundegi.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import co.kr.mapo.project_fundegi.data.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-13
 * @desc
 */

@Dao
interface SavingDAO {

    // ----- insert
    // satart saving
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun startSaving(settingEntity: InformSavingEntity)

    // save saving
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSaving(savingEntity: SavingEntity)

    // save Wakeup Stamp
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStamp(stampEntity: StampEntity)

    // save Study Time
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStudy(studyEntity: StudyEntity)

    // save Study Time
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoney(savedMoneyEntity: SavingMoneyEntity)


    // ----- select
    // get all saving
    @Query("SELECT * FROM table_inform_saving WHERE endDate = ''")
    fun getAllSaving() : LiveData<List<InformSavingEntity>>

    @Query("SELECT * FROM table_inform_saving ORDER BY endDate ASC")
    fun getAllSavingsList() : LiveData<List<InformSavingEntity>>

    @Query("SELECT * FROM table_inform_saving WHERE endDate = ''")
    suspend fun getAllSavings() : MutableList<InformSavingEntity>

    @Query("SELECT id FROM table_inform_saving WHERE endDate = ''")
    fun getAllSavingIDs() : List<Int>

    // get saving per money
    @Query("SELECT money FROM table_inform_saving WHERE id = :id and endDate = ''")
    suspend fun getSaveMoney(id: Int) : Int

    // get saving(isSaving)
    @Query("SELECT * FROM table_inform_saving WHERE id = :id and endDate = ''")
    suspend fun getSaving(id: Int) : InformSavingEntity

    // get saving start_date
    @Query("SELECT startDate FROM table_inform_saving WHERE id = :id and endDate = ''")
    suspend fun getSavingStart(id: Int) : String

//    // get saved total money
//    @Query("SELECT SUM(money) FROM table_saving")
//    fun getTotalSavingMoney() : LiveData<Int>
    // get saved total money
    @Query("SELECT money FROM table_money")
    fun getTotalSavingMoney() : LiveData<Int>

    // get saved total money
    @Query("SELECT money FROM table_money")
    fun getMoneyTable() : List<Int>

    // get saved total money(kind of)
    @Query("SELECT SUM(money) FROM table_saving WHERE id = :id")
    fun getSavingMoney(id: Int) : LiveData<Int>

    // get goal money(kind of)
    @Query("SELECT goal FROM table_inform_saving WHERE id = :id and endDate = ''")
    fun getGoalMoney(id: Int) : LiveData<Int>

    // get today study-time
    @Query("SELECT time FROM table_study WHERE date = :date")
    fun getTodayStudyTime(date: String) : Int
    @Query("SELECT time FROM table_study WHERE date = :date")
    fun getStudyTime(date: String) : LiveData<Int>

    @Query("SELECT * FROM table_saving WHERE id = :id")
    fun getTimeLine(id: Int) : LiveData<List<SavingEntity>>

    @Query("SELECT startDate FROM table_inform_saving WHERE id = :id and endDate = ''")
    fun getStartDate(id: Int) : LiveData<String>

    @Query("SELECT endDate FROM table_inform_saving WHERE seq = :seq")
    fun getEndDate(seq: Int) : LiveData<String>

    @Query("SELECT seq FROM table_inform_saving WHERE id = :id AND startDate= :startDate")
    fun getSeq(id: Int, startDate:String) : Int


    // ----- delete
    @Query("DELETE FROM table_saving WHERE id = :id")
    suspend fun deleteSaving(id: Int)
    @Query("DELETE FROM table_inform_saving WHERE seq = :seq")
    suspend fun deleteSetting(seq: Int)

    // ----- update
    @Query("UPDATE table_inform_saving SET total = total+:money WHERE id = :id and endDate = ''")
    suspend fun updateSaveMoney(id: Int, money: Int)
    // end saving
    @Query("UPDATE table_inform_saving SET endDate = :endDate WHERE seq = :seq")
    suspend fun updateEndSaving(seq: Int, endDate : String)

    @Query("UPDATE table_inform_saving SET money = :money, goal = :goal  WHERE seq = :seq")
    suspend fun updateSaving(seq: Int, money: Int, goal: Int)

    @Query("UPDATE table_money SET money = money + :money")
    suspend fun addSavingMoney(money: Int)

}