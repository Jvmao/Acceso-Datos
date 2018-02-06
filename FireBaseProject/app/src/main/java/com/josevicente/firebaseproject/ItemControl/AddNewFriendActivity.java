package com.josevicente.firebaseproject.ItemControl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.josevicente.firebaseproject.Modelo.User;
import com.josevicente.firebaseproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewFriendActivity extends AppCompatActivity {
    //Variables
    private Button btnAddAmigo;
    //Variables BBDD
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,db2;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    public static final String user = "Usuarios"; //Nombre de la Tabla en la BBDD
    public static final String friend = "Amigos";

    private ListView listViewF;
    private ArrayList<String> listFUser;
    private ArrayAdapter<String> arrayAdapterFUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fr_user);
        //Instanciamos las Variables
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(user); //Nombre de la nueva tabla
        db2 = database.getReference().child(friend);
        mAuth = FirebaseAuth.getInstance();
        btnAddAmigo = findViewById(R.id.btnAddAmigo);
        listViewF = findViewById(R.id.listViewF);
        listFUser = new ArrayList<>();

        listUsers();

        btnAddAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    //Método para Listar Usuarios
    public void listUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listFUser.clear(); //Limpiamos la lista
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    //Añadimos los datos a lista que queremos mostrar desde la clase User
                    listFUser.add("Username: "+user.getUserName()+"\n"
                            +"Correo: "+user.getCorreo()+"\n"
                            +"Nombre: "+user.getNombre());
                }
                //Adaptamos el ListView para que muestre los datos
                arrayAdapterFUser = new ArrayAdapter<>(AddNewFriendActivity.this,android.R.layout.simple_list_item_1,listFUser);
                listViewF.setAdapter(arrayAdapterFUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Método para enlazar usuarios
    public void addFriend(){
        mUser = mAuth.getCurrentUser();
        String id = mUser.getUid();
        DatabaseReference databaseReference1 = db2.push();
        //Amigo amigo = new Amigo();
        Map<String, String> dataToSave = new HashMap<>();

        dataToSave.put("Amigo",id);
        databaseReference1.setValue(dataToSave);

        Toast.makeText(AddNewFriendActivity.this,"Amigos Añadidos",Toast.LENGTH_LONG).show();

    }
}
