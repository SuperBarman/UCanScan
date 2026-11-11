package com.ucanscan.app.data.local

import android.content.Context
import androidx.room.Room

// Mantiene una sola instancia de la base de datos.
object DatabaseProvider {

    @Volatile
    private var database: UCanScanDatabase? = null

    fun obtener(context: Context): UCanScanDatabase {

        return database ?: synchronized(this) {

            database ?: Room.databaseBuilder(
                context.applicationContext,
                UCanScanDatabase::class.java,
                "ucanscan_database"
            ).addMigrations(UCanScanMigrations.MIGRATION_1_2, UCanScanMigrations.MIGRATION_2_3).build().also {
                database = it
            }
        }
    }
}