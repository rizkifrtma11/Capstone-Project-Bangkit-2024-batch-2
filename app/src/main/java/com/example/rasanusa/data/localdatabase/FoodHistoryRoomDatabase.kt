package com.example.rasanusa.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rasanusa.config.Converters

@Database(entities = [FoodHistory::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodHistoryRoomDatabase : RoomDatabase() {

    abstract fun foodHistoryDao(): FoodHistoryDao

    companion object{
        @Volatile
        private var INSTANCE: FoodHistoryRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodHistoryRoomDatabase {
            if (INSTANCE == null){
                synchronized(FoodHistoryRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FoodHistoryRoomDatabase::class.java, "rasanusa.db").build()
                }
            }
            return INSTANCE as FoodHistoryRoomDatabase
        }
    }
}