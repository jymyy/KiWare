package modelo;

import java.util.Date;

public class ProductoLacteo {


	private int id;
	private String nombre;
	private String caducidad;
	private String tipo;
	private String formato;
	
	public ProductoLacteo(String nombre, String caducidad, String tipo,
			String formato) {
		this.nombre = nombre;
		this.caducidad = caducidad;
		this.tipo = tipo;
		this.formato = formato;
	}
	
	public ProductoLacteo(int id, String nombre, String caducidad, String tipo,
			String formato) {
		this.id = id;
		this.nombre = nombre;
		this.caducidad = caducidad;
		this.tipo = tipo;
		this.formato = formato;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}



	
	
}
