package ecomerce;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EcomerceGUI extends JFrame {
    private Tienda tienda = new Tienda();
    private Usuario usuarioActual;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public EcomerceGUI() {
        setTitle("Tienda Online - Java Desktop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initLoginPanel();
        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private void initLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField txtEmail = new JTextField(20);
        JPasswordField txtPass = new JPasswordField(20);
        JButton btnLogin = new JButton("Iniciar Sesión");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; panel.add(txtPass, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String pass = new String(txtPass.getPassword());
            usuarioActual = tienda.login(email, pass);
            if (usuarioActual != null) {
                if (usuarioActual instanceof Administrador) initAdminDashboard();
                else initClienteDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(panel, "Login");
    }

    private void initClienteDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Bienvenido, " + usuarioActual.getNombre(), SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcome, BorderLayout.NORTH);

        String[] cols = {"ID", "Nombre", "Precio", "Stock"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        for (Producto p : tienda.getCatalogo()) {
            model.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getStock()});
        }

        JButton btnAdd = new JButton("Agregar al Carrito");
        btnAdd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                tienda.agregarAlCarrito(id, 1);
                JOptionPane.showMessageDialog(this, "Producto añadido");
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnAdd, BorderLayout.SOUTH);

        mainPanel.add(panel, "Cliente");
        cardLayout.show(mainPanel, "Cliente");
    }

    private void initAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("PANEL ADMINISTRADOR", SwingConstants.CENTER), BorderLayout.NORTH);
        
        JTextArea area = new JTextArea("Lista de Usuarios:\n");
        for(Usuario u : tienda.getTodosUsuarios()){
            area.append(u.getNombre() + " - " + u.getRol() + "\n");
        }
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        mainPanel.add(panel, "Admin");
        cardLayout.show(mainPanel, "Admin");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcomerceGUI().setVisible(true));
    }
}