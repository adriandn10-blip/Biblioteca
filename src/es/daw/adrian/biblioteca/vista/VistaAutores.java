package es.daw.adrian.biblioteca.vista;

import es.daw.adrian.biblioteca.dao.AutorDAO;
import es.daw.adrian.biblioteca.model.Autor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaAutores extends JPanel {
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtNacionalidad;
    private JList<String> listaAutores;
    private DefaultListModel<String> modeloLista;
    private AutorDAO autorDAO;

    public VistaAutores() {
        autorDAO = new AutorDAO();
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(3, 2));
        panelDatos.add(new JLabel("ID:"));
        txtId = new JTextField(10);
        txtId.setEditable(false);
        panelDatos.add(txtId);
        panelDatos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField(20);
        panelDatos.add(txtNacionalidad);

        JPanel panelBotones = new JPanel();
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        modeloLista = new DefaultListModel<>();
        listaAutores = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAutores);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String nacionalidad = txtNacionalidad.getText();
                if (!nombre.isEmpty()) {
                    autorDAO.insertar(new Autor(0, nombre, nacionalidad));
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
                    String nacionalidad = txtNacionalidad.getText();
                    if (!nombre.isEmpty()) {
                        autorDAO.actualizar(new Autor(id, nombre, nacionalidad));
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
                    autorDAO.eliminar(id);
                    limpiarCampos();
                    actualizarLista();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido");
                }
            }
        });

        listaAutores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccion = listaAutores.getSelectedValue();
                if (seleccion != null) {
                    String[] partes = seleccion.split(" - ");
                    int id = Integer.parseInt(partes[0]);
                    ArrayList<Autor> autores = autorDAO.leerTodos();
                    for (Autor autor : autores) {
                        if (autor.getId() == id) {
                            txtId.setText(String.valueOf(autor.getId()));
                            txtNombre.setText(autor.getNombre());
                            txtNacionalidad.setText(autor.getNacionalidad());
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
        txtNacionalidad.setText("");
    }

    private void actualizarLista() {
        modeloLista.clear();
        ArrayList<Autor> autores = autorDAO.leerTodos();
        for (Autor autor : autores) {
            modeloLista.addElement(autor.getId() + " - " + autor.getNombre());
        }
    }
}