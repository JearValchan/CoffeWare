package com.example.testapplication

import java.io.Serializable

class Producto {
    var idProducto: Int = 0
    lateinit var producto_type: String
    lateinit var nombre: String
    var preciobase: Float = 0f
    var id_categoria: Int = 0
}