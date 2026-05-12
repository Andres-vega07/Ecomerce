package modelo;
public class CarritoItem {
    private Producto producto;
    private int cantidad;
    public CarritoItem(Producto p, int c) { 
        this.producto = p; this.cantidad = c; 
    }
    
    public Producto getProducto() { 
        return producto; 
    }
    
    public int getCantidad() { 
        return cantidad; 
    }
    
    public double getSubtotal() { 
        return producto.getPrecio() * cantidad; 
    }
    
    @Override 
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " - Subtotal: $" + getSubtotal();
    }
}