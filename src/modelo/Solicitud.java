package modelo;
 
import java.util.Date;
 
public class Solicitud {
 
    private int id;
    private String departamento;
    private Date fecha;
    private String descripcion;
     
    public Solicitud(int id, String departamento, Date fecha, String descripcion) {
        super();
        this.id = id;
        this.departamento = departamento;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
     
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
     
}