package modelo;
import java.util.*;

public class Tienda {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Producto> catalogo = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private List<CarritoItem> carritoActual = new ArrayList<>();
    private Usuario usuarioLogueado = null;

    public Tienda() {
        usuarios.add(new Administrador(1, "Admin", "admin@tienda.com", "1234"));
        usuarios.add(new Cliente(1, "usuario", "usuario@usuario.com", "1234"));
        catalogo.add(new Producto(101, "Laptop Pro", "Potente", 1500.0, 10, "Electrónica"));
        catalogo.add(new Producto(102, "Mouse Optico", "Ergonómico", 25.0, 50, "Accesorios"));
    }

    public Usuario login(String email, String pass) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getPassword().equals(pass)) {
                usuarioLogueado = u;
                return u;
            }
        }
        return null;
    }

    public void logout() { usuarioLogueado = null; carritoActual.clear(); }

    public boolean registrarCliente(String n, String e, String p, String d, String t) {
        for (Usuario u : usuarios) if (u.getEmail().equals(e)) return false;
        usuarios.add(new Cliente(usuarios.size() + 1, n, e, p, d, t));
        return true;
    }

    public List<Producto> getCatalogo() { return catalogo; }

    public String agregarAlCarrito(int id, int cant) {
        for (Producto p : catalogo) {
            if (p.getId() == id) {
                if (p.getStock() >= cant) {
                    carritoActual.add(new CarritoItem(p, cant));
                    return "Agregado.";
                } else return "Stock insuficiente.";
            }
        }
        return "No encontrado.";
    }

    public List<CarritoItem> getCarrito() { return carritoActual; }
    public double getTotalCarrito() { return carritoActual.stream().mapToDouble(CarritoItem::getSubtotal).sum(); }

    public boolean quitarDelCarrito(int id) {
        return carritoActual.removeIf(i -> i.getProducto().getId() == id);
    }

    public Pedido realizarPedido() {
        if (carritoActual.isEmpty()) return null;
        Pedido p = new Pedido(pedidos.size() + 1, carritoActual, getTotalCarrito());
        pedidos.add(p);
        carritoActual.clear();
        return p;
    }

    public List<Pedido> getPedidosDelCliente() { return pedidos; }
    public List<Pedido> getTodosPedidos() { return pedidos; }
    public List<Usuario> getTodosUsuarios() { return usuarios; }

    public boolean agregarProducto(String n, String d, double p, int s, String c) {
        catalogo.add(new Producto(catalogo.size() + 101, n, d, p, s, c));
        return true;
    }
    public boolean eliminarProducto(int id) { return catalogo.removeIf(p -> p.getId() == id); }
    public boolean actualizarStock(int id, int s) {
        for (Producto p : catalogo) { if (p.getId() == id) { p.setStock(s); return true; } }
        return false;
    }
}
