package don.coffee.coffeeware

import java.io.Serializable


open class Producto(

    var id:Int,
    var producto_type:String,
    var nombre: String,
    var precioBase: Double,
    var categoria:Categoria,
    var image: Int,
    var descripcion: String,
    var ingredientesBase: ArrayList<PorcionIngredienteBase>,
    var ingredientesExtra: ArrayList<IngredienteExtra>
): Serializable