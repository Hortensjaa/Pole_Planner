package com.example.poleplanner.data_structure

import android.content.Context
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
                val poseDao = it.poseDao()
                val initialPoses = listOf(
                    Pose(name = "Gemini", description = "Description 1", difficulty = 1),
                    Pose(name = "Aysha", description = "Description 2", difficulty = 2),
                    Pose(name = "Fireman spin", difficulty = 3),
                    Pose(name = "Ballerina", description = "Description 4"),
                    Pose(name = "Brass Monkey"),
                    Pose(name = "Invert"),
                    Pose(name = "Jade"),
                    Pose(name = "Scorpio"),
                    Pose(name = "Iron X"),
                    Pose(name = "Pole sit"),
                    Pose(name = "Reverse grab spin"),
                    Pose(name = "Russian Layback"),
                )
                poseDao.insertAll(initialPoses)
            }
        }
    }
}

// future: allow user to add pose with name
// source: https://stackoverflow.com/questions/45912619/using-room-as-singleton-in-kotlin
open class SingletonHolder<T, A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}

@Database(entities = [Pose::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun poseDao(): PoseDao

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
            AppDatabase::class.java,
            "app_database.db")
            .addCallback(AppDatabaseCallback(it.applicationContext))
            .build()
    })
}
