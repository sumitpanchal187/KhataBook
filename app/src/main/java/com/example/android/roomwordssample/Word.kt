package com.example.android.roomwordssample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "word_table")
data class Word(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "id") val id: Int ,
                @ColumnInfo(name = "date") val date:String,
                @ColumnInfo(name = "word") val word: String ,
                @ColumnInfo(name = "category") val category:String ,
                @ColumnInfo(name = "note") val note: String,
                @ColumnInfo(name = "img") val img: Int,
                val activityType: ActivityType
)