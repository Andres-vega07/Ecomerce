package modelo;
import java.util.*;

public abstract class Usuario {
    private int id;
    private String nombre, email, password;
    public Usuario(int id, String nombre, String email, String password) {
        this.id = id; this.nombre = nombre; this.email = email; this.password = password;
    }
    public int getId() { 
        return id; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public String getEmail() {
        return email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public abstract String getRol();
    public abstract String obtenerPermisos();
}