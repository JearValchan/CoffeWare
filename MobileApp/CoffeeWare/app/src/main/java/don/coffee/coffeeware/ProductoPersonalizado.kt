package don.coffee.coffeeware

import java.io.Serializable

open class ProductoPersonalizado (
    nombre: String,
    categoria: Categoria,
    image: Int,
    descripcion: String,
    precioBase: Double,
    ingredientesBase: ArrayList<PorcionIngredienteBase>,
    ingredientesExtra: ArrayList<IngredienteExtra>,
    var orden: Orden,
    var nota: String,
    var precioExtra: Double,
    var cantidad: Int
): Producto(nombre, categoria, image, descripcion, precioBase, ingredientesBase, ingredientesExtra)