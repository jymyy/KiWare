package controlador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dao.DAOMysql;
import modelo.*;

public class Controlador {

	Usuario usuario = new Usuario("usuario", "contrasena");
	DAOMysql almacen = new DAOMysql();
	public List<ProductoLacteo> ListPL = new LinkedList<ProductoLacteo>();
	public List<MateriaPrima> ListMP = new LinkedList<MateriaPrima>();
	public List<Solicitud> ListSolicitud = new ArrayList<Solicitud>();
	private int capacidadLacteos = 100;
	private int capacidadMatPrimas = 100;
	
	public Controlador() {
		// Al crear el controlador se crea la conexion a la base de datos
		// y a su vez las tablas y contenido inicial de las mismas.
		almacen.conexion();
		this.ListPL = almacen.getLacteos();
		this.ListMP = almacen.getMatPrimas();
	}
	
	public boolean logout() {
		//TODO: falta cerrar la aplicación al salir.
		return true;
	}
	
	public int getnumLacteos(){
		return this.almacen.getNumLacteos();
	}
	
	public int getnumMatPrimas(){
		return this.almacen.getNumPrimas();
	}
	
	public boolean login(String usuariotext, char[] contrasenatext) {
		return usuario.login(usuariotext, contrasenatext);
	}
	
	public void entregarFabricacionDeLista(int id, String cantidadtext) {
		// Cuando usuario hace "Entregar a fabricaci�n" a traves de lista de materias primas
		Date date = new Date();
		int cantidad = Integer.parseInt(cantidadtext);
		Solicitud solicitud = new Solicitud(id, "Ventas", date, "");
		ListSolicitud.add(solicitud);
		
		//TODO: TENEMOS QUE ELIMINAR DE LA BASE DE DATOS DE MATERIAS PRIMAS
		
	}
	
	public void exportarVentasDeLista(int id, String cantidadtext) {
		// Cuando usuario hace "Exportar a ventas" a traves de lista de productos lacteos
		Date date = new Date();
		int cantidad = Integer.parseInt(cantidadtext);
		Solicitud solicitud = new Solicitud(id, "Ventas", date, "");
		ListSolicitud.add(solicitud);
		
		//TODO: TENEMOS QUE ELIMINAR ELEMENTOS DE LA BASE DE DATOS DE LACTEOS
	}
	
	public void entregarFabricacion(String idtext, String nombretext, String cantidadtext, String fechatext, String descripciontext) throws ParseException {
		// Cuando usuario hace "Entregar a fabricaci�n" a traves de bot�n de "Entregar a fabricaci�n"
		// Qu� hacemos con los productos cuando entregamos a fabricaci�n??!
		// tenemos que cambiar los datos a sus formas correctas! C�mo String fecha --> Date fecha.
		// Los cambios de fechas no funciona ahora! Tenemos que buscar algo manera para cambiar String -> Date.
		Date date; 
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = formatter.parse(fechatext);
		
		int cantidad = Integer.parseInt(cantidadtext);
		int id = Integer.parseInt(idtext);
		
		//NUEVA CANTIDAD DE MATERIA PRIMA ES CANTIDADVIEJO - CANTIDADENTREGADO? Dond� mantenemos la cantidad y capacidad de los productos??!
		Solicitud solicitud = new Solicitud(id, "Ventas", date, descripciontext);
		ListSolicitud.add(solicitud);
	}
	
	public void exportarVentas(String idtext, String nombretext, String cantidadtext, String fechatext, String descripciontext) throws ParseException {
		// Cuando usuario hace "Exportar a ventas" a traves de bot�n de "Exportar a ventas"
		// Los cambios de fechas no funcionan ahora. Tenemos que buscar algo manera para cambiar String -> Date.
		Date date; 
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = formatter.parse(fechatext);
		int cantidad = Integer.parseInt(cantidadtext);
		int id = Integer.parseInt(idtext);
		
		//NUEVA CANTIDAD DE PRODUCTO LACTEO ES CANTIDADVIEJO - CANTIDADENTREGADO? Dond� mantenemos la cantidad y capacidad de los productos??!
		Solicitud solicitud = new Solicitud(id, "Ventas", date, descripciontext);
		ListSolicitud.add(solicitud);
	}
	
	public void agregarMateriaPrima(String nombretext, String caducidadtext, 
			String entradatext, boolean refrigeracion) {
		
		MateriaPrima mp = new MateriaPrima(nombretext, caducidadtext, entradatext, refrigeracion);
		this.almacen.addprima(mp,1);
		actualizarMateriasPrimas();
	}
	
	public void agregarProductoLacteo(String nombretext, String tipotext, String formatotext, 
		 String caducidadtext) {
		ProductoLacteo pl = new ProductoLacteo(nombretext, caducidadtext, tipotext, formatotext);
		System.out.println("---nombre impreso xD"+pl.getNombre());
		this.almacen.addlacteo(pl,1);
		actualizarLacteos();
	}

	public int getCapacidadLacteos() {
		return capacidadLacteos;
	}

	public int getCapacidadMatPrimas() {
		return capacidadMatPrimas;
	}
	
	private void actualizarMateriasPrimas(){
		this.ListMP = null;
		this.ListMP = this.almacen.getMatPrimas();
		for (MateriaPrima producto : this.ListMP) {
			System.out.println("--"+producto.getNombre());
		}
	}
	
	private void actualizarLacteos(){
		this.ListPL = null;
		this.ListPL = this.almacen.getLacteos();
		for (ProductoLacteo producto : this.ListPL) {
			System.out.println("--"+producto.getNombre());
		}
	}
	
}
