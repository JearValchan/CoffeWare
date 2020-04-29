package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_confirm_order.list_orden
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.producto_orden.*
import kotlinx.android.synthetic.main.producto_orden.view.*

class paymentActivity : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()
    var ingredientesBase = ArrayList<IngredienteBase>()
    var ingredientesExtra = ArrayList<IngredienteExtra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        var adaptador = paymentActivity.paymentAdaptor(this, productosPersonalizados, ingredientesBase, ingredientesExtra
        )
        list_orden.adapter = adaptador

        var intentMenu = Intent(this, MainActivity::class.java)

        btnCancelar.setOnClickListener{
            startActivity(intentMenu)
        }

        btnConfirm.setOnClickListener{
            
        }

        list_orden.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (list_ingredients.visibility == View.VISIBLE) list_ingredients.visibility =
                View.GONE else list_ingredients.visibility = View.VISIBLE
        }

    }

    private class paymentAdaptor:BaseAdapter{


        var context: Context? = null
        var productosPersonalizado = ArrayList<ProductoPersonalizado>()
        var ingredientesBase = ArrayList<IngredienteBase>()
        var ingredientesExtra = ArrayList<IngredienteExtra>()

        constructor(context: Context, productosPersonalizado: ArrayList<ProductoPersonalizado>, ingredientesBase: ArrayList<IngredienteBase>, ingredientesExtra: ArrayList<IngredienteExtra>) {
            this.context = context
            this.productosPersonalizado = productosPersonalizado
            this.ingredientesBase = ingredientesBase
            this.ingredientesExtra = ingredientesExtra
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = productosPersonalizado[position]
            var inflator = LayoutInflater.from(context)
            var vista = inflator.inflate(R.layout.activity_confirm_order, null)
            vista.product_name.setText(productoPersonalizado.nombre)
            vista.precio.setText((productoPersonalizado.precioExtra + productoPersonalizado.precioBase).toString())

            return vista
        }

        override fun getItem(position: Int): Any {
            return productosPersonalizado[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return productosPersonalizado.size
        }


    }

}
