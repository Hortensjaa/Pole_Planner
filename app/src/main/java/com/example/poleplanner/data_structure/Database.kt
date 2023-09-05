package com.example.poleplanner.data_structure

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.poleplanner.data_structure.daos.DayDao
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.daos.PoseTagDao
import com.example.poleplanner.data_structure.daos.TagDao
import com.example.poleplanner.data_structure.models.Day
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.data_structure.references.PoseTagCrossRef
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        loadData()
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
        loadData()
    }

    private fun loadData() {
        val database = AppDatabase.getInstance(context)
        database.let {
            GlobalScope.launch {

                val ptdao = it.poseTagDao
                var i = 0
                for ((pose, tags) in InitialData.poses_with_tags) {
                    ptdao.insertPoseWithTags(pose, tags)
                    i++
                }
            }
        }
    }
}

@Database(
    entities = [Pose::class, Tag::class, PoseTagCrossRef::class, Day::class],
    version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val poseDao: PoseDao
    abstract val tagDao: TagDao
    abstract val poseTagDao: PoseTagDao
    abstract val dayDao: DayDao

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
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
