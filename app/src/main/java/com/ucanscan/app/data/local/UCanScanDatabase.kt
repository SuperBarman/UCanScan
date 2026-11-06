package com.ucanscan.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StockEntity::class, MovimientoEntity::class],
    version = 3,
    exportSchema = false
)
abstract class UCanScanDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao
    abstract fun movimientoDao(): MovimientoDao
}