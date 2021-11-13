package com.najed.headsupv2.db

import androidx.room.*

@Dao
interface CelebDAO {

    @Insert
    fun addCeleb(celeb: Celeb)

    @Query("SELECT * FROM Celebrity")
    fun getAllCelebs(): List<Celeb>

    @Update
    fun updateCeleb(celeb: Celeb)

    @Delete
    fun deleteCeleb(celeb: Celeb)

}