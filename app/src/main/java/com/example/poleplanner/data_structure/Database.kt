package com.example.poleplanner.data_structure

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val database = AppDatabase.getInstance(context)
        database.let {
            GlobalScope.launch {
                val poseDao = it.poseDao
                poseDao.insertAll(InitialData.poses)

                val tagDao = it.tagDao
                tagDao.insertAll(InitialData.tags)

                Log.d("AppDatabaseCallback", "Initial data inserted")
            }
        }
    }
}

@Database(
    entities = [Pose::class, Tag::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val poseDao: PoseDao
    abstract val tagDao: TagDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .addCallback(AppDatabaseCallback(context))
                .build()
        }
    }
}
