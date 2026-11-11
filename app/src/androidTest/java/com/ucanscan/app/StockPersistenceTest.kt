package com.ucanscan.app

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ucanscan.app.data.local.StockEntity
import com.ucanscan.app.data.local.UCanScanDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockPersistenceTest {

    @Test
    fun comprobarPersistencia() = runBlocking {

        val contexto = InstrumentationRegistry
            .getInstrumentation().targetContext

        val nombreBD = "prueba_persistencia"

        // Comenzar con una base de prueba vacia
        contexto.deleteDatabase(nombreBD)

        var db = Room.databaseBuilder(
            contexto,
            UCanScanDatabase::class.java,
            nombreBD
        ).build()

        try {
            // Guardar 10 unidades
            val stock = StockEntity(
                productoId = "P1",
                sucursalId = "S1",
                cantidad = 10,
                stockMinimo = 5
            )

            db.stockDao().guardarStock(stock)

            // Comprobar que se guardaron 10
            assertEquals(
                10,
                db.stockDao().buscarStock("P1", "S1")?.cantidad
            )

            // Actualizar a 7 unidades
            db.stockDao().guardarStock(
                stock.copy(cantidad = 7)
            )

            // Cerrar y volver a abrir la base
            db.close()

            db = Room.databaseBuilder(
                contexto,
                UCanScanDatabase::class.java,
                nombreBD
            ).build()

            // Deben seguir existiendo 7 unidades
            assertEquals(
                7,
                db.stockDao().buscarStock("P1", "S1")?.cantidad
            )

        } finally {
            db.close()
            contexto.deleteDatabase(nombreBD)
        }
    }
}