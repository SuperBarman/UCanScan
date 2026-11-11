package com.ucanscan.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    @Insert
    suspend fun insertar(movimiento: MovimientoEntity)

    @Query("SELECT * FROM movimientos ORDER BY fechaMillis DESC")
    fun observarMovimientos(): Flow<List<MovimientoEntity>>
}