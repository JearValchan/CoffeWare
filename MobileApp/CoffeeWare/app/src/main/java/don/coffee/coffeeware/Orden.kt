package don.coffee.coffeeware

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Orden (var cliente: String, var precioFinal: Double, var estadoOrden: Enum<EstadoEnum>): Parcelable
