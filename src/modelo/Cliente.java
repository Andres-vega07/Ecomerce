package modelo;
public class Cliente extends Usuario {
    private String direccion, telefono;
    public Cliente(int id, String nombre, String email, String password, String dir, String tel) {
        super(id, nombre, email, password);
    }
    @Override 
    public String getRol() { 
        return "CLIENTE"; 
    }
    @Override 
    public String obtenerPermisos() { 
        return "COMPRA Y SEGUIMIENTO"; 
    }
}
