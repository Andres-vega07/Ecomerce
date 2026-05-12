package modelo;
import java.util.*;
public class Pedido {
    private int id;
    private List<CarritoItem> items;
    private double total;
    private String estado;
    public Pedido(int id, List<CarritoItem> items, double total) {
        this.id = id; this.items = new ArrayList<>(items); this.total = total; this.estado = "PENDIENTE";
    }
    public int getId() { return id; }
    public List<CarritoItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }
    @Override public String toString() {
        return "Pedido #" + id + " | Total: $" + total + " | Estado: " + estado;
    }
}