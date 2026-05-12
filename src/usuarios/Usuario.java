/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usuarios;

/**
 *
 * @author andre
 */
    
public class Usuario {
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean permisos;
    
    
    public Usuario(String nombre, String correo, String contraseña){
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        
        permisos = VerificarPermisos();
    }
    public boolean VerificarPermisos(){
    if(getContraseña().equals("soyadmin1234")){
        System.out.println("Eres admin");
        return true;
    }else{
        System.out.println("No eres admin");
        return false;
    }
    }
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean isPermisos() {
        return permisos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setPermisos(boolean permisos) {
        this.permisos = permisos;
    }
}



