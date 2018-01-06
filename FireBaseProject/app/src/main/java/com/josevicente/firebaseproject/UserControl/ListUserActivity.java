package com.josevicente.firebaseproject.UserControl;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.josevicente.firebaseproject.LoginUser.LoginActivity;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.User;
import com.josevicente.firebaseproject.R;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    //Definimos las Variables
    private ListView listView;
    private DatabaseReference databaseReference;
    private ArrayList<String> listUser;
    private ArrayAdapter<String> arrayAdapterUser;
    public static final String userIndex = "Usuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        //Instanciamos las Variables
        listView = findViewById(R.id.listViewUser);
        listUser = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference(userIndex);

        //Añadimos el EventListener a Firebase para que muestre el listado de usuarios
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUser.clear(); //Limpiamos la lista
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    //Añadimos los datos a lista que queremos mostrar desde la clase User
                    listUser.add("Username: "+user.getUserName()+"\n"
                            +"Correo: "+user.getCorreo()+"\n"
                            +"Nombre: "+user.getNombre()+"\n"
                            +"Apellidos: "+user.getApellidos()+"\n"
                            +"Dirección: "+user.getDirec());
                }
                //Adaptamos el ListView para que muestre los datos
                arrayAdapterUser = new ArrayAdapter<>(ListUserActivity.this,android.R.layout.simple_list_item_1,listUser);
                listView.setAdapter(arrayAdapterUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Método por definir
            }
        });

    }

    //Añadimos el menu creado en menu2.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el Método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //mUser = mAuth.getCurrentUser();
        switch (item.getItemId()){
            case R.id.action_sigin:
                //Nos devuelve a LoginActivity
                Intent intentLogin = new Intent(ListUserActivity.this,LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;

            case R.id.action_start:
                //Nos devuelve a la página de inicio
                Intent intentStart = new Intent(ListUserActivity.this,MainActivity.class);
                startActivity(intentStart);
                finish();
                break;

            case R.id.actionAgregar:
                //Pasamos a Agregar Usuario
                Intent intentAdd = new Intent(ListUserActivity.this,UserActivity.class);
                startActivity(intentAdd);
                finish();
                break;

            case R.id.actionListar:
                Snackbar.make(getWindow().getDecorView(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.actionMod:
                //Pasamos a la actividad listar usuarios
                Intent intentMod = new Intent(ListUserActivity.this,UpdateActivity.class);
                startActivity(intentMod);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
