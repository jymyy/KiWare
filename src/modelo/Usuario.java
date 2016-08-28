package modelo;

import java.util.Arrays;

public class Usuario {

	private String nombre;
	private String password;
		
	public Usuario(String nombre, String password) {
		super();
		this.nombre = nombre;
		this.password = password;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean login(String usuariotext, char[] contrasenatext){
		char[] charPassword = this.password.toCharArray();
		if(this.nombre.equals(usuariotext) && Arrays.equals(contrasenatext, charPassword)){
			return true;
		} else{
			return false;
		}
	}
}
