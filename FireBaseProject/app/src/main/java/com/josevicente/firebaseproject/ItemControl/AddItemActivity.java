package com.josevicente.firebaseproject.ItemControl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.josevicente.firebaseproject.Main.MainActivity;
import com.josevicente.firebaseproject.Modelo.User;
import com.josevicente.firebaseproject.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {
    //Declaramos las Variables
    private EditText addName,addDesc,addPrecio;
    private Spinner spinnerItems;
    private Button btnAddItem;
    private ArrayAdapter<CharSequence> adapter;

    //Variables BBDD
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    public static final String userItem = "Productos"; //Nombre de la Tabla en la BBDD

    //Variables para cargar imagenes y un progressDialog
    private ProgressDialog mDialog;
    private static final int GALLERY_CODE=1;
    private Uri imageUri;
    private StorageReference mStorage;
    private ImageButton imageAddItem;

    //Variables para realizar fotos desde el dispositivo
    private ImageButton imagePic;
    private static final int CAM_REQUEST=1313;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Instanciamos las Variables
        addName = findViewById(R.id.addName);
        addDesc = findViewById(R.id.addDesc);
        addPrecio = findViewById(R.id.addPrecio);
        btnAddItem = findViewById(R.id.btnAddItem);
        spinnerItems = findViewById(R.id.spinnerItems);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(userItem);
        mAuth = FirebaseAuth.getInstance();

        //Instanciamos las variable para la carga de imágenes y el progressDialog
        mDialog = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        imageAddItem = findViewById(R.id.imageAddItem);
        imagePic = findViewById(R.id.imageTakePic);


        //Creamos un ArrayAdapter usando el string array y un default spinner layout
        adapter = ArrayAdapter.createFromResource(AddItemActivity.this,
                R.array.spinnerItems, android.R.layout.simple_list_item_1);
        //Especificamos el layout a usar cuando desplegamos la lista
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Aplicamos el adapter a la lista
        spinnerItems.setAdapter(adapter);

        //Declaramos el Listener para el botón btnAddItem, que nos permite añadir productos
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        //Declaramos el Listener para seleccionar imágenes desde el terminal con el imageButton definido
        imageAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*"); //Recoge cualquier tipo de imagen//
                startActivityForResult(galleryIntent,GALLERY_CODE);
            }
        });

        //Declaramos el Listener para realizar una foto con el dispositivo
        imagePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentFoto,CAM_REQUEST);
            }
        });
    }

    //Definimos el Método para Añadir el producto nuevo
    public void addItem() {
        //Definimos las Variables a añadir en la BBDD
        final String producto = addName.getText().toString();
        final String descrip = addDesc.getText().toString();
        final String precio = addPrecio.getText().toString();
        final String cate = spinnerItems.getSelectedItem().toString();
        User user = new User();
        final String usName = user.getUserName();

        if ((!producto.isEmpty()) && (!descrip.isEmpty()) && (!precio.isEmpty()) && (!cate.isEmpty()) && imageUri !=null) {
            if (!TextUtils.isEmpty(producto) && !TextUtils.isEmpty(descrip) && !TextUtils.isEmpty(precio)
                    && !TextUtils.isEmpty(cate) && imageUri !=null) {

                mDialog.setMessage("Creando Nuevo Artículo");
                mDialog.show();
                try {
                    //-------------------Opción sin Imagen-------------------------------------------//
                    /*DatabaseReference dbReference = databaseReference.push();
                    mUser = mAuth.getCurrentUser();
                    Map<String, String> dataToSave = new HashMap<>();

                    //Obtenemos el formato de la fecha
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
                    Date date = new Date();
                    String strDate = dateFormat.format(date).toString();

                    //Le pasamos los mismo valores creados en el objeto Blog
                    dataToSave.put("nproducto", producto);
                    dataToSave.put("desc", descrip);
                    dataToSave.put("categoria", cate);
                    dataToSave.put("precio", precio);
                    dataToSave.put("fecha", strDate);
                    dataToSave.put("userid", mUser.getUid()); //Recuperamos el ID del Usuario
                    dataToSave.put("userName",usName); //Recuperamos el nombre de usuario

                    dbReference.setValue(dataToSave);//Pasamos todos los datos a la BBDD*/
                    //--------------------------------------------------------------------------------------//

                    //------------------------Opción con carga de Imagen--------------------------------------------//
                    StorageReference imagePath = mStorage.child("imagenesItem").child(imageUri.getLastPathSegment()); //Carpeta donde se guardan las imagenes
                    imagePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri imagen = taskSnapshot.getDownloadUrl();  //Definimos la variable imagen a cargar

                            DatabaseReference dbReference = databaseReference.push(); //Pasamos los datos a la referencia de la BBDD
                            mUser = mAuth.getCurrentUser(); //Autorizamos al usuario mediante el login
                            Map<String, String> dataToSave = new HashMap<>(); //Obtenemos los datos mediante Map<>

                            //Obtenemos el formato de la fecha
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
                            Date date = new Date();
                            String strDate = dateFormat.format(date).toString();

                            //Le pasamos los mismo valores creados en el objeto Producto
                            dataToSave.put("nproducto", producto);
                            dataToSave.put("desc", descrip);
                            dataToSave.put("categoria", cate);
                            dataToSave.put("precio", precio);
                            dataToSave.put("imagen",imagen.toString()); //Añadimos la imagen en la BBDD
                            dataToSave.put("fecha", strDate);
                            dataToSave.put("userid", mUser.getUid()); //Recuperamos el ID del Usuario
                            dataToSave.put("userName",usName); //Recuperamos el nombre de usuario

                            dbReference.setValue(dataToSave);//Pasamos todos los datos a la BBDD
                            mDialog.dismiss();
                        }
                    });
                    //-----------------------------------------------------------------------------------------------//

                } catch (Exception e) {
                    Log.i("ERROR", e.getMessage());
                }
            } else {
                Toast.makeText(AddItemActivity.this, "Error FireBase", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(AddItemActivity.this, "Producto Añadido!!!", Toast.LENGTH_LONG).show();
            limpiar();

           //-----------------------Opción con foto desde cámara-----------------------------------------------------------//
        } else if((!producto.isEmpty()) && (!descrip.isEmpty()) && (!precio.isEmpty()) && (!cate.isEmpty()) && bitmap !=null){
            if (!TextUtils.isEmpty(producto) && !TextUtils.isEmpty(descrip) && !TextUtils.isEmpty(precio)
                    && !TextUtils.isEmpty(cate) && bitmap !=null) {
                mDialog.setMessage("Creando Nuevo Artículo");
                mDialog.show();

                imagePic.setDrawingCacheEnabled(true);
                imagePic.buildDrawingCache();
                bitmap = imagePic.getDrawingCache();
                ByteArrayOutputStream bit = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bit);
                byte[] data = bit.toByteArray();

                UploadTask uploadTask = mStorage.child("imagenesItem/").child(String.valueOf(bitmap.getGenerationId())).putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference dbReference = databaseReference.push(); //Pasamos los datos a la referencia de la BBDD
                        mUser = mAuth.getCurrentUser(); //Autorizamos al usuario mediante el login
                        Map<String, String> dataToSave = new HashMap<>(); //Obtenemos los datos mediante Map<>

                        //Obtenemos el formato de la fecha
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
                        Date date = new Date();
                        String strDate = dateFormat.format(date).toString();

                        //Le pasamos los mismo valores creados en el objeto Producto
                        dataToSave.put("nproducto", producto);
                        dataToSave.put("desc", descrip);
                        dataToSave.put("categoria", cate);
                        dataToSave.put("precio", precio);
                        dataToSave.put("imagen",downloadUrl.toString()); //Añadimos la imagen en la BBDD
                        dataToSave.put("fecha", strDate);
                        dataToSave.put("userid", mUser.getUid()); //Recuperamos el ID del Usuario
                        dataToSave.put("userName",usName); //Recuperamos el nombre de usuario

                        dbReference.setValue(dataToSave);//Pasamos todos los datos a la BBDD
                        mDialog.dismiss();
                    }
                });

            }else {
                Toast.makeText(AddItemActivity.this, "Error FireBase", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(AddItemActivity.this, "Producto Añadido!!!", Toast.LENGTH_LONG).show();
            limpiar();
            //-----------------------------------------------------------------------------------------------------//
        } else {
            Toast.makeText(AddItemActivity.this, "Faltan Datos por Rellenar", Toast.LENGTH_LONG).show();
        }

    }

    //Añadimos el método para limpiar campos una vez se ha añadido un nuevo producto
    public void limpiar(){
        addName.setText("");
        addDesc.setText("");
        //Reinicia el Adapter
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerItems.setAdapter(adapter);
        addPrecio.setText("");
        imageAddItem.setImageResource(R.drawable.addnew64); //Restablecemos el item
        imagePic.setImageResource(R.drawable.camara1x50);

    }

    //Añadimos el menu creado en menu3.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Generamos el método onOptionItemSelect
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mUser = mAuth.getCurrentUser();
        switch (item.getItemId()){
            case R.id.salirAdd:
                if(mUser !=null && mAuth !=null){
                    mAuth.signOut(); //El usuario deja de estar logueado si selecciona esta opción en el ActionBar
                    Toast.makeText(AddItemActivity.this,"Has salido de la Aplicación",Toast.LENGTH_LONG).show();
                    Intent intentMain = new Intent(AddItemActivity.this, MainActivity.class);
                    startActivity(intentMain); //Nos devuelve a la pantalla inicial si el usuario se desloguea
                    finish();
                }
                break;

            case R.id.addItem:
                //Toast.makeText(AddItemActivity.this,"Estás en la Opción Elegida",Toast.LENGTH_LONG).show();
                Snackbar.make(getWindow().getCurrentFocus(),"Estás en la Opción Elegida",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.verElementos:
                Intent intentListUser = new Intent(AddItemActivity.this, ListUserItemActivity.class);
                startActivity(intentListUser); //Nos devuelve a la pantalla LisUserItem
                finish();
                break;

            case R.id.gestElementos:
                //Añadimos la librería para usar SnackBar desde File>>Project Structure>>app>>dependencies>>+>>com.android.support:design:27.0.2
                //Snackbar.make(getWindow().getDecorView(),"Under Construction",Snackbar.LENGTH_LONG).show();
                Intent intentGest = new Intent(AddItemActivity.this,GestItemActivity.class);
                startActivity(intentGest); //Nos devuelve a la pantalla GestItemActivity
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para cargar imágenes del dispositivo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Seleccionamos las imagenes que hay guardadas en el dispositivo
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageAddItem.setImageURI(imageUri); //Le pasamos el imageButton imageAddItem
        }

        //Nos permite la opción de hacer una foto al dispositivo y añadirla a la App
        if(requestCode == CAM_REQUEST  && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            imagePic.setImageBitmap(bitmap); //Le pasamos el imageButton imagePic
            //imageUri = data.getData();
            //imagePic.setImageURI(imageUri);
        }
    }
}
