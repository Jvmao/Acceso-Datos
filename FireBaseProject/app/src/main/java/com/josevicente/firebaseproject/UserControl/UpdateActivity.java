package com.josevicente.firebaseproject.UserControl;

import android.content.Intent;
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

public class UpdateActivity extends AppCompatActivity {
    //Definimos las Variables
    private DatabaseReference databaseReference;
    private Button btnSearch,btnUpdate,btnDelete;
    private EditText searchUser,updateUserName,updateEmail,updateNombre,updateApellidos,updateDir;
    public static final String userIndex = "Usuarios";
    public static final String usName = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Instanciamos las Variables
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        searchUser = findViewById(R.id.editSearch);
        updateUserName = findViewById(R.id.updateUserName);
        updateEmail = findViewById(R.id.updateMail);
        updateNombre = findViewById(R.id.updatePNombre);
        updateApellidos = findViewById(R.id.updateApellidos);
        updateDir = findViewById(R.id.updateDir);
        databaseReference = FirebaseDatabase.getInstance().getReference(userIndex);

        //Añadimos el Listener para el Botón Search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser(); //Importamos el método searchUser()
            }
        });

        //Añadimos el Listener para el Botón update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(); //Importamos el método updateUser()
            }
        });

        //Añadimos el Listener para el Botón Eliminar
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(); //importamos el método delete()
            }
        });
    }

    //Definimos el Método para realizar las consultas
    public void searchUser(){
        //Recogemos las Variables a añadir a la consulta para la búsqueda por nombre de usuario
        final String search = searchUser.getText().toString();

        //Definimos la consulta a realizar para buscar usuarios
        Query searchQuery = FirebaseDatabase.getInstance().getReference(userIndex).
                orderByChild("userName").equalTo(search);

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    for(DataSnapshot snap: dataSnapshot.getChildren()){
                        User user = snap.getValue(User.class);
                        updateUserName.setText(user.getUserName().toString());
                        updateEmail.setText(user.getCorreo().toString());
                        updateNombre.setText(user.getNombre().toString());
                        updateApellidos.setText(user.getApellidos().toString());
                        updateDir.setText(user.getDirec().toString());
                    }
                }else{
                    Toast.makeText(UpdateActivity.this,"Usuario No Encontrado!!!",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    //Definimos el Método para actualizar usuarios
    public void updateUser(){
        final String userName = updateUserName.getText().toString();
        final String email = updateEmail.getText().toString();
        final String nombre = updateNombre.getText().toString();
        final String ape = updateApellidos.getText().toString();
        final String dir = updateDir.getText().toString();

        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(nombre) &&
                !TextUtils.isEmpty(ape) && !TextUtils.isEmpty(dir)){

            //Definimos la consulta que queremos realizar para actualizar usuarios
            Query updateQuery = databaseReference.orderByChild(usName).equalTo(userName);
            updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnap: dataSnapshot.getChildren()){
                        String id = dataSnap.getKey();
                        //Definimos la ruta que se debe de buscar en la bbdd para realizar las modificaciones
                        databaseReference.child(id).child("userName").setValue(userName);
                        databaseReference.child(id).child("correo").setValue(email);
                        databaseReference.child(id).child("nombre").setValue(nombre);
                        databaseReference.child(id).child("apellidos").setValue(ape);
                        databaseReference.child(id).child("direc").setValue(dir);
                        }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            Toast.makeText(UpdateActivity.this,"Datos Modificados",Toast.LENGTH_LONG).show();
            limpiar();
        }else {
            Toast.makeText(UpdateActivity.this,"Datos No Modificados",Toast.LENGTH_LONG).show();
        }

    }

    //Definimos el Método para Eliminar usuarios a partir del Nombre de Usuario
    public void delete(){
        final String userName = updateUserName.getText().toString();
        final String email = updateEmail.getText().toString();
        final String nombre = updateNombre.getText().toString();
        final String ape = updateApellidos.getText().toString();
        final String dir = updateDir.getText().toString();

        if(!TextUtils.isEmpty(userName)&& !TextUtils.isEmpty(email) && !TextUtils.isEmpty(nombre) &&
                !TextUtils.isEmpty(ape) && !TextUtils.isEmpty(dir)) {
            //Definimos la consulta a realizar para eliminar usuarios
            Query deleteQuery = FirebaseDatabase.getInstance().getReference(userIndex).
                    orderByChild("userName").equalTo(userName);

            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String id = snap.getKey();
                        databaseReference.child(id).child("id").removeValue();
                        databaseReference.child(id).child("userName").removeValue();
                        databaseReference.child(id).child("correo").removeValue();
                        databaseReference.child(id).child("pass").removeValue();
                        databaseReference.child(id).child("nombre").removeValue();
                        databaseReference.child(id).child("apellidos").removeValue();
                        databaseReference.child(id).child("direc").removeValue();
                    }
                    Toast.makeText(UpdateActivity.this, "Usuario Eliminado!!!", Toast.LENGTH_LONG).show();
                    limpiar();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }else{
            Toast.makeText(UpdateActivity.this, "No se ha podido Eliminar el Usuario", Toast.LENGTH_LONG).show();
        }

    }

    //Definimos el Método para Limpiar los Campos
    public void limpiar(){
        searchUser.setText("");
        updateUserName.setText("");
        updateEmail.setText("");
        updateNombre.setText("");
        updateApellidos.setText("");
        updateDir.setText("");
    }

    //Añadimos el menu creado en menu1.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el Método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sigin:
                //Nos devuelve a LoginActivity
                Intent intentLogin = new Intent(UpdateActivity.this,LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;


            case R.id.action_start:
                //Nos devuelve a la página de inicio
                Intent intentStart = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intentStart);
                finish();
                break;

            case R.id.actionAgregar:
                //Pasamos a UserActivity
                Intent intentAdd = new Intent(UpdateActivity.this,UserActivity.class);
                startActivity(intentAdd);
                finish();
                break;

            case R.id.actionListar:
                //Pasamos a la actividad listar usuarios
                Intent intentListar = new Intent(UpdateActivity.this,ListUserActivity.class);
                startActivity(intentListar);
                finish();
                break;

            case R.id.actionMod:
                Snackbar.make(getWindow().getDecorView(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
