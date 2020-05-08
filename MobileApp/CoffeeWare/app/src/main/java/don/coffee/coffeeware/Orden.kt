package don.coffee.coffeeware

import java.io.Serializable

data class Orden (var cliente: String, var precioFinal: Double, var estadoOrden: Enum<EstadoEnum>): Serializable
