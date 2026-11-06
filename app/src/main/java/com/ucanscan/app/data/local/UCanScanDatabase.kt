package com.ucanscan.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StockEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UCanScanDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao
}