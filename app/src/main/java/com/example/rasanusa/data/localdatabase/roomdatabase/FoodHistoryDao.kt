package com.example.rasanusa.data.localdatabase.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FoodHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (foodHistory: FoodHistory)

    @Query("SELECT * from foodHistory ORDER BY timestamp")
    fun getAllHistory() : LiveData<List<FoodHistory>>

    @Query("DELETE FROM foodHistory")
    fun deleteAllHistory()
}