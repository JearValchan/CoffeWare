package don.coffee.coffeeware

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_confirm_order.view.*
import kotlinx.android.synthetic.main.activity_confirm_order.view.list_orden
import kotlinx.android.synthetic.main.producto_orden.view.*
import java.text.FieldPosition

class ConfirmOrder : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        var adaptador = AdapterConfirmar(this, productosPersonalizados)
        list_orden.adapter = adaptador
    }

    private class AdapterConfirmar:BaseAdapter {

        var context: Context? = null
        var productosPersonalizado = ArrayList<ProductoPersonalizado>()

        constructor(context: Context, productosPersonalizado: ArrayList<ProductoPersonalizado>) {
            this.context = context
            this.productosPersonalizado = productosPersonalizado
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
