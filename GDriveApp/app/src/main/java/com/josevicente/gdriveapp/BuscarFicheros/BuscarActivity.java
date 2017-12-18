package com.josevicente.gdriveapp.BuscarFicheros;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.josevicente.gdriveapp.R;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {
    //Declaramos las Variables
    private DriveResourceClient mDriveResourceClient;

    private EditText buscarText;
    private Button btnSearch;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        //Instanciamos las Variables
        buscarText = findViewById(R.id.buscarText);
        btnSearch = findViewById(R.id.btnSearch);
        listView = findViewById(R.id.listFilesID);
        values = new ArrayList<>();
        mDriveResourceClient = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));


        //Listener del Botón Buscar Ficheros
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = buscarText.getText().toString();
                if(!nombre.isEmpty()){
                    searchFiles(); //Añadimos el Método searchFiles() creado
                    Toast.makeText(BuscarActivity.this,"Archivo Encontrado en Google Drive",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(BuscarActivity.this,"Escribe un Archivo a Buscar",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Definimos el método para buscar los elementos
    public void searchFiles(){
        final String name = buscarText.getText().toString();

        //Definimos el query para la búsqueda de elementos por Título
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.TITLE, name))
                .build();

        //Relacionamos el query creado con GoogleDrive
        final Task<MetadataBuffer> queryTask = mDriveResourceClient.query(query);
        //Declaramos el listener de queryTask para saber si se aplica con éxito o falla
        queryTask
                .addOnSuccessListener(this,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                               Log.d("EXITO","Archivo Encontrado");
                               Toast.makeText(BuscarActivity.this,"Archivo Encontrado",Toast.LENGTH_LONG).show();

                                for(int i=0;i<queryTask.getResult().getCount();i++) {
                                    values.add(queryTask.getResult().get(i).getTitle());
                                }
                               arrayAdapter = new ArrayAdapter<>(BuscarActivity.this,android.R.layout.simple_list_item_1,values);
                               listView.setAdapter(arrayAdapter);
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR", "Error Buscando Archivos", e);
                        Toast.makeText(BuscarActivity.this,"Archivo No Encontrado",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
