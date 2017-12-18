package com.josevicente.gdriveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;

public class MainActivity extends AppCompatActivity {
    //Definimos las Variables
    private Button btnCon;
    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;

    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos las Variables
        btnCon = findViewById(R.id.btnConectar);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mGoogleSignInClient = buildGoogleSignInClient();
                    startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"Conectado a GOOGLE DRIVE",Toast.LENGTH_LONG).show();
            }
        });



    }

    //Método para conectarse a Google Drive
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

}
