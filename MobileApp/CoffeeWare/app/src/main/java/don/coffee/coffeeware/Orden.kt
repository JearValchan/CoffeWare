package don.coffee.coffeeware

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Orden (var cliente: String, var productoPersonalizado: ProductoPersonalizado , var precioFinal: Double, var estadoOrden: Enum<EstadoEnum>): Parcelable
