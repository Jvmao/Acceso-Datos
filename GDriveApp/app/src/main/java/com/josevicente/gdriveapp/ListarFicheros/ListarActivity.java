package com.josevicente.gdriveapp.ListarFicheros;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.josevicente.gdriveapp.Modelo.Filas;
import com.josevicente.gdriveapp.R;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {
    private DriveResourceClient mDriveResourceClient;
    DriveClient mDriveClient;
    private Button btnElementos;
    private ListView listViewEl;
    private ArrayList<String> values;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        //Instanciamos las Variables
        btnElementos = findViewById(R.id.btnListFiles);
        listViewEl = findViewById(R.id.listViewElementos);
        values = new ArrayList<>();
        mDriveClient = Drive.getDriveClient(ListarActivity.this, GoogleSignIn.getLastSignedInAccount(this));
        mDriveResourceClient = Drive.getDriveResourceClient(ListarActivity.this, GoogleSignIn.getLastSignedInAccount(this));

        //Listener para el Botón de Listar Elementos
        btnElementos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFiles(); //Añadimos el método open files creado
            }
        });
    }

    //Declaramos el método para listar elementos en Google Drive
    public void openFiles(){
        /*Query query =
                new Query.Builder()
                        .addFilter(Filters.and(Filters.eq(SearchableField.MIME_TYPE, "text/plain"),
                                Filters.eq(SearchableField.STARRED, true)))
                        .build();*/

        //Definimos el query para listar elementos por Título
        SortOrder sortOrder = new SortOrder.Builder().addSortAscending(SortableField.TITLE).build();
        Query query = new Query.Builder().setSortOrder(sortOrder).build();

        final Task<MetadataBuffer> queryTask = mDriveResourceClient.query(query);
        //Declaramos el listener de queryTask para saber si se aplica con éxito o falla
        queryTask
                .addOnSuccessListener(this,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                                Log.d("EXITO","Archivo Encontrado");
                                Toast.makeText(ListarActivity.this,"Listando Archivos!!! "+queryTask.getResult().getCount(),Toast.LENGTH_LONG).show();

                                for(int i=0;i<queryTask.getResult().getCount();i++) {
                                    values.add(queryTask.getResult().get(i).getTitle());
                                }
                                mAdapter= new ArrayAdapter<>(ListarActivity.this,android.R.layout.simple_list_item_1,values);
                                listViewEl.setAdapter(mAdapter);
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR", "Error al Listar los Archivos", e);
                        Toast.makeText(ListarActivity.this,"Archivo No Encontrado",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

}
