package com.ucanscan.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ucanscan.app.data.local.StockEntity
import com.ucanscan.app.repository.StockRepository
import kotlinx.coroutines.flow.Flow

// Expone los datos de Room a las pantallas.
class StockRoomViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = StockRepository(application)

    // Flow emite una lista nueva cuando cambia el stock.
    val stocks: Flow<List<StockEntity>> = repository.stocks

    // Permite guardar cambios de inventario.
    suspend fun guardarStock(stock: StockEntity) {
        repository.guardarStock(stock)
    }
}