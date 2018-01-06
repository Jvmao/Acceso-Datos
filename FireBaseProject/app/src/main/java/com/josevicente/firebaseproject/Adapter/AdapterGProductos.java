package com.josevicente.firebaseproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.josevicente.firebaseproject.ItemControl.GestItemActivity;
import com.josevicente.firebaseproject.Modelo.Producto;
import java.util.List;
import com.josevicente.firebaseproject.R;
import com.squareup.picasso.Picasso;


public class AdapterGProductos extends RecyclerView.Adapter<AdapterGProductos.ViewHolder>{
    //Declaramos las Variables
    private Context context;
    private List<Producto> productoList;

    //Añadimos las Variables para el Alert Dialog
    private AlertDialog.Builder alertDialogBilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private DatabaseReference databaseReference;
    public static final String userItem = "Productos";

    //Constructor de las Variables
    public AdapterGProductos(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Le pasamos el layout a adaptar, en este caso getproductos.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gestproducto,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Pasamos el objeto y las variables instanciadas en ViewHolder
        Producto producto = productoList.get(position);
        String imagenP = null;

        holder.nomGProducto.setText(producto.getNproducto());
        holder.descGProducto.setText(producto.getDesc());
        holder.catGProducto.setText(producto.getCategoria());
        holder.precioGProducto.setText(producto.getPrecio());

        imagenP = producto.getImagen();

        //Añadimos la librería Picasso para la carga de imagenes
        //File>>Project Structure>>app>>Dependencies>>+>>library dependencie>>Picasso
        Picasso.with(context).load(imagenP).into(holder.imagenGProducto);

    }


    @Override
    public int getItemCount() {
        return productoList.size();
    }

    //Declaramos el método ViewHolder y extendemos el Recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Declaramos las Variables public
        public ImageView imagenGProducto;
        public TextView nomGProducto;
        public TextView descGProducto;
        public TextView catGProducto;
        public TextView precioGProducto;
        public ImageButton imageUpdate;
        public ImageButton imageDelete;

        //Generamos el Constructor
        public ViewHolder(final View itemView,Context ctx) {
            super(itemView);

            //Instanciamos las Variables Declaradas
            context = ctx;
            imagenGProducto = itemView.findViewById(R.id.imagenProducto);
            nomGProducto = itemView.findViewById(R.id.nomGProducto);
            descGProducto = itemView.findViewById(R.id.descGProducto);
            catGProducto = itemView.findViewById(R.id.catGProducto);
            precioGProducto = itemView.findViewById(R.id.precioGProducto);
            imageUpdate = itemView.findViewById(R.id.imageUpdate);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            databaseReference = FirebaseDatabase.getInstance().getReference(userItem);


            //ImageButton para Actualizar el Usuario mediante un alertDialog
            imageUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //updateProducto(); //Le pasamos el método updateProducto()

                    //Instanciamos el AlertDialog
                    alertDialogBilder = new AlertDialog.Builder(context);
                    inflater = LayoutInflater.from(context);
                    final View view = inflater.inflate(R.layout.update_item,null); //Le pasamos el layout update_item.xml

                    //Instanciamos las variables creadas en update_item.xml:
                    //databaseReference = FirebaseDatabase.getInstance().getReference(userItem);
                    final EditText updateNombre = view.findViewById(R.id.updatePNombre);
                    final EditText updateDesc = view.findViewById(R.id.updateDesc);
                    final EditText updateCat = view.findViewById(R.id.updateCat);
                    final EditText updatePrecio = view.findViewById(R.id.updatePrecio);
                    Button btnUpdateProducto = view.findViewById(R.id.btnUpdateProducto);
                    Button btnUpdateSalir = view.findViewById(R.id.btnUpdateSalir);

                    //Definimos el query para mostrar los productos
                    Query showData = FirebaseDatabase.getInstance().getReference(userItem).orderByChild("nproducto")
                            .equalTo(nomGProducto.getText().toString());
                    showData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnap: dataSnapshot.getChildren()){
                                //Recogemos las variables a pasar al AlertDialog
                                Producto producto = dataSnap.getValue(Producto.class);
                                updateNombre.setText(producto.getNproducto().toString());
                                updateDesc.setText(producto.getDesc().toString());
                                updateCat.setText(producto.getCategoria().toString());
                                updatePrecio.setText(producto.getPrecio().toString());

                                Log.i("NOMBRE ",updateNombre.getText().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("CANCELADO","Actualización Cancelada");
                        }
                    });



                    // -----------------Actualizar Artículos mediante AlertDialog-------------------------------//

                    //Instanciamos el AlertDialog
                    alertDialogBilder.setView(view);
                    dialog = alertDialogBilder.create(); //Creamos el alertDialog
                    dialog.show(); //Nos muestra el AlertDialog

                    //Listener Botón Actualizar
                    btnUpdateProducto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String nombre = updateNombre.getText().toString();
                            final String desc = updateDesc.getText().toString();
                            final String cat = updateCat.getText().toString();
                            final String precio = updatePrecio.getText().toString();

                            if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(cat)
                                    && !TextUtils.isEmpty(precio)){
                                //Defenimos la consulta a realizar para actualizar el artículo
                                Query updateItem = databaseReference.orderByChild("nproducto")
                                        .equalTo(nomGProducto.getText().toString());
                                updateItem.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnap: dataSnapshot.getChildren()){
                                            //Recogemos las Variables a Actualizar
                                            String id = dataSnap.getKey();
                                            databaseReference.child(id).child("nproducto").setValue(nombre);
                                            databaseReference.child(id).child("desc").setValue(desc);
                                            databaseReference.child(id).child("categoria").setValue(cat);
                                            databaseReference.child(id).child("precio").setValue(precio);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.i("ERROR","Error Update Item");
                                    }
                                });

                                Toast.makeText(context,"Datos Modificados",Toast.LENGTH_LONG).show();
                                dialog.dismiss(); //Cerramos el AlertDialog
                            }else {
                                Toast.makeText(context,"No se han Modificado los Datos",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    //Listener para el botón Salir de AlertDialog
                    btnUpdateSalir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss(); //Cierra el alertDialog
                        }
                    });
                }
            });
            //-----------------------------------------------------------------------------------------------------------------//

            //ImageButton para eliminar artículos
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //------------------------Definimos el método para eliminar elementos desde el imageButton---------------//
                    //final String imagen = imagenGProducto.getDrawable().toString();
                    final String nombre = nomGProducto.getText().toString();
                    final String desc = descGProducto.getText().toString();
                    final String cat = catGProducto.getText().toString();
                    final String precio = precioGProducto.getText().toString();

                    if(!TextUtils.isEmpty(nombre)&&!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(cat)
                            &&!TextUtils.isEmpty(precio)){
                        //Definimos la consulta a realizar para eliminar los items por nombre del producto
                        Query deleteQuery = databaseReference.orderByChild("nproducto").equalTo(nombre);
                        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    //Recogemos las variables a eliminar
                                    String id = snap.getKey();
                                    databaseReference.child(id).child("userid").removeValue();
                                    databaseReference.child(id).child("nproducto").removeValue();
                                    databaseReference.child(id).child("desc").removeValue();
                                    databaseReference.child(id).child("categoria").removeValue();
                                    databaseReference.child(id).child("precio").removeValue();
                                    databaseReference.child(id).child("fecha").removeValue();
                                    databaseReference.child(id).child("imagen").removeValue();

                                    productoList.remove(getAdapterPosition()); //Recogemos el cardview por posición
                                    notifyItemRemoved(getAdapterPosition()); //Eliminamos el cardview
                                }
                                Toast.makeText(context,"Artículo Eliminado",Toast.LENGTH_LONG).show();
                                Log.i("ELIMINADO",nombre);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.i("CANCELADO","Eliminación Cancelada!!!");
                            }
                        });
                    }

                }
                //----------------------------------------------------------------------------------------------------//
            });


        }
    }

}
