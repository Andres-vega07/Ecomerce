package modelo;
public class Producto {
    private int id, stock;
    private String nombre, descripcion, categoria;
    private double precio;
    public Producto(int id, String nombre, String desc, double precio, int stock, String cat) {
        this.id = id; this.nombre = nombre; this.descripcion = desc;
        this.precio = precio; this.stock = stock; this.categoria = cat;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void setStock(int s) { this.stock = s; }
    @Override public String toString() {
        return String.format("%-5d %-22s $%-9.2f %-8d %-15s", id, nombre, precio, stock, categoria);
    }
}