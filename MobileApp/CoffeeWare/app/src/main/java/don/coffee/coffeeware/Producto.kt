package don.coffee.coffeeware

import java.io.Serializable

open class Producto(
    var nombre: String,
    var categoria: Categoria,
    var image: Int,
    var descripcion: String,
    var precioBase: Double,
    var ingredientesBase: ArrayList<PorcionIngredienteBase>,
    var ingredientesExtra: ArrayList<IngredienteExtra>
): Serializable