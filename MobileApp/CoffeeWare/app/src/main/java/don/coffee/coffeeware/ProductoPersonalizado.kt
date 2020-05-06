package don.coffee.coffeeware

import java.io.Serializable


open class ProductoPersonalizado (
    id: Int,
    tipo:String,
    nombre: String,
    precioBase: Double,
    categoria: Categoria,
    image: Int,
    descripcion: String,
    ingredientesBase: ArrayList<PorcionIngredienteBase>,
    ingredientesExtra: ArrayList<IngredienteExtra>,
    var orden: Orden,
    var nota: String,
    var precioExtra: Double,
    var cantidad: Int
): Producto(id,tipo,nombre, precioBase,categoria, image, descripcion, ingredientesBase, ingredientesExtra)