package Adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.josevicente.ejerciciosqlite.R;

import java.util.List;

import Modelo.Alumno;
import Modelo.Profesor;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder>{
    //Creamos el Context
    private Context context;
    //Añadimos la lista de la clase Alumno en Modelo
    private List<Alumno> alumnoList;
    private List<Profesor> profesorList;

    public AdaptadorListado(Context context, List<Alumno> alumnoList, List<Profesor> profesorList) {
        this.context = context;
        this.alumnoList = alumnoList;
        this.profesorList = profesorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_elementos,parent,false);
        return new AdaptadorListado.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alumno alumno = alumnoList.get(position);
        holder.nameAlumno.setText(alumno.getNombre());
        holder.edadAlumno.setText(alumno.getEdad());
        holder.cicloAlumno.setText(alumno.getCiclo());
        holder.cursoAlumno.setText(alumno.getCurso());
        holder.notaAlumno.setText(alumno.getNota_media());

        Profesor profesor = profesorList.get(position);
        holder.nameProfesor.setText(profesor.getNombre());
        holder.edadProfesor.setText(profesor.getEdad());
        holder.cicloProfesor.setText(profesor.getCiclo());
        holder.cursoProfesor.setText(profesor.getCurso_tutor());
        holder.despachoProfesor.setText(profesor.getDespacho());
    }

    @Override
    public int getItemCount() {
        return alumnoList.size();
    }


    //Creamos el Método ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Definimos las variables a incluir y las declaramos públicas para que sean accesibles
        public TextView nameAlumno,edadAlumno,cicloAlumno,cursoAlumno,notaAlumno;
        public TextView nameProfesor,edadProfesor,cicloProfesor,cursoProfesor,despachoProfesor;

        @Override
        public void onClick(View v) {

        }

        public ViewHolder(View v, Context ctx) {
            super(v);

            //Le pasamos el Context
            context=ctx;

            //Inicializamos las Variables de Alumno mediante el ID definido en listaElementos.xml
            nameAlumno=v.findViewById(R.id.listNombreAlumno);
            edadAlumno=v.findViewById(R.id.listEdadAlumno);
            cicloAlumno=v.findViewById(R.id.listCicloAlumno);
            cursoAlumno=v.findViewById(R.id.listCursoAlumno);
            notaAlumno=v.findViewById(R.id.listNota);

            //Inicializamos las Variables de Profesor mediante el ID definido en listaElementos.xml
            nameProfesor=v.findViewById(R.id.listNombreProfesor);
            edadProfesor=v.findViewById(R.id.listEdadProfesor);
            cicloProfesor=v.findViewById(R.id.listCicloProfesor);
            cursoProfesor=v.findViewById(R.id.listCursoProfesor);
            despachoProfesor=v.findViewById(R.id.listDespacho);
        }

    }
}
