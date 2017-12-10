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

import com.josevicente.ejerciciosqlite.ElProfesorActivity;
import com.josevicente.ejerciciosqlite.R;

import java.util.List;

import Datos.DBHandler;
import Modelo.Profesor;


public class AdaptadorProfesor extends RecyclerView.Adapter <AdaptadorProfesor.ViewHolder>{
    //Creamos el Context
    private Context context;
    //Añadimos la lista de la clase Profesor en Modelo
    private List<Profesor> profesorList;

    //Añadimos las Variables para el Alert Dialog
    private AlertDialog.Builder alertDialogBilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    //Añadimos el Constructor de la Variables
    public AdaptadorProfesor(Context context, List<Profesor> profesorList) {
        this.context = context;
        this.profesorList = profesorList;
    }

    @Override
    public AdaptadorProfesor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflamos el layout_elementos_profesor
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elementos_profesor,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(AdaptadorProfesor.ViewHolder holder, int position) {
        Profesor profesor = profesorList.get(position);
        holder.nameProfesor.setText(profesor.getNombre());
        holder.edadProfesor.setText(profesor.getEdad());
        holder.cicloProfesor.setText(profesor.getCiclo());
        holder.cursoProfesor.setText(profesor.getCurso_tutor());
        holder.despachoProfesor.setText(profesor.getDespacho());
    }

    @Override
    public int getItemCount() {
        return profesorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Definimos las variables a incluir y las declaramos públicas para que sean accesibles
        public TextView nameProfesor;
        public TextView edadProfesor;
        public TextView cicloProfesor;
        public TextView cursoProfesor;
        public TextView despachoProfesor;
        public Button dltButton;

        public ViewHolder(View v,Context ctx) {
            super(v);

            //Le pasamos el Context
            context=ctx;

            //Inicializamos las Variables mediante el ID definido en layout_elementos.xml
            nameProfesor=v.findViewById(R.id.elNombreProfesor);
            edadProfesor=v.findViewById(R.id.elEdadProfesor);
            cicloProfesor=v.findViewById(R.id.elCicloProfesor);
            cursoProfesor=v.findViewById(R.id.elCursoProfesor);
            despachoProfesor=v.findViewById(R.id.elDespachoProfesor);
            dltButton=v.findViewById(R.id.eliminarProfesor);

            //Agregamos los botones al Listener
            dltButton.setOnClickListener(this);

            //Iniciamos el Listener
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pasamos a la siguiente ventana y obtenemos la posición de los items
                    int position = getAdapterPosition();
                    Profesor profesor = profesorList.get(position);
                    Intent intent = new Intent(context, ElProfesorActivity.class); //Pasamos a la Activity ElAlumnoActivity
                    intent.putExtra("id ",profesor.getId());
                    intent.putExtra("Nombre ",profesor.getNombre());
                    intent.putExtra("Edad ",profesor.getEdad());
                    intent.putExtra("Ciclo ",profesor.getCiclo());
                    intent.putExtra("Curso ",profesor.getCurso_tutor());
                    intent.putExtra("Despacho ",profesor.getDespacho());

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.eliminarProfesor:
                    //Añadimos el método para eliminar elementos
                    int position = getAdapterPosition();
                    Profesor profesor = profesorList.get(position);
                    deleteProfesor(profesor.getId()); //Añadimos el método para eliminar elementos
                    break;
            }
        }

        //Creamos el método para eliminar elementos Profesor y también creamos un nuevo layout llamado confirmation_dialog
        public void deleteProfesor(final int id){
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
                    dialog.dismiss(); //Cierra el Diálogo
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Eliminamos el item
                    DBHandler db = new DBHandler(context);
                    db.eliminarProfesor(id);
                    profesorList.remove(getAdapterPosition()); //Removemos el item
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss(); //Cuando finaliza las operaciones cierra el Dialog
                }
            });

        }
    }
}
