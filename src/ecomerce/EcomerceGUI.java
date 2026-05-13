package ecomerce;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EcomerceGUI extends JFrame {
    private Tienda tienda = new Tienda();
    private Usuario usuarioActual;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    
    // Colores
    private static final Color COLOR_PRINCIPAL = new Color(25, 103, 210);
    private static final Color COLOR_SECUNDARIO = new Color(56, 142, 60);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_ERROR = new Color(211, 47, 47);

    public EcomerceGUI() {
        setTitle("Tienda Online - Java Desktop");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initLoginPanel();
        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private void initLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Panel contenedor interno
        JPanel loginContainer = new JPanel(new GridBagLayout());
        loginContainer.setBackground(Color.WHITE);
        loginContainer.setBorder(BorderFactory.createLineBorder(COLOR_PRINCIPAL, 2));
        
        GridBagConstraints gbcContainer = new GridBagConstraints();
        gbcContainer.insets = new Insets(20, 20, 20, 20);

        // Título
        JLabel titulo = new JLabel("INICIAR SESIÓN");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(COLOR_PRINCIPAL);
        gbcContainer.gridx = 0;
        gbcContainer.gridy = 0;
        gbcContainer.gridwidth = 2;
        gbcContainer.anchor = GridBagConstraints.CENTER;
        loginContainer.add(titulo, gbcContainer);

        // Email
        gbcContainer.gridwidth = 1;
        gbcContainer.gridx = 0;
        gbcContainer.gridy = 1;
        gbcContainer.anchor = GridBagConstraints.EAST;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginContainer.add(lblEmail, gbcContainer);

        JTextField txtEmail = new JTextField(25);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbcContainer.gridx = 1;
        gbcContainer.anchor = GridBagConstraints.WEST;
        loginContainer.add(txtEmail, gbcContainer);

        // Contraseña
        gbcContainer.gridx = 0;
        gbcContainer.gridy = 2;
        gbcContainer.anchor = GridBagConstraints.EAST;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginContainer.add(lblPass, gbcContainer);

        JPasswordField txtPass = new JPasswordField(25);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbcContainer.gridx = 1;
        gbcContainer.anchor = GridBagConstraints.WEST;
        loginContainer.add(txtPass, gbcContainer);

        // Botón Login
        gbcContainer.gridx = 0;
        gbcContainer.gridy = 3;
        gbcContainer.gridwidth = 2;
        gbcContainer.anchor = GridBagConstraints.CENTER;
        gbcContainer.insets = new Insets(30, 20, 20, 20);
        
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(COLOR_PRINCIPAL);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(200, 45));
        
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(21, 85, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(COLOR_PRINCIPAL);
            }
        });
        
        loginContainer.add(btnLogin, gbcContainer);

        // Texto de ayuda
        gbcContainer.gridx = 0;
        gbcContainer.gridy = 4;
        gbcContainer.gridwidth = 2;
        gbcContainer.insets = new Insets(10, 20, 20, 20);
        JLabel lblHelp = new JLabel("Demo: admin@tienda.com / 1234");
        lblHelp.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblHelp.setForeground(new Color(120, 120, 120));
        loginContainer.add(lblHelp, gbcContainer);

        // Agregar contenedor al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginContainer, gbc);

        // Listener del botón
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String pass = new String(txtPass.getPassword());
            
            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            usuarioActual = tienda.login(email, pass);
            if (usuarioActual != null) {
                if (usuarioActual instanceof Administrador) {
                    initAdminDashboard();
                } else {
                    initClienteDashboard();
                }
                txtEmail.setText("");
                txtPass.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", 
                    "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(panel, "Login");
    }

    private void initClienteDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_PRINCIPAL);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel welcome = new JLabel("Bienvenido, " + usuarioActual.getNombre());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcome.setForeground(Color.WHITE);
        topPanel.add(welcome, BorderLayout.WEST);
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLogout.setBackground(COLOR_ERROR);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            tienda.logout();
            usuarioActual = null;
            cardLayout.show(mainPanel, "Login");
        });
        topPanel.add(btnLogout, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);

        // Tabla de productos
        String[] cols = {"ID", "Nombre", "Precio", "Stock"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(200, 220, 250));
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        for (Producto p : tienda.getCatalogo()) {
            model.addRow(new Object[]{p.getId(), p.getNombre(), "$" + String.format("%.2f", p.getPrecio()), p.getStock()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setBackground(COLOR_FONDO);
        
        JButton btnAdd = new JButton("Agregar al Carrito");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(COLOR_SECUNDARIO);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new Dimension(180, 40));
        
        JButton btnCarrito = new JButton("Ver Carrito");
        btnCarrito.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCarrito.setBackground(COLOR_PRINCIPAL);
        btnCarrito.setForeground(Color.WHITE);
        btnCarrito.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCarrito.setFocusPainted(false);
        btnCarrito.setPreferredSize(new Dimension(180, 40));

        btnAdd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                tienda.agregarAlCarrito(id, 1);
                JOptionPane.showMessageDialog(this, "Producto agregado al carrito correctamente", 
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un producto", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnCarrito.addActionListener(e -> mostrarCarrito());

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnCarrito);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "Cliente");
        cardLayout.show(mainPanel, "Cliente");
    }

    private void mostrarCarrito() {
        List<CarritoItem> carrito = tienda.getCarrito();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Producto", "Cantidad", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(25);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        for (CarritoItem item : carrito) {
            model.addRow(new Object[]{
                item.getProducto().getNombre(),
                item.getCantidad(),
                "$" + String.format("%.2f", item.getSubtotal())
            });
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        double total = tienda.getTotalCarrito();
        JLabel lblTotal = new JLabel("Total: $" + String.format("%.2f", total));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setForeground(COLOR_PRINCIPAL);
        bottomPanel.add(lblTotal, BorderLayout.WEST);

        JButton btnComprar = new JButton("Confirmar Pedido");
        btnComprar.setBackground(COLOR_SECUNDARIO);
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComprar.setFocusPainted(false);
        
        btnComprar.addActionListener(e -> {
            Pedido pedido = tienda.realizarPedido();
            if (pedido != null) {
                JOptionPane.showMessageDialog(null, "Pedido realizado exitosamente!\n" + pedido, 
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
                model.setRowCount(0);
                lblTotal.setText("Total: $0.00");
            }
        });
        
        bottomPanel.add(btnComprar, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "Carrito de Compras", JOptionPane.PLAIN_MESSAGE);
    }

    private void initAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_PRINCIPAL);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titulo = new JLabel("PANEL ADMINISTRADOR");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        topPanel.add(titulo, BorderLayout.WEST);
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLogout.setBackground(COLOR_ERROR);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            tienda.logout();
            usuarioActual = null;
            cardLayout.show(mainPanel, "Login");
        });
        topPanel.add(btnLogout, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Tab 1: Usuarios
        JPanel usuariosPanel = crearPanelUsuarios();
        tabs.addTab("Usuarios", usuariosPanel);

        // Tab 2: Productos
        JPanel productosPanel = crearPanelProductos();
        tabs.addTab("Productos", productosPanel);

        // Tab 3: Pedidos
        JPanel pedidosPanel = crearPanelPedidos();
        tabs.addTab("Pedidos", pedidosPanel);

        panel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(panel, "Admin");
        cardLayout.show(mainPanel, "Admin");
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(COLOR_FONDO);

        String[] cols = {"ID", "Nombre", "Email", "Rol"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(25);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        for (Usuario u : tienda.getTodosUsuarios()) {
            model.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getRol()});
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(COLOR_FONDO);

        String[] cols = {"ID", "Nombre", "Precio", "Stock", "Categoria"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(25);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        for (Producto p : tienda.getCatalogo()) {
            model.addRow(new Object[]{p.getId(), p.getNombre(), "$" + String.format("%.2f", p.getPrecio()), 
                         p.getStock(), "Electrónica"});
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(COLOR_FONDO);

        String[] cols = {"ID Pedido", "Total", "Estado"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(25);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        for (Pedido p : tienda.getTodosPedidos()) {
            model.addRow(new Object[]{p.getId(), "$" + String.format("%.2f", p.getTotal()), p.getEstado()});
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcomerceGUI().setVisible(true));
    }
}
