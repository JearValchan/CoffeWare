package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_confirm_order.view.*
import kotlinx.android.synthetic.main.activity_confirm_order.view.list_orden
import kotlinx.android.synthetic.main.ingredient_view.view.*
import kotlinx.android.synthetic.main.producto_orden.*
import kotlinx.android.synthetic.main.producto_orden.view.*
import java.text.FieldPosition

class ConfirmOrder : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()
    var ingredientesBase = ArrayList<IngredienteBase>()
    var ingredientesExtra = ArrayList<IngredienteExtra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        var adaptador = AdapterConfirmar(this, productosPersonalizados, ingredientesBase, ingredientesExtra)
        list_orden.adapter = adaptador

        var intentMenu = Intent(this, MainActivity::class.java)

        menuBtn.setOnClickListener{
            startActivity(intentMenu)
        }

        cancelBtn.setOnClickListener{
            productosPersonalizados.clear()
        }

        list_orden.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (ingredientsList.visibility == VISIBLE) ingredientsList.visibility = GONE else ingredientsList.visibility = VISIBLE
        }
    }

    private class AdapterConfirmar:BaseAdapter {

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

            var listaIngredientes = vista.ingredientsList

            for (ingrediente in productoPersonalizado.ingredientesExtra){
                var ingr = inflator.inflate(R.layout.ingredient_view, null)
                ingr.ingredientName.setText(ingrediente.nombre)
            }

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
