package don.coffee.coffeeware

import kotlinx.android.parcel.Parcelize

@Parcelize
open class ProductoPersonalizado (
    var idPersonalizado: Int,
    var tipoPersonalizado:String,
    var nombrePersonalizado: String,
    var precioBasePersonalizado: Double,
    var categoriaPersonalizado: Categoria,
    var imagePersonalizado: Int,
    var descripcionPersonalizado: String,
    var ingredientesBasePersonalizado: ArrayList<PorcionIngredienteBase>,
    var ingredientesExtraPersonalizado: ArrayList<IngredienteExtra>,
    var ordenPersonalizado: Orden,
    var notaPersonalizado: String,
    var precioExtraPersonalizado: Double,
    var cantidadPersonalizado: Int
): Producto(idPersonalizado,tipoPersonalizado,nombrePersonalizado, precioBasePersonalizado,categoriaPersonalizado, imagePersonalizado, descripcionPersonalizado, ingredientesBasePersonalizado, ingredientesExtraPersonalizado)