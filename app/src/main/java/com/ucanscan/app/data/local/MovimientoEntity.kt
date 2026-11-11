package com.ucanscan.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class MovimientoEntity(
    @PrimaryKey val id: String,
    val productoId: String,
    val sucursalId: String,
    val usuarioId: String,
    val tipo: String,
    val cantidad: Int,
    val fechaMillis: Long,
    @ColumnInfo(defaultValue = "0") val stockAnterior: Int,
    @ColumnInfo(defaultValue = "0") val stockNuevo: Int
)