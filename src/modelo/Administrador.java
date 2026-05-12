package modelo;
public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String email, String password) {
        super(id, nombre, email, password);
    }
    @Override 
    public String getRol() { 
        return "ADMIN"; 
    }
    
    @Override 
    public String obtenerPermisos() { 
        return "GESTIÓN TOTAL"; 
    }
}