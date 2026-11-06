package com.ucanscan.app.viewmodel

import androidx.lifecycle.ViewModel
import com.ucanscan.app.data.DatosEjemplo

// Resumen de todas las tiendas.
data class ResumenGeneral(
    val productos: Int,
    val sucursales: Int,
    val unidades: Int,
    val alertas: Int,
    val sinStock: Int
)

// Resumen de una tienda.
data class ResumenSucursal(
    val nombre: String,
    val unidades: Int,
    val alertas: Int,
    val sinStock: Int
)

class ReportsViewModel : ViewModel() {

    // Calcula el resumen de toda la empresa.
    fun obtenerResumenGeneral(): ResumenGeneral {
        val stocks = DatosEjemplo.stocks

        return ResumenGeneral(
            productos = DatosEjemplo.productos.size,
            sucursales = DatosEjemplo.sucursales.size,
            unidades = stocks.sumOf { it.cantidad },
            alertas = stocks.count {
                it.cantidad <= it.stockMinimo
            },
            sinStock = stocks.count {
                it.cantidad == 0
            }
        )
    }

    // Calcula los resultados de cada sucursal.
    fun obtenerResumenSucursales(): List<ResumenSucursal> {

        return DatosEjemplo.sucursales.map { sucursal ->

            val stocks = DatosEjemplo.stocks.filter {
                it.sucursalId == sucursal.id
            }

            ResumenSucursal(
                nombre = sucursal.nombre,
                unidades = stocks.sumOf { it.cantidad },
                alertas = stocks.count {
                    it.cantidad <= it.stockMinimo
                },
                sinStock = stocks.count {
                    it.cantidad == 0
                }
            )
        }
    }
}