package don.coffee.coffeeware

import java.io.Serializable

data class Producto(var nombre: String,
                    var image: Int,
                    var descripcion: String,
                    var categoria: Categoria,
                    var precioBase: Double,
                    var ingredientesBase: ArrayList<PorcionIngredienteBase>,
                    var ingredientesExtra: ArrayList<IngredienteExtra>): Serializable