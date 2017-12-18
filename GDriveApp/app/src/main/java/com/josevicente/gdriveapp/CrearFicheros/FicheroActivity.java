package com.josevicente.gdriveapp.CrearFicheros;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import com.josevicente.gdriveapp.R;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class FicheroActivity extends AppCompatActivity {
    //Variables
    private Button btnFile;
    private EditText fileName;

    DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichero);

        //Insanciamos las Variables
        btnFile = findViewById(R.id.btnFile);
        fileName = findViewById(R.id.fileName);

        //Listener para el Botón btnFile
        btnFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nombre = fileName.getText().toString();
                if(!nombre.isEmpty()){
                    createFile(v);
                    Toast.makeText(FicheroActivity.this,"Fichero Añadido en Google Drive",Toast.LENGTH_LONG).show();
                    fileName.setHint("Escribe un Nombre");
                }else{
                    Toast.makeText(FicheroActivity.this,"Introduce un Nombre",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    //Definimos el Método para crear un nuevo fichero
    public void createFile(View view) {
        //Definimos las variables Task
        final String name = fileName.getText().toString();
        mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
        mDriveResourceClient = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));

        final Task<DriveFolder> rootFolderTask = mDriveResourceClient.getRootFolder();
        final Task<DriveContents> createContentsTask = mDriveResourceClient.createContents();

        Tasks.whenAll(rootFolderTask,createContentsTask).continueWithTask(new Continuation<Void, Task<DriveFile>>() {
            @Override
            public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                DriveFolder parent = rootFolderTask.getResult();
                DriveContents contents = createContentsTask.getResult();
                OutputStream outputStream = contents.getOutputStream();
                try (Writer writer = new OutputStreamWriter(outputStream)) {
                    writer.write("Ficheros");
                }

                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle(name)
                        .setMimeType("text/plain")
                        .setStarred(true)
                        .build();

                return mDriveResourceClient.createFile(parent, changeSet, contents);
            }
        })
                .addOnSuccessListener(this, new OnSuccessListener<DriveFile>() {
                    @Override
                    public void onSuccess(DriveFile driveFile) {
                        Toast.makeText(FicheroActivity.this,"Fichero Creado con Éxito!!",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FicheroActivity.this,"Imposible crear Fichero!!",Toast.LENGTH_LONG).show();
                    }
                });

    }

}
