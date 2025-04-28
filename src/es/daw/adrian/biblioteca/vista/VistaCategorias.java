package es.daw.adrian.biblioteca.vista;

import es.daw.adrian.biblioteca.dao.CategoriaDAO;
import es.daw.adrian.biblioteca.model.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaCategorias extends JPanel {
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTextField txtId;
    private JTextField txtNombre;
    private JList<String> listaCategorias;
    private DefaultListModel<String> modeloLista;
    private CategoriaDAO categoriaDAO;

    public VistaCategorias() {
        categoriaDAO = new CategoriaDAO();
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(2, 2));
        panelDatos.add(new JLabel("ID:"));
        txtId = new JTextField(10);
        txtId.setEditable(false);
        panelDatos.add(txtId);
        panelDatos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelDatos.add(txtNombre);

        JPanel panelBotones = new JPanel();
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        modeloLista = new DefaultListModel<>();
        listaCategorias = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaCategorias);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                if (!nombre.isEmpty()) {
                    categoriaDAO.crear(new Categoria(0, nombre));
                    limpiarCampos();
                    actualizarLista();
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre es obligatorio");
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nombre = txtNombre.getText();
                    if (!nombre.isEmpty()) {
                        categoriaDAO.actualizar(new Categoria(id, nombre));
                        limpiarCampos();
                        actualizarLista();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    categoriaDAO.eliminar(id);
                    limpiarCampos();
                    actualizarLista();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido");
                }
            }
        });

        listaCategorias.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccion = listaCategorias.getSelectedValue();
                if (seleccion != null) {
                    String[] partes = seleccion.split(" - ");
                    int id = Integer.parseInt(partes[0]);
                    ArrayList<Categoria> categorias = categoriaDAO.leerTodos();
                    for (Categoria categoria : categorias) {
                        if (categoria.getId() == id) {
                            txtId.setText(String.valueOf(categoria.getId()));
                            txtNombre.setText(categoria.getNombre());
                            break;
                        }
                    }
                }
            }
        });

        actualizarLista();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
    }

    private void actualizarLista() {
        modeloLista.clear();
        ArrayList<Categoria> categorias = categoriaDAO.leerTodos();
        for (Categoria categoria : categorias) {
            modeloLista.addElement(categoria.getId() + " - " + categoria.getNombre());
        }
    }
}
