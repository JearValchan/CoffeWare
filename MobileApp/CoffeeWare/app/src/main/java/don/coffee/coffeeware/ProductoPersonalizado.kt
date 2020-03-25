package don.coffee.coffeeware

import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ProductoPersonalizado (
    override var nombre: String?,
    override var categoria: Categoria?,
    override var image: Int,
    override var descripcion: String?,
    override var precioBase: Double,
    override var ingredientesBase: ArrayList<PorcionIngredienteBase>,
    override var ingredientesExtra: ArrayList<IngredienteExtra>,
    var nota: String,
    var precioExtra: Double,
    var cantidad: Int,
    var ingredientesQuitar: ArrayList<IngredienteBase>,
    var ingredientesAgregar: ArrayList<PorcionIngredienteExtra>
): Producto(nombre, categoria, image, descripcion, precioBase, ingredientesBase, ingredientesExtra)