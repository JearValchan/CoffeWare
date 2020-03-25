package don.coffee.coffeeware

import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredienteBase(override var nombre: String?): Ingrediente(nombre)