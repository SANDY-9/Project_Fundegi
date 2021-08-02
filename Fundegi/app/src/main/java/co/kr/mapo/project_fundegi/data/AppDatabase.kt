package co.kr.mapo.project_fundegi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.kr.mapo.project_fundegi.data.repository.SavingDAO

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-13
 * @desc
 */

@Database(
    entities = [
        SavingEntity::class,
        InformSavingEntity::class,
        StampEntity::class,
        StudyEntity::class,
        SavingMoneyEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savingDAO() : SavingDAO
    companion object {
        @Volatile var instance : AppDatabase? = null
        operator fun invoke(context: Context) : AppDatabase? {
            if(instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "fundegi.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}