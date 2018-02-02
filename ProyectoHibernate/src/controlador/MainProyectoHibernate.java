package controlador;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import modelo.Empresa;
import modelo.Item;
import modelo.Pedido;

public class MainProyectoHibernate {

	public static void main(String[] args) {
		System.out.println("<---------------Proyecto Hibernate--------------->");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(df.format(date)); 
		
		//Definimos la session para conectarnos
		Session session = HibernateUtilities.getSessionFactory().openSession();
		
		session.beginTransaction(); //Iniciamos la BBDD
		
		//Definimos un nuevo objeto Empresa
		Empresa e = new Empresa();
		
		e.setCif("C4563738");
		e.setNombre("Mercadona");
		e.setEmpleados(30000);
		e.setDireccion("C/Mayor 1,Valencia");
		session.save(e); //Guardamos los datos
		
		//Definimos un nuevo objeto Pedido
		Pedido p = new Pedido();
		//Insertamos los datos PEDIDO
		p.setFecha(date);
		
		//Insertamos los elementos Item dentro de pedido
		p.getItem().add(new Item("Leche Desnatada",500));
		p.getItem().add(new Item("Aceite de Oliva",2000));
		p.getItem().add(new Item("Galletas Desayuno",135));
		p.getItem().add(new Item("Agua Mineral",5000));
		session.save(p);
		
		
		session.getTransaction().commit(); //Realizamos la operación
		
		session.beginTransaction(); //Iniciamos la BBDD
		//Recuperamos elementos de Empresa
		Empresa e2 = session.get(Empresa.class,1); //Recuperamos el objeto con su ID
		System.out.println("---------Empresa-------"+"\n"
							+e2.getCif()+"\n"
							+e2.getNombre()+"\n"
							+String.valueOf(e.getEmpleados())+"\n"
							+e2.getDireccion()+"\n"
							+"----------------------");
		
		//Recuperamos elementos de Pedido
		Pedido p2 = session.get(Pedido.class, 1); //Recuperamos el Objeto con su ID
		System.out.println("--------Pedido--------"+"\n"
							+p2.getId()+"\n"
							+p2.getFecha()+"\n"
							+"----------------------");
		
		//Recuperamos el objeto Item dentro de Pedido
		for(Item itm: p2.getItem()) {
			System.out.println("Datos Recuperados: "+itm.getNombre()+" "+itm.getCantidad());
		}
		
		//Recuperamos elementos de Item
		/*Item it2 = session.get(Item.class, 1); //Recuperamos el Objeto con su ID
		System.out.println("------Item------"+"\n"
							+it2.getId()+"\n"
							+it2.getNombre()+"\n"
							+it2.getCantidad()+"\n"
							+"---------------");*/
		
		session.getTransaction().commit(); //Realizamos la operación
		session.close(); //Cerramos session
		HibernateUtilities.getSessionFactory().close();//Cerramos Hibernate

	}

}
