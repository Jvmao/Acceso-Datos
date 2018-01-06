package com.josevicente.firebaseproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josevicente.firebaseproject.Modelo.Producto;
import java.util.List;
import com.josevicente.firebaseproject.R;
import com.squareup.picasso.Picasso;


public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolder>{
    //Declaramos las Variables
    private Context context;
    private List<Producto> productoList;

    //Obtenermos el contructor de las Variables
    public AdapterProductos(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Le pasamos el layout a adaptar, en este caso productos.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Pasamos el objeto y las variables instanciadas en ViewHolder
        Producto producto = productoList.get(position);
        String imagenIt = null;

        holder.nomProducto.setText(producto.getNproducto());
        holder.descProducto.setText(producto.getDesc());
        holder.catProducto.setText(producto.getCategoria());
        holder.precioProducto.setText(producto.getPrecio());
        holder.fechaProducto.setText(producto.getFecha());

        //Añadimos la imagen a cargar
        imagenIt = producto.getImagen();
        Picasso.with(context).load(imagenIt).into(holder.imageItem);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    //Declaramos el método siguiente
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Recogemos las Variables declaradas en productos.xml
        public ImageView imageItem;
        public TextView nomProducto;
        public TextView descProducto;
        public TextView catProducto;
        public TextView precioProducto;
        public TextView fechaProducto;

        public ViewHolder(View itemView,Context ctx) {
            super(itemView);

            //Instanciamos las variables
            context = ctx;
            imageItem = itemView.findViewById(R.id.imageItem);
            nomProducto = itemView.findViewById(R.id.nomProducto);
            descProducto = itemView.findViewById(R.id.descProducto);
            catProducto = itemView.findViewById(R.id.catProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
            fechaProducto = itemView.findViewById(R.id.fechaProducto);
        }
    }
}
