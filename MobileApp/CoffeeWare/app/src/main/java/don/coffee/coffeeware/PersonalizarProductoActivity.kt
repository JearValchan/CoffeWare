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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_personalizar_producto.*
import kotlinx.android.synthetic.main.ingrediente_view.view.*
import kotlinx.android.synthetic.main.producto_view.view.*
import java.io.Serializable

class PersonalizarProductoActivity : AppCompatActivity() {

    var adaptador: AdaptadorIngsBase? = null
    var porcionesIngsBase = ArrayList<PorcionIngredienteBase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_producto)

        val bundle = intent.extras
        var ingredientesExtra: ArrayList<IngredienteExtra>


        if (bundle != null){
            //iv_pelicula_imagen.setImageResource(bundle.getInt("header"))
            var ser: Serializable? = bundle.getSerializable("producto")
            var prod: Producto = ser as Producto

            textview_nombreprod.setText(prod.nombre)
            textview_preciobase.setText("${prod.precioBase}")
            //textview_preciopzado.setText()

            ingredientesExtra = prod.ingredientesExtra
            porcionesIngsBase = prod.ingredientesBase

            adaptador = AdaptadorIngsBase(this, porcionesIngsBase)
            gridview_productos.adapter = adaptador

        }

    }

    class AdaptadorIngsBase : BaseAdapter {
        var ingredientes = ArrayList<PorcionIngredienteBase>()
        var contexto: Context? = null

        constructor(contexto: Context, ingredientes: ArrayList<PorcionIngredienteBase>) {
            this.contexto = contexto
            this.ingredientes = ingredientes
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var ingBase = ingredientes[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            //vista.textview_nombreing.setText(ingBase.c)
            //vista.textview_descripcion.setText(producto.descripcion)

            /*vista.btn_producto.setOnClickListener{
                var nombre = vista.textview_nombre.text
                val iterator = productos.iterator()

                iterator.forEach {
                    if(it.nombre.equals(nombre)){
                        var prod: Producto = it
                        val intent = Intent(contexto, PersonalizarProductoActivity::class.java)
                        intent.putExtra("producto",prod)
                        contexto!!.startActivity(intent)
                    }
                }

            }*/

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

}
