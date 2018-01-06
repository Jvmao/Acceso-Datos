package com.josevicente.firebaseproject.LoginUser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.josevicente.firebaseproject.ItemControl.AddItemActivity;
import com.josevicente.firebaseproject.ItemControl.GestItemActivity;
import com.josevicente.firebaseproject.ItemControl.ListUserItemActivity;
import com.josevicente.firebaseproject.ListAllItems.ProductosActivity;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.R;

public class UserInActivity extends AppCompatActivity {
    //Declaramos las Variables
    private Button btnAddIn,btnListIn,btnGestion;
    //Variables para Autenticar al Usuario
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_in);
        //Instanciamos las Variables
        btnAddIn = findViewById(R.id.btnAddIn);
        btnListIn = findViewById(R.id.btnListIn);
        btnGestion = findViewById(R.id.btnGestion);
        mAuth = FirebaseAuth.getInstance();

        //Añadimos el Listener del Botón btnAddIn para pasar a la Activity AddItemActivity
        btnAddIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(UserInActivity.this, AddItemActivity.class);
                startActivity(intentAdd);
            }
        });

        //Añadimos el Listener del Botón btnListIn para pasar a la Activity ListUserItemActivity
        btnListIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListUser = new Intent(UserInActivity.this, ListUserItemActivity.class);
                startActivity(intentListUser);

                //Intent intentListUser = new Intent(UserInActivity.this, ProductosActivity.class);
                //startActivity(intentListUser);
            }
        });

        //Añadimos el Listener del Botón btnGestión para pasar a la Activity GestItemActivity
        btnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGestItem = new Intent(UserInActivity.this, GestItemActivity.class);
                startActivity(intentGestItem);
            }
        });

    }

    //Añadimos el ActionBar creado en main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu); //Inflamos el layout main_menu.xml
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el Método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mUser = mAuth.getCurrentUser();
        switch (item.getItemId()){
            case R.id.action_signout:
                if(mUser !=null && mAuth !=null){
                    mAuth.signOut(); //El usuario deja de estar logueado si selecciona esta opción en el ActionBar
                    Toast.makeText(UserInActivity.this,"Has salido de la Aplicación",Toast.LENGTH_LONG).show();
                    Intent intentMain = new Intent(UserInActivity.this, MainActivity.class);
                    startActivity(intentMain); //Nos devuelve a la pantalla inicial si el usuario se desloguea
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
