package com.najed.headsupv2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Celebrity")
data class Celeb (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "celeb_id")
    val id: Int,

    @ColumnInfo(name = "celeb_name")
    val name: String,

    @ColumnInfo(name = "celeb_taboo1")
    val taboo1: String,

    @ColumnInfo(name = "celeb_taboo2")
    val taboo2: String,

    @ColumnInfo(name = "celeb_taboo3")
    val taboo3: String
)