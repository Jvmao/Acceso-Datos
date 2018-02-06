package com.josevicente.firebaseproject.ItemControl;

import android.content.Intent;
import android.net.Uri;
import android.os.FileUriExposedException;
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
import android.widget.Toast;

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
import com.josevicente.firebaseproject.Adapter.AdapterGProductos;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.Producto;
import com.josevicente.firebaseproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GestItemActivity extends AppCompatActivity {
    //Declaramos las Variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static final String userItem = "Productos";
    public static final String userCat = "categoria";

    //Le pasamos las Variables del RecyclerView
    private RecyclerView recyclerView;
    private AdapterGProductos adapterGProductos;
    private ArrayList<Producto> productoList;

    //Variables para el spinner
    private Spinner spinnerItems;
    private ArrayAdapter<CharSequence> adapter;

    //Declaramos las variables Storage
    private StorageReference mStorageRef;
    public ImageView imagenGProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gest_item);
        //Instanciamos las Variables
        spinnerItems = findViewById(R.id.spinnerCategoria);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(userItem);
        mAuth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        imagenGProducto = findViewById(R.id.imagenProducto);

        //Instanciamos el spinner
        adapter = ArrayAdapter.createFromResource(GestItemActivity.this,
                R.array.spinnerItems, android.R.layout.simple_list_item_1);
        //Especificamos el layout a usar cuando desplegamos la lista
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Aplicamos el adapter a la lista
        spinnerItems.setAdapter(adapter);

        //Instanciamos las variables Recycler
        productoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerGestID); //Desde activit_gest_item.xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listUserItem(); //Pasamos el método para listar los productos

        //Declaramos el Listener para el spinner cada vez que se selecciona un elemento
        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Recogemos el nombre del elemento en el spinner por posición y se muestra en un Snackbar
                final String pos = (String) parent.getItemAtPosition(position);
                Snackbar.make(getWindow().getDecorView(),"Productos Ordenados por "+pos,Snackbar.LENGTH_LONG).show();

                //Definimos el query para filtrar los resultados a partir de la categoria y la posición del elemento en el spinner
                mUser = mAuth.getCurrentUser();
                final String userId = mUser.getUid();

                Query filtroQuery = mDatabaseReference.orderByChild(userCat).equalTo(pos);
                filtroQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listUserItem();
                        //productoList.clear();
                        //Adaptamos el recyclerview a la lista
                        /*for(DataSnapshot snap: dataSnapshot.getChildren()){
                            Producto producto = snap.getValue(Producto.class);
                            productoList.add(producto);
                        }
                        recyclerView.setAdapter(adapterGProductos);
                        adapterGProductos.notifyDataSetChanged();*/
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(GestItemActivity.this,"Error al acce                                                                     q   dsdsder a la Base de Datos",Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    //Método para listar los elementos del Usuario
    public void listUserItem(){
        mUser = mAuth.getCurrentUser();
        String id = mUser.getUid();
        Query itemUserQuery = mDatabaseReference.orderByChild("userid").equalTo(id);
        itemUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productoList.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Producto producto = snap.getValue(Producto.class);
                    productoList.add(producto);
                }
                //Adaptamos el adapter a nuestros datos de la bbdd
                adapterGProductos = new AdapterGProductos(GestItemActivity.this,productoList);
                recyclerView.setAdapter(adapterGProductos);
                adapterGProductos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //Añadimos el menu creado en menu3.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Generamos el método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mUser = mAuth.getCurrentUser();
        switch (item.getItemId()){
            case R.id.salirAdd:
                if(mUser !=null && mAuth !=null){
                    mAuth.signOut(); //El usuario deja de estar logueado si selecciona esta opción en el ActionBar
                    Toast.makeText(GestItemActivity.this,"Has salido de la Aplicación",Toast.LENGTH_LONG).show();
                    Intent intentMain = new Intent(GestItemActivity.this, MainActivity.class);
                    startActivity(intentMain); //Nos devuelve a la pantalla inicial si el usuario se desloguea
                    finish();
                }
                break;
            case R.id.addItem:
                Intent intentAddItem = new Intent(GestItemActivity.this, AddItemActivity.class);
                startActivity(intentAddItem); //Nos devuelve a la pantalla AddItemActivity
                finish();
                break;
            case R.id.verElementos:
                Intent intentListUser = new Intent(GestItemActivity.this, ListUserItemActivity.class);
                startActivity(intentListUser); //Nos devuelve a la pantalla LisUserItem
                finish();
                break;
            case R.id.gestElementos:
                Snackbar.make(getWindow().getCurrentFocus(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;
        }return super.onOptionsItemSelected(item);
    }

    //Guardamos los cambios que se producen en esta Activity
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //outState.putParcelable("imagen",imagenArticulo);
        if(mStorageRef != null){
            outState.putString("datos",mStorageRef.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final String ref = savedInstanceState.getString("datos");

        if(ref==null){
            return;
        }

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(ref);
        Picasso.with(GestItemActivity.this).load(mStorageRef.toString()).into(imagenGProducto);
        List<FileDownloadTask> tasks = mStorageRef.getActiveDownloadTasks();
        if(tasks.size()>0){
            FileDownloadTask task = tasks.get(0);
            task.addOnSuccessListener(this, new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.i("DESCARGA", "descarga correcta!");
                    Picasso.with(GestItemActivity.this).load(mStorageRef.toString()).into(imagenGProducto);
                }
            });
        }
    }
}
