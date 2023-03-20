package com.incava.notyourfaultkotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.incava.notyourfaultkotlin.data.Item


@Dao
interface ShelterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //중복데이터 방지.
    fun insertShelterData(item : Item)

    @Update
    fun updateShelterData(item : Item)

    @Delete
    fun deleteShelterData(item : Item)

    @Query("SELECT * FROM Item ")
    fun getAllShelterData() : List<Item>

    @Query("SELECT COUNT(*) FROM Item")
    fun getUserCount(): Int
}