package com.ucanscan.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ucanscan.app.model.TipoMovimiento
import com.ucanscan.app.repository.MovimientosRoomRepository

class MovimientosRoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovimientosRoomRepository(application)
    val movimientos = repository.movimientos

    suspend fun registrar(tipo: TipoMovimiento, productoId: String, sucursalId: String, cantidad: Int) {
        repository.registrar(tipo, productoId, sucursalId, cantidad)
    }
}