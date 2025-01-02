package com.example.weighttracking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CalendarDate::class], version = 1)
@TypeConverters(Converters::class)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun CalendarDao(): CalendarDao

    companion object {
        @Volatile
        private var Instance: CalendarDatabase? = null

        fun getDatabase(context: Context): CalendarDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CalendarDatabase::class.java, "calendar_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
