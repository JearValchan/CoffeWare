package don.coffee.coffeeware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_personalizar_producto.*

class PersonalizarProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_producto)

        val producto = intent.getParcelableExtra<Producto>("producto")
        nombreProducto.setText(producto.nombre)
        precioBase.setText(""+producto.categoria.nombre)
        precioTotal.setText(""+producto.precioBase)
    }

}
