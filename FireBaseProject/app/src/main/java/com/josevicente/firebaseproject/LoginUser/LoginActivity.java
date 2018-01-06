package com.josevicente.firebaseproject.LoginUser;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.UserControl.UserActivity;
import com.josevicente.firebaseproject.R;

public class LoginActivity extends AppCompatActivity {
    //Declaramos las Variables
    private EditText editMail,editPass;
    private Button btnLoginUser;

    //Variables para Autenticar al Usuario
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instanciamos las Variables
        editMail = findViewById(R.id.mailLogin);
        editPass = findViewById(R.id.loginPass);
        btnLoginUser = findViewById(R.id.btnUserLogin);

        mAuth = FirebaseAuth.getInstance();

        //Declaramos el Listener para el botón btnLoginUser
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editMail.getText().toString()) && !TextUtils.isEmpty(editPass.getText().toString())){
                    loginUser(editMail.getText().toString(),editPass.getText().toString()); //Pasamos el método loginUser()
                }else{
                    Toast.makeText(LoginActivity.this,"Datos Incorrectos",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    //Definimos el Método para Loguear al Usuario ya registrado anteriormente
    public void loginUser(String em,String pwd){
        //El usuario accede a la cuenta con el email y el password
        mAuth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Bienvenido a QuickTrade",Toast.LENGTH_LONG).show();
                    Intent intentIn = new Intent(LoginActivity.this,UserInActivity.class); //Pasamos a UserInActivity
                    startActivity(intentIn);
                }else{
                    Toast.makeText(LoginActivity.this,"Datos Incorrectos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Añadimos el ActionBar creado en main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu); //Inflamos el layout main_menu.xml
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el Método onOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_start:
                //Pasamos a MainActivity desde ActionBar
                Intent intentMain = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intentMain);
                finish();
                break;

            case R.id.action_user:
                //Pasamos a UserActivity desde ActionBar
                Intent intentUser = new Intent(LoginActivity.this,UserActivity.class);
                startActivity(intentUser);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
