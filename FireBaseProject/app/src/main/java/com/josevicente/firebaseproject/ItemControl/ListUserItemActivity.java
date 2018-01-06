package com.josevicente.firebaseproject.ItemControl;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.Producto;
import com.josevicente.firebaseproject.R;

import java.util.ArrayList;

public class ListUserItemActivity extends AppCompatActivity {
    //Definimos las Variables
    private ListView listViewItem;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<String> listUserItem;
    private ArrayAdapter<String> arrayAdapterUserItem;
    public static final String userItem = "Productos";
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_item);

        //Instanciamos las Variables
        listViewItem = findViewById(R.id.listUserItem);
        listUserItem = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(userItem);
        mAuth = FirebaseAuth.getInstance();

        listUserItem(); //Añadimos el método listUserItem()

    }

    //Definimos el Método para listar los productos de cada usuario
    public void listUserItem(){
        mUser = mAuth.getCurrentUser();
        Query itemUserQuery = databaseReference.orderByChild(userItem);
        itemUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUserItem.clear(); //Limpiamos la lista
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Producto producto = snapshot.getValue(Producto.class);

                    //Añadimos los datos a lista que queremos mostrar desde la clase User
                    listUserItem.add("Nombre: "+producto.getNproducto()+"\n"
                            +"Descripción: "+producto.getDesc()+"\n"
                            +"Categoría: "+producto.getCategoria()+"\n"
                            +"Precio: "+producto.getPrecio()+"\n"
                            +"Fecha: "+producto.getFecha());
                }
                //Adaptamos el ListView para que muestre los datos
                arrayAdapterUserItem = new ArrayAdapter<>(ListUserItemActivity.this,android.R.layout.simple_list_item_1,listUserItem);
                listViewItem.setAdapter(arrayAdapterUserItem);
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
                    Toast.makeText(ListUserItemActivity.this,"Has salido de la Aplicación",Toast.LENGTH_LONG).show();
                    Intent intentMain = new Intent(ListUserItemActivity.this, MainActivity.class);
                    startActivity(intentMain); //Nos devuelve a la pantalla inicial si el usuario se desloguea
                    finish();
                }
                break;
            case R.id.addItem:
                Intent intentAddItem = new Intent(ListUserItemActivity.this, AddItemActivity.class);
                startActivity(intentAddItem); //Nos devuelve a la pantalla LisUserItem
                finish();
                break;
            case R.id.verElementos:
                //Toast.makeText(ListUserItemActivity.this,"Estás en la Opción Elegida",Toast.LENGTH_LONG).show();
                Snackbar.make(getWindow().getCurrentFocus(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;
            case R.id.gestElementos:
                //Snackbar.make(getWindow().getDecorView(),"Under Construction",Snackbar.LENGTH_LONG).show();
                Intent intentGest = new Intent(ListUserItemActivity.this,GestItemActivity.class);
                startActivity(intentGest); //Nos devuelve a la pantalla GestItemActivity
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
