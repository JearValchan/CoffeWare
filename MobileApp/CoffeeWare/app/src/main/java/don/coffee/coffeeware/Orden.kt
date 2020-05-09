package don.coffee.coffeeware

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Orden (var ID:Int, var cliente: String, var ESTADO: Enum<EstadoEnum>,var preciofinal: Double): Parcelable
