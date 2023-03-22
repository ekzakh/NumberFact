package com.ekzak.numberfact.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers_table ORDER BY date ASC")
    suspend fun allNumbers(): List<NumberCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(numberCache: NumberCache)

    @Query("SELECT * FROM numbers_table WHERE number = :number")
    suspend fun numberFact(number: String): NumberCache?  //todo check what happened if not exist element
}
