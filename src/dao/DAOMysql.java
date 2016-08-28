package dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import modelo.MateriaPrima;
import modelo.ProductoLacteo;

public class DAOMysql {
	
	private static Statement stt;
	private static Connection con;

	public static void conexion() {
		
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            
            stt = con.createStatement();
            
            stt.execute("CREATE DATABASE IF NOT EXISTS almacen");
            stt.execute("USE almacen");
            // Creamos la tabla lacteos/
            stt.execute("DROP TABLE IF EXISTS lacteos");
            stt.execute("CREATE TABLE lacteos (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "nombre VARCHAR(25),"
                    + "caducidad VARCHAR(25),"
                    + "tipo VARCHAR(25),"
                    + "formato VARCHAR(25),"
                    + "cantidad INTEGER,"
                    + "PRIMARY KEY(id)"
                    + ")");
            // a�adimos items a la tabla lacteos
			stt.execute("INSERT INTO lacteos (nombre, caducidad, tipo, formato,cantidad) VALUES" + 
			        "('desnatada', '16-09-2016', '1', 'individual','22'), ('semidesnatada', '16-09-2016', '2', 'individual','16'), " +
			        "('entera', '16-09-2016', '3', 'individual','48')");
			
			// Creamos la tabla materiasprimas
            stt.execute("DROP TABLE IF EXISTS materiasprimas");
            stt.execute("CREATE TABLE materiasprimas (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "nombre VARCHAR(25),"
                    + "caducidad VARCHAR(25),"
                    + "entrada VARCHAR(25),"
                    + "refrigerar VARCHAR(25),"
                    + "PRIMARY KEY(id)"
                    + ")");
            // a�adimos items a la tabla lacteos
			stt.execute("INSERT INTO materiasprimas (nombre, caducidad, entrada, refrigerar) VALUES" + 
			        "('azucar', '16-9-2016', '16-9-2016', 'no'), ('brick', '16-9-2016', '16-9-2016', 'no'), " +
			        "('leche', '16-9-2016', '16-9-2016', 'si')");
			
        }catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public LinkedList<ProductoLacteo> getLacteos(){
        ResultSet res;
        LinkedList<ProductoLacteo> productosLacteos = new LinkedList<ProductoLacteo>();
		try {
			res = stt.executeQuery("SELECT * FROM lacteos");
	        while (res.next()){
	            ProductoLacteo lacteo = new ProductoLacteo(Integer.parseInt(res.getString("id")), 
	            		res.getString("nombre"), res.getString("caducidad"), 
	            		res.getString("tipo"), res.getString("formato"));
	            productosLacteos.add(lacteo);
	        }
	        res.close();
		} catch (SQLException e) {
			System.out.println("Problemas con la base de datos::gestLacteos()");
		}
		return productosLacteos;
	}
	
	public LinkedList<MateriaPrima> getMatPrimas(){
        ResultSet res;
        LinkedList<MateriaPrima> materiasprimas = new LinkedList<MateriaPrima>();
		try {
			Boolean refrig = false;
			res = stt.executeQuery("SELECT * FROM materiasprimas");
	        while (res.next()){
	            if (res.getString("refrigerar") == "si"){
					refrig = true;
				}
	            MateriaPrima materia = new MateriaPrima(Integer.parseInt(res.getString("id")), 
	            		res.getString("nombre"), res.getString("caducidad"), res.getString("entrada"), refrig);
	            materiasprimas.add(materia);
	        }
	        res.close();
		} catch (SQLException e) {
			System.out.println("Problemas con la base de datos::gestMateriasPrimas()");
		}
		return materiasprimas;
	}
	
	/*
	 * Permite añadir una materia prima nueva
	 */
	public void addlacteo(ProductoLacteo producto, int cantidad){
		
		String query = " insert into lacteos (nombre, caducidad, tipo, formato, cantidad)"
		        + " values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			System.out.println("nombre --> "+producto.getNombre());
			preparedStmt.setString (1, producto.getNombre());
			preparedStmt.setString (2, producto.getCaducidad().toString());
			preparedStmt.setString (3, producto.getTipo());
			preparedStmt.setString (4, producto.getFormato());
			preparedStmt.setString (5, String.valueOf(cantidad));
			preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Problema con la query::addLacteo()");
		}
	}
	
	/*
	 * Permite añadir una materia prima a la base de datos
	 */
	public void addprima(MateriaPrima producto, int cantidad){
		String refrig = "no";
		if (producto.isRefrigerar()==true){
			refrig = "si";
		}
		String query = " insert into materiasprimas (nombre, caducidad, entrada, refrigerar, cantidad)"
		        + " values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString (1, producto.getNombre());
			preparedStmt.setString (2, producto.getCaducidad().toString());
			preparedStmt.setString (3, producto.getEntrada().toString());
			preparedStmt.setString (4, refrig);
			preparedStmt.setString (5, String.valueOf(cantidad));
			preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Problema con la query::addPrima()");
		}
	}
	
	public void addCantidadMateriaPrima(MateriaPrima materia, int cantidad){
		
	}
	
	/*Busca la cantidad de elementos por nombre.
	 * Retorna -1 en el caso en el que no haya elementos con ese nombre
	 */
	public int getCantidadProductoLacteo(ProductoLacteo producto){
		int total = -1;
		ResultSet res;
		try {
			res = stt.executeQuery("SELECT * FROM lacteos");
	        while (res.next()){
	        	if (res.getString("nombre").equals(producto.getNombre())){
	        		total += Integer.parseInt(res.getString("cantidad"));
	        	}
	        }
		} catch (SQLException e) {
			System.out.println("::getCantidadProductoLacteo");
		}

        return total;
	}
	
	/*Busca la cantidad de elementos por nombre.
	 * Retorna -1 en el caso en el que no haya elementos con ese nombre
	 */
	public int getCantidadPrimas(ProductoLacteo producto){
		int total = -1;
		ResultSet res;
		try {
			res = stt.executeQuery("SELECT * FROM materiasprimas");
	        while (res.next()){
	        	if (res.getString("nombre").equals(producto.getNombre())){
	        		total += Integer.parseInt(res.getString("cantidad"));
	        	}
	        }
		} catch (SQLException e) {
			System.out.println("::getCantidadMaterias");
		}

        return total;
	}
	
	public int getNumLacteos(){
		LinkedList<ProductoLacteo> productos = getLacteos();
		return productos.size();
	}
	
	public int getNumPrimas(){
		LinkedList<MateriaPrima> productos = getMatPrimas();
		return productos.size();
	}
	
	public void desconexion(){
        try {
			stt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		conexion();
		Date d1 = new Date(12);
		ProductoLacteo pr = new ProductoLacteo("pepe", d1, "3", "pack");
		addlacteo(pr,6);
		addlacteo(pr,88);
		LinkedList<ProductoLacteo> lista = getLacteos();

		
		for (ProductoLacteo producto : lista) {
			 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");    
			 String fechaConFormato = sdf.format(producto.getCaducidad()); 
			System.out.println(fechaConFormato);
			System.out.println(producto.getNombre());
			System.out.println(getCantidadProductoLacteo(producto));
		}
	}*/
}
