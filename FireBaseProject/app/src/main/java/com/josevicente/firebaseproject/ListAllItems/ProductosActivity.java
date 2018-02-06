package com.josevicente.firebaseproject.ListAllItems;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.josevicente.firebaseproject.Adapter.AdapterProductos;
import com.josevicente.firebaseproject.ItemControl.GestItemActivity;
import com.josevicente.firebaseproject.LoginUser.LoginActivity;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.Producto;
import com.josevicente.firebaseproject.UserControl.UserActivity;
import com.josevicente.firebaseproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity {
    //Declaramos las Variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static final String userItem = "Productos";
    public static final String userCat = "categoria";

    //Le pasamos las Variables del RecyclerView
    private RecyclerView recyclerView;
    private AdapterProductos adapterProductos;
    private List<Producto> productoList;

    //Declaramos las variables para almacenar imagenes
    private StorageReference mStorageRef;
    public ImageView imageItem;
    private Uri fileUri;

    //Variables para el spinner
    private Spinner spItems;
    private ArrayAdapter<CharSequence> adapterSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //Instanciamos las Variables
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(userItem);
        mDatabaseReference.keepSynced(true); //Mantiene la bbdd sincronizada

        mStorageRef = FirebaseStorage.getInstance().getReference().child("imagenesItem");
        imageItem = findViewById(R.id.imageItem);


        //Instanciamos el spinner y el ArrayList que contiene los elementos
        spItems = findViewById(R.id.spItem);
        adapterSp = ArrayAdapter.createFromResource(ProductosActivity.this,
                R.array.spinnerItems, android.R.layout.simple_list_item_1);
        //Especificamos el layout a usar cuando desplegamos la lista
        adapterSp.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Aplicamos el adapter a la lista
        spItems.setAdapter(adapterSp);

        //Instanciamos las variables Recycler
        productoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerProductoID); //Desde activity_productos.xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAllElements();//Le pasamos el método para que liste todos los elementos
        //filtraProducto(); //Le pasamos el método para filtar los elementos cuando se selecciona el spinner

        spItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Recogemos el nombre del elemento en el spinner por posición y se muestra en un Snackbar
                final String pos = (String) parent.getItemAtPosition(position);
                Snackbar.make(getWindow().getDecorView(),"Productos Ordenados por "+pos,Snackbar.LENGTH_LONG).show();

                Query filtroQuery = mDatabaseReference.orderByChild(userCat).equalTo(pos); //Consulta para ordenar los elementos por categoría
                filtroQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //listAllElements();
                        productoList.clear();
                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                            Producto producto = snap.getValue(Producto.class);
                            productoList.add(producto);
                        }
                        recyclerView.setAdapter(adapterProductos);
                        adapterProductos.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Método para Listar todos los elementos disponibles
    public void listAllElements(){
        Query allItemQuery = mDatabaseReference;
        allItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productoList.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Producto producto = snap.getValue(Producto.class);
                    productoList.add(producto);
                }
                //Adaptamos el adapter a nuestros datos de la bbdd
                adapterProductos = new AdapterProductos(ProductosActivity.this,productoList);
                recyclerView.setAdapter(adapterProductos);
                adapterProductos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //Método para Filtar los elementos mediante el Spinner
    /*public void filtraProducto(){
        spItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Recogemos el nombre del elemento en el spinner por posición y se muestra en un Snackbar
                final String pos = (String) parent.getItemAtPosition(position);
                Snackbar.make(getWindow().getDecorView(),"Productos Ordenados por "+pos,Snackbar.LENGTH_LONG).show();

                Query filtroQuery = mDatabaseReference.orderByChild(userCat).equalTo(pos); //Consulta para ordenar los elementos por categoría
                filtroQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //listAllElements();
                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                            Producto producto = snap.getValue(Producto.class);
                            productoList.add(producto);
                        }
                        //Adaptamos el adapter a nuestros datos de la bbdd
                        adapterProductos = new AdapterProductos(ProductosActivity.this,productoList);
                        recyclerView.setAdapter(adapterProductos);
                        adapterProductos.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }*/

    //Añadimos el menu creado en menu4.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu4,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mUser = mAuth.getCurrentUser();
        switch (item.getItemId()){
            case R.id.actionInicio:
                Intent intentInicio = new Intent(ProductosActivity.this, MainActivity.class);
                startActivity(intentInicio);
                finish();
                break;
            case R.id.actionLogin:
                Intent intentLogin = new Intent(ProductosActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;
            case R.id.actionUser:
                Intent intentUser = new Intent(ProductosActivity.this, UserActivity.class);
                startActivity(intentUser);
                finish();
                break;
            case R.id.actionOut:
                if(mUser !=null && mAuth !=null){
                    mAuth.signOut(); //El usuario deja de estar logueado si selecciona esta opción en el ActionBar
                    Snackbar.make(getWindow().getDecorView(),"Has Salido de la Aplicación",Snackbar.LENGTH_LONG).show();
                    Intent intentMain = new Intent(ProductosActivity.this, MainActivity.class);
                    startActivity(intentMain); //Nos devuelve a la pantalla inicial si el usuario se desloguea
                    finish();
                }else{
                    Snackbar.make(getWindow().getDecorView(),"No estás dentro de la Aplicación",Snackbar.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Guardamos los cambios que se producen en esta Activity
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //outState.putParcelable("imagen",imagenArticulo);
        Producto producto = new Producto();
        if(mStorageRef != null){
            outState.putString("datos",mStorageRef.toString());
            //Picasso.with(ProductosActivity.this).load(mStorageRef.toString()).into(imageItem);
            Picasso.with(ProductosActivity.this).load(fileUri).into(imageItem);
            outState.putParcelable("datos",fileUri);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final String ref = savedInstanceState.getString("datos");

        if(ref==null){
            return;
        }
        fileUri = savedInstanceState.getParcelable("datos");
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(ref);
        List<FileDownloadTask> tasks = mStorageRef.getActiveDownloadTasks();
        if(tasks.size()>0){
            FileDownloadTask task = tasks.get(0);
            task.addOnSuccessListener(this, new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.i("DESCARGA", "descarga correcta!");
                    Picasso.with(ProductosActivity.this).load(mStorageRef.toString()).into(imageItem);
                }
            });
        }
    }

}
