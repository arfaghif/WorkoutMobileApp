package com.example.workout.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HistoryDao {

//    @Query("SELECT * FROM history_table WHERE date LIKE :dateInput ORDER BY id ASC")
//    fun getHistoryOnDate(dateInput: Date): Flow<List<History>>

    @Query("SELECT * FROM history_table ORDER BY id ASC")
    fun getHistoryOnDate(): Flow<List<History>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

//    @Query("DELETE FROM word_table")
//    suspend fun deleteAll()
}