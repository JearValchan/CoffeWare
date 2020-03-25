package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_personalizar_producto.*
import kotlinx.android.synthetic.main.ingrediente_view.view.*
import kotlinx.android.synthetic.main.producto_view.view.*
import java.io.Serializable

class PersonalizarProductoActivity : AppCompatActivity() {

    var adaptador: AdaptadorIngsBase? = null
    var adaptadorExtra: AdaptadorIngsExtra? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_producto)

        var bundle: Bundle? = intent.extras
        var nombre: String? = bundle?.getString("nombre")
        var preciobase: Double? = bundle?.getDouble("preciobase")
        var ingsbase: ArrayList<PorcionIngredienteBase>? = bundle?.getParcelableArrayList<PorcionIngredienteBase>("ingsbase")
        var ingsextra: ArrayList<IngredienteExtra>? = bundle?.getParcelableArrayList<IngredienteExtra>("ingsextra")

        textview_nombreprod.setText(nombre)
        textview_preciobase.setText("$preciobase")

        adaptador = AdaptadorIngsBase(this, ingsbase)
        listview_ingsbase.adapter = adaptador

        adaptadorExtra = AdaptadorIngsExtra(this, ingsextra)
        listview_ingsextra.adapter = adaptadorExtra
    }

    class AdaptadorIngsBase : BaseAdapter {
        var ingredientes = ArrayList<PorcionIngredienteBase>()
        var contexto: Context? = null

        constructor(contexto: Context, ingredientes: ArrayList<PorcionIngredienteBase>?) {
            this.contexto = contexto
            if (!ingredientes.isNullOrEmpty()) {
                this.ingredientes = ingredientes
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var ingBase = ingredientes[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            var nombreIngrediente: String? = ingBase.ingrediente.nombre
            var cantidadIngrediente: Int = ingBase.cantidad

            vista.textview_nombreing.setText("$nombreIngrediente")
            vista.textview_cantidad.setText("$cantidadIngrediente")

            return vista
        }

        override fun getItem(position: Int): Any {
            return ingredientes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ingredientes.size
        }

    }

    class AdaptadorIngsExtra : BaseAdapter {
        var extras = ArrayList<IngredienteExtra>()
        var contexto: Context? = null

        constructor(contexto: Context, extras: ArrayList<IngredienteExtra>?) {
            this.contexto = contexto
            if (!extras.isNullOrEmpty()) {
                this.extras = extras
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var extra = extras[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            var nombreIngrediente: String? = extra.nombre
            var cantidadIngrediente: Int = 0

            vista.textview_nombreing.setText("$nombreIngrediente")
            vista.textview_cantidad.setText("$cantidadIngrediente")

            return vista
        }

        override fun getItem(position: Int): Any {
            return extras[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return extras.size
        }

    }

}
