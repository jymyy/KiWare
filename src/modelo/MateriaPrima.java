package modelo;

import java.util.Date;

public class MateriaPrima {

	private int id;
	private String nombre;
	private String caducidad;
	private String entrada;
	private boolean refrigerar;
	
	
	public MateriaPrima(String nombre, String caducidad, String entrada,
			boolean refrigerar) {
		this.nombre = nombre;
		this.caducidad = caducidad;
		this.entrada = entrada;
		this.refrigerar = refrigerar;
	}
	
	public MateriaPrima(int id,String nombre, String caducidad, String entrada,
			boolean refrigerar) {
		this.id = id;
		this.nombre = nombre;
		this.caducidad = caducidad;
		this.entrada = entrada;
		this.refrigerar = refrigerar;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	public boolean isRefrigerar() {
		return refrigerar;
	}
	public void setRefrigerar(boolean refrigerar) {
		this.refrigerar = refrigerar;
	}
	
	
	
	
}
