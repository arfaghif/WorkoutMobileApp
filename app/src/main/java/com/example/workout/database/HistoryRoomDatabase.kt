package com.example.workout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate

@Database(entities = arrayOf(History::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)

public abstract class HistoryRoomDatabase : RoomDatabase() {

    abstract fun HistoryDao(): HistoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HistoryRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java,
                    "history_database"
                ).addCallback(HistoryDatabaseCallback(scope)
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class HistoryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.HistoryDao())
                }
            }
        }

        suspend fun populateDatabase(historyDao: HistoryDao) {
            // Delete all content here.
            historyDao.deleteAll()

            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.format(Date(System.currentTimeMillis()))
            val time = Timestamp(System.currentTimeMillis())
            println(time)
            // Add dummy data
            var history = History(0,"Walking", date, time, time, 10.0f)
            historyDao.insert(history)

            history = History(0,"Walking", "2021-04-28", time, time, 1000.0f)
            historyDao.insert(history)

        }
    }
}