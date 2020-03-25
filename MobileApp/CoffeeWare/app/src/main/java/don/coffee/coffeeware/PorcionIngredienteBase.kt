package don.coffee.coffeeware

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class PorcionIngredienteBase(var cantidad:Int,var ingrediente:Ingrediente) : Parcelable