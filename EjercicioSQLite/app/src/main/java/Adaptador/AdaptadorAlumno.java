package Adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.josevicente.ejerciciosqlite.ElAlumnoActivity;
import com.josevicente.ejerciciosqlite.R;
import java.util.List;

import Datos.DBHandler;
import Modelo.Alumno;


public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.ViewHolder> {
    //Creamos el Context
    private Context context;
    //Añadimos la lista de la clase Alumno en Modelo
    private List<Alumno> alumnoList;

    //Añadimos las Variables para el Alert Dialog
    private AlertDialog.Builder alertDialogBilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    //Creamos el Constructor de las Variables
    public AdaptadorAlumno(Context context, List<Alumno> alumnoList) {
        this.context = context;
        this.alumnoList = alumnoList;
    }

    @Override
    public AdaptadorAlumno.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elementos_alumno,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(AdaptadorAlumno.ViewHolder holder, int position) {
        Alumno alumno = alumnoList.get(position);
        holder.nameAlumno.setText(alumno.getNombre());
        holder.edadAlumno.setText(alumno.getEdad());
        holder.cicloAlumno.setText(alumno.getCiclo());
        holder.cursoAlumno.setText(alumno.getCurso());
        holder.notaAlumno.setText(alumno.getNota_media());
    }

    @Override
    public int getItemCount() {
        return alumnoList.size();
    }

    //Creamos el Método ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Definimos las variables a incluir y las declaramos públicas para que sean accesibles
        public TextView nameAlumno;
        public TextView edadAlumno;
        public TextView cicloAlumno;
        public TextView cursoAlumno;
        public TextView notaAlumno;
        //public Button editButton;
        public Button deleteButton;

        public ViewHolder(View v,Context ctx) {
            super(v);

            //Le pasamos el Context
            context=ctx;

            //Inicializamos las Variables mediante el ID definido en layout_elementosAlumno.xml
            nameAlumno=v.findViewById(R.id.elNombreAlumno);
            edadAlumno=v.findViewById(R.id.elEdadAlumno);
            cicloAlumno=v.findViewById(R.id.elCicloAlumno);
            cursoAlumno=v.findViewById(R.id.elCursoAlumno);
            notaAlumno=v.findViewById(R.id.elMediaAlumno);
            deleteButton=v.findViewById(R.id.deleteButton);

            //Agregamos los botones al Listener
            //editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            //Iniciamos el Listener
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pasamos a la siguiente ventana y obtenemos la posición de los items
                    int position = getAdapterPosition();
                    Alumno alumno = alumnoList.get(position);
                    Intent intent = new Intent(context, ElAlumnoActivity.class); //Pasamos a la Activity ElAlumnoActivity
                    intent.putExtra("id ",alumno.getId()); //Indicamos la variables a conseguir
                    intent.putExtra("Nombre ",alumno.getNombre());
                    intent.putExtra("Edad ",alumno.getEdad());
                    intent.putExtra("Ciclo ",alumno.getCurso());
                    intent.putExtra("Curso ",alumno.getCurso());
                    intent.putExtra("Nota ",alumno.getNota_media());

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.deleteButton:
                    //Añadimos el método para eliminar elementos
                    int position = getAdapterPosition();
                    Alumno alumno = alumnoList.get(position);
                    deleteAlumno(alumno.getId()); //Añadimos el método para eliminar elementos
                    break;
            }
        }

        //Creamos el método para eliminar elementos Alumno y también creamos un nuevo layout llamado confirmation_dialog
        public void deleteAlumno(final int id){
            //Insertamos el AlertDialog
            alertDialogBilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog,null);

            //Introducimos los Botones
            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            alertDialogBilder.setView(view);
            dialog = alertDialogBilder.create();
            dialog.show();

            //Listener para noButton
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss(); //Cierra el Dialogo
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Eliminamos el item
                    DBHandler db = new DBHandler(context);
                    db.eliminarAlumno(id);
                    alumnoList.remove(getAdapterPosition()); //Removemos el item
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss(); //Cuando finaliza las operaciones cierra el Dialog
                }
            });

        }
    }

}
