package com.incava.notyourfaultkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.incava.notyourfaultkotlin.data.Item

@Database(entities = [Item::class], version = 1 , exportSchema = false)
abstract class ShelterDatabase : RoomDatabase() {
    abstract fun shelterDao(): ShelterDAO

    companion object {
        private var instance: ShelterDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ShelterDatabase? {
            if (instance == null)
                synchronized(ShelterDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShelterDatabase::class.java,
                        "shelter.db"
                    ).build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}