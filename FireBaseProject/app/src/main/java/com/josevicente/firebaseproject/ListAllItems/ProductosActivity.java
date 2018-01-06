package com.josevicente.firebaseproject.ListAllItems;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.josevicente.firebaseproject.Adapter.AdapterProductos;
import com.josevicente.firebaseproject.LoginUser.LoginActivity;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.Producto;
import com.josevicente.firebaseproject.UserControl.UserActivity;
import com.josevicente.firebaseproject.R;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity {
    //Declaramos las Variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static final String userItem = "Productos";

    //Le pasamos las Variables del RecyclerView
    private RecyclerView recyclerView;
    private AdapterProductos adapterProductos;
    private List<Producto> productoList;

    //Declaramos las variables para almacenar imagenes
    private StorageReference mStorageRef;
    private Uri imagenArticulo = null;
    private ImageView imagenP;
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

        imagenP = findViewById(R.id.imageItem);

        //Instanciamos las variables Recycler
        productoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerProductoID); //Desde activity_productos.xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
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

        if (mStorageRef != null) {
            outState.putString("imagen", mStorageRef.toString());
        }

        if(imagenArticulo !=null){
            outState.putParcelable("uri",imagenArticulo);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final String stringRef = savedInstanceState.getString("imagen");
        if (stringRef == null) {
            return;
        }else{
            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);
            imagenP = findViewById(R.id.imagenProducto);
            imagenArticulo = savedInstanceState.getParcelable("uri");
            imagenP.setImageURI(imagenArticulo);
        }

    }
}
