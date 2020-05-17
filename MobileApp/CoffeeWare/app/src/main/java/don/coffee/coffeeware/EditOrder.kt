package don.coffee.coffeeware
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_editar_ordenes.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ingrediente_view.view.*
import kotlinx.android.synthetic.main.ingrediente_view.view.remove
import kotlinx.android.synthetic.main.producto_editar_orden.view.*
import kotlinx.android.synthetic.main.producto_orden.view.*
import kotlinx.android.synthetic.main.producto_orden.view.precio
import kotlinx.android.synthetic.main.producto_orden.view.product_name
import kotlinx.android.synthetic.main.producto_view.view.*
import kotlinx.android.synthetic.main.viewlistaproductos.view.*

class EditOrder : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ordenes)

        productosPersonalizados = SessionData.ordenActual



        //Adaptador
        var adaptador = AdaptadorProductosPersonalizados(this, productosPersonalizados)
        list_ordenEdit.adapter = adaptador


        //Botón para confirmar edición
        //No lo envía a la base de datos
        var intentConfirmOrder = Intent(this, ConfirmOrder::class.java)
        btn_guardarcambios.setOnClickListener(){
            startActivity(intentConfirmOrder)
        }

        //Botón para añadir más productos
        var intentMain = Intent(this, MainActivity::class.java)
        btn_añadirproducto.setOnClickListener(){
            startActivity(intentMain)
        }

        //Botón para cancelar orden
        btn_cancelar.setOnClickListener(){
            productosPersonalizados.clear()
            startActivity(intentMain)
            adaptador!!.notifyDataSetChanged()
        }


    }

    fun personalizar(producto: Producto){
        val intent = Intent(this, PersonalizarProductoActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }




    inner class AdaptadorProductosPersonalizados : BaseAdapter {
        var productosPersonalizados = ArrayList<ProductoPersonalizado>()
        var contexto: Context? = null

        constructor(contexto: Context, productosPersonailzados: ArrayList<ProductoPersonalizado>) {
            this.contexto = contexto
            this.productosPersonalizados = productosPersonailzados

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = productosPersonalizados[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.producto_editar_orden, null)

            vista.product_name.setText(productoPersonalizado.nombrePersonalizado)
            vista.precio.setText(productoPersonalizado.precioBasePersonalizado.toString())


            //botonEliminarProductoP
            vista.remove.setOnClickListener {
            productosPersonalizados.remove(productosPersonalizados[position])
                this.notifyDataSetChanged()
            }

            //botonEditarProductoP
            vista.edit.setOnClickListener(){
                var productoTemp = SessionData.ordenActual[position]
                SessionData.ordenActual.removeAt(position)
                personalizar(productoTemp)
            }
            return vista
        }

        override fun getItem(position: Int): Any {
            return productosPersonalizados[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return productosPersonalizados.size
        }

    }

}