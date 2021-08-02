package co.kr.mapo.project_fundegi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-13
 * @desc
 */

@Entity(tableName = "table_saving")
data class SavingEntity(
    @PrimaryKey(autoGenerate = true)
    val seq : Int = 0,
    val id : Int,
    val date : String,
    val money : Int,
    var studyTime : Int  = 0
)

@Entity(tableName = "table_inform_saving")
data class InformSavingEntity(
    @PrimaryKey(autoGenerate = true)
    val seq: Int = 0,
    val id : Int,
    val startDate : String,
    val money : Int,
    val goal : Int,
    var total : Int = 0,
    var endDate: String = ""
)

@Entity(tableName = "table_stamp")
data class StampEntity(
    @PrimaryKey
    val date : String,
    var isWaked : Boolean = false
)

@Entity(tableName = "table_study")
data class StudyEntity(
    @PrimaryKey
    val date : String,
    var time : Int
)

@Entity(tableName = "table_money")
data class SavingMoneyEntity(
    @PrimaryKey
    var money : Int = 0
)