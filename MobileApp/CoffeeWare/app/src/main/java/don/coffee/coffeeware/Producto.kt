package don.coffee.coffeeware

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


open class Producto(
    open var nombre: String?,
    open var categoria: Categoria?,
    open var image: Int,
    open var descripcion: String?,
    open var precioBase: Double,
    open var ingredientesBase: ArrayList<PorcionIngredienteBase>,
    open var ingredientesExtra: ArrayList<IngredienteExtra>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Categoria::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        TODO("ingredientesBase"),
        TODO("ingredientesExtra")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeParcelable(categoria, flags)
        parcel.writeInt(image)
        parcel.writeString(descripcion)
        parcel.writeDouble(precioBase)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}