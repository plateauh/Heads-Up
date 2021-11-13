package com.najed.headsupv2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Celeb::class],version = 1,exportSchema = false)

abstract class CelebsDatabase: RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: CelebsDatabase? = null

        fun getInstance(context: Context): CelebsDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CelebsDatabase::class.java,
                    "celebs_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }

    }
    abstract fun celebDAO(): CelebDAO
}