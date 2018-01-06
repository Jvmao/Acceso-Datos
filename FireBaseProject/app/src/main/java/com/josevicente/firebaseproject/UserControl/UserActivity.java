package com.josevicente.firebaseproject.UserControl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.josevicente.firebaseproject.LoginUser.LoginActivity;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.User;
import com.josevicente.firebaseproject.R;

public class UserActivity extends AppCompatActivity {
    //Declaramos las Variables
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private EditText editUserName,editMail,editPass,editName,editApe,editDir;
    private Button btnAddUser;
    private Button btnMostrarUsuario;
    private Button btnModificarUsuario;

    public static final String userIndex = "Usuarios"; //Nombre de la Tabla en la BBDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Instanciamos las Variables
        editUserName = findViewById(R.id.editUserName);
        editMail = findViewById(R.id.editMail);
        editPass = findViewById(R.id.editPassID);
        editName = findViewById(R.id.editName);
        editApe = findViewById(R.id.editApe);
        editDir = findViewById(R.id.editDir);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnMostrarUsuario = findViewById(R.id.btnConsultarUsuario);
        btnModificarUsuario = findViewById(R.id.btnModificarUsuario);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(userIndex);
        mAuth = FirebaseAuth.getInstance();

        //Listener botón btnAddUser para Agregar Usuario
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Le pasamos los valores de email y password para autenticar al usuario
                addUser();
            }
        });

        //Listener para el Botón btnMostrarUsuarios
        btnMostrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para pasar a ListUserActivity
                Intent intentList = new Intent(UserActivity.this, ListUserActivity.class);
                startActivity(intentList);
            }
        });

        //Listener para el Botón btnModificarUsuario
        btnModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para pasar a UpdateActivity
                Intent intentUpdate = new Intent(UserActivity.this, UpdateActivity.class);
                startActivity(intentUpdate);
            }
        });
    }

    //Definimos el método para introducir usuarios en la BBDD de FireBase
    public void addUser(){
        //Recogemos las Variables para agregarlas a la BBDD de FireBase
        final String userName = editUserName.getText().toString();
        final String mail = editMail.getText().toString();
        final String pass = editPass.getText().toString();
        final String name = editName.getText().toString();
        final String apellidos = editApe.getText().toString();
        final String dir = editDir.getText().toString();

        if(!userName.isEmpty() && !mail.isEmpty() && !pass.isEmpty() && !name.isEmpty() &&
                !apellidos.isEmpty() && !dir.isEmpty()){
            //Para esta opción debemos de deshabilitar las reglas en la consola firebase a true
                                /*String id = databaseReference.push().getKey();

                                //Declaramos un nuevo objeto User
                                User user = new User(id,userName,mail,pass,name,apellidos,dir);
                                databaseReference.child(id).setValue(user);
                                limpiar();
                                Toast.makeText(UserActivity.this,"Datos Guardados en FireBase",Toast.LENGTH_LONG).show();*/

            //Definimos el Query para evitar un nombre de usuario repetido
            Query userQuery = FirebaseDatabase.getInstance().getReference(userIndex).
                    orderByChild("userName").equalTo(userName);
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0){
                        //limpiar();
                        //Si el nombre de usuario ya está elegido nos devuelve el siguiente mensaje
                        Toast.makeText(UserActivity.this,"Elige otro Nombre de Usuario",Toast.LENGTH_LONG).show();
                    }else{
                        if(!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(mail)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(name)
                                &&!TextUtils.isEmpty(apellidos)&&!TextUtils.isEmpty(dir)){
                            //Creamos un nuevo usuario auntenticado con Email y Password
                            mAuth.createUserWithEmailAndPassword(mail,pass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                mUser = mAuth.getCurrentUser();
                                                String id = databaseReference.push().getKey();

                                                //Declaramos un nuevo objeto User
                                                User user = new User(id, userName, mail, pass, name, apellidos, dir);
                                                databaseReference.child(id).setValue(user);
                                                limpiar();
                                                Toast.makeText(UserActivity.this, "Datos Guardados en FireBase", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(UserActivity.this, "No es Posible Guardar los Datos", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            //Si falra algún campo por rellenar nos devuelve el siguiente mensaje
            Toast.makeText(UserActivity.this,"Faltan Campos por Rellenar",Toast.LENGTH_LONG).show();
        }


    }

    //Definimos el Método para Limpiar los campos
    public void limpiar(){
        editUserName.setText("");
        editMail.setText("");
        editPass.setText("");
        editName.setText("");
        editApe.setText("");
        editDir.setText("");
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
                Intent intentLogin = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;


            case R.id.action_start:
                //Nos devuelve a la página de inicio
                Intent intentStart = new Intent(UserActivity.this,MainActivity.class);
                startActivity(intentStart);
                finish();
                break;

            case R.id.actionAgregar:
                Snackbar.make(getWindow().getDecorView(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.actionListar:
                //Pasamos a la actividad listar usuarios
                Intent intentListar = new Intent(UserActivity.this,ListUserActivity.class);
                startActivity(intentListar);
                finish();
                break;

            case R.id.actionMod:
                Intent intentMod = new Intent(UserActivity.this,UpdateActivity.class);
                startActivity(intentMod);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
