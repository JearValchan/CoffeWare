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
import kotlinx.android.synthetic.main.activity_confirm_order.list_orden
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_personalizar_producto.*
import kotlinx.android.synthetic.main.producto_orden.*
import kotlinx.android.synthetic.main.producto_orden.view.*

class paymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val orden = intent.getParcelableExtra<Orden>("orden")

        var adaptador = paymentAdaptor(this, orden.productos!!)
        list_orden.adapter = adaptador

        var intentMenu = Intent(this, MainActivity::class.java)

        precioTotalPago.text = obtenerPrecioFinal().toString()

        btnCancelar.setOnClickListener{
            startActivity(intentMenu)
        }

        btnConfirm.setOnClickListener{
            
        }
    }

    fun obtenerPrecioFinal(): Double{
        var total: Double = 0.0

        for(x in SessionData.ordenActual){
            total += x.preciobase+x.precioExtra
        }
        return total
    }

    private class paymentAdaptor:BaseAdapter{
        var context: Context? = null
        var productosPersonalizado = ArrayList<ProductoPersonalizado>()

        constructor(context: Context, productosPersonalizado: ArrayList<ProductoPersonalizado>) {
            this.context = context
            this.productosPersonalizado = productosPersonalizado
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = productosPersonalizado[position]
            var inflator = LayoutInflater.from(context)
            var vista = inflator.inflate(R.layout.producto_orden, null)
            vista.product_name.text = productoPersonalizado.nombrePersonalizado
            vista.precio.text = (productoPersonalizado.precioExtra + productoPersonalizado.preciobase).toString()

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
