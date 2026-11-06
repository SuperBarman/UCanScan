package com.ucanscan.app.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object UCanScanMigrations {
    // Conserva los registros de stock existentes en Room 1.
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `movimientos` (
                    `id` TEXT NOT NULL,
                    `productoId` TEXT NOT NULL,
                    `sucursalId` TEXT NOT NULL,
                    `usuarioId` TEXT NOT NULL,
                    `tipo` TEXT NOT NULL,
                    `cantidad` INTEGER NOT NULL,
                    `fechaMillis` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
            """.trimIndent())
        }
    }

    // Si ya tienes Room 2, agrega la informacion de discrepancias sin borrar movimientos.
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE `movimientos` ADD COLUMN `stockAnterior` INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE `movimientos` ADD COLUMN `stockNuevo` INTEGER NOT NULL DEFAULT 0")
        }
    }
}