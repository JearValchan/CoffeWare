package don.coffee.coffeeware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_personalizar_producto.*

class PersonalizarProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_producto)

        val producto = intent.getParcelableExtra<Producto>("producto")
        val productoPersonalizado = ProductoPersonalizado(producto)
        productoPersonalizado.precioExtra = 25.0
        nombreProducto.setText(productoPersonalizado.nombrePersonalizado)
        precioBase.setText(""+productoPersonalizado.precioBase)
        precioTotal.setText(""+(productoPersonalizado.precioBasePersonalizado+productoPersonalizado.precioExtra))

        btn_anadirOrden.setOnClickListener {
            val intent = Intent(this, paymentActivity::class.java)
            intent.putExtra("productoPersonalizado",productoPersonalizado)
            startActivity(intent)
        }
    }



}
