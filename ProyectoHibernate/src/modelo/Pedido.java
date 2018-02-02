package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
	//Variables
	private int id;
	private Date fecha;
	
	//Añadimos el objeto Item para relacionarlo con el objeto Pedido
	private List<Item> item = new ArrayList<Item>();
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public List<Item> getItem() {
		return item;
	}
	public void setItem(List<Item> item) {
		this.item = item;
	}

}
