package don.coffee.coffeeware

import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredienteExtra(var precio: Double, override var nombre: String?): Ingrediente(nombre)