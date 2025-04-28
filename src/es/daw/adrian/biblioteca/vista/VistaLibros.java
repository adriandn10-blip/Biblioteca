package es.daw.adrian.biblioteca.vista;

import es.daw.adrian.biblioteca.dao.AutorDAO;
import es.daw.adrian.biblioteca.dao.CategoriaDAO;
import es.daw.adrian.biblioteca.dao.LibroDAO;
import es.daw.adrian.biblioteca.model.Autor;
import es.daw.adrian.biblioteca.model.Categoria;
import es.daw.adrian.biblioteca.model.Libro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaLibros extends JPanel {
    private JButton btnCrear;

    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JTextField txtAnio;
    private JComboBox<String> cmbAutor;
    private JComboBox<String> cmbCategoria;
    private JList<String> listaLibros;
    private DefaultListModel<String> modeloLista;
    private LibroDAO libroDAO;

    public VistaLibros() {
        libroDAO = new LibroDAO();
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(5, 2));
        panelDatos.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField(20);
        panelDatos.add(txtIsbn);
        panelDatos.add(new JLabel("Título:"));
        txtTitulo = new JTextField(30);
        panelDatos.add(txtTitulo);
        panelDatos.add(new JLabel("Año:"));
        txtAnio = new JTextField(10);
        panelDatos.add(txtAnio);
        panelDatos.add(new JLabel("Autor:"));
        cmbAutor = new JComboBox<>();
        panelDatos.add(cmbAutor);
        panelDatos.add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>();
        panelDatos.add(cmbCategoria);
        cargarAutores();
        cargarCategorias();

        JPanel panelBotones = new JPanel();
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        modeloLista = new DefaultListModel<>();
        listaLibros = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaLibros);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = txtIsbn.getText();
                    String titulo = txtTitulo.getText();
                    int anio = Integer.parseInt(txtAnio.getText());
                    String autorSeleccionado = (String) cmbAutor.getSelectedItem();
                    String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();
                    int autorId = Integer.parseInt(autorSeleccionado.split(" - ")[0]);
                    int categoriaId = Integer.parseInt(categoriaSeleccionada.split(" - ")[0]);

                    if (!isbn.isEmpty() && !titulo.isEmpty()) {
                        libroDAO.insertar(new Libro(isbn, titulo, anio, autorId, categoriaId));
                        limpiarCampos();
                        actualizarLista();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos");
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = txtIsbn.getText();
                    String titulo = txtTitulo.getText();
                    int anio = Integer.parseInt(txtAnio.getText());
                    if (!isbn.isEmpty() && !titulo.isEmpty()) {
                        String autorSeleccionado = (String) cmbAutor.getSelectedItem();
                        String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();
                        if (autorSeleccionado != null && categoriaSeleccionada != null) {
                            int autorId = Integer.parseInt(autorSeleccionado.split(" - ")[0]);
                            int categoriaId = Integer.parseInt(categoriaSeleccionada.split(" - ")[0]);
                            libroDAO.actualizar(new Libro(isbn, titulo, anio, autorId, categoriaId));
                            limpiarCampos();
                            actualizarLista();
                            JOptionPane.showMessageDialog(null, "Libro actualizado correctamente");
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione un autor y una categoría");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = txtIsbn.getText();
                if (!isbn.isEmpty()) {
                    libroDAO.eliminar(isbn);
                    limpiarCampos();
                    actualizarLista();
                }
            }
        });

        listaLibros.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccion = listaLibros.getSelectedValue();
                if (seleccion != null) {
                    String[] partes = seleccion.split(" - ");
                    String isbn = partes[0];
                    ArrayList<Libro> libros = libroDAO.obtenerTodos();
                    for (Libro libro : libros) {
                        if (libro.getIsbn().equals(isbn)) {
                            txtIsbn.setText(libro.getIsbn());
                            txtTitulo.setText(libro.getTitulo());
                            txtAnio.setText(String.valueOf(libro.getAnio_pub()));
                            
                            for (int i = 0; i < cmbAutor.getItemCount(); i++) {
                                if (cmbAutor.getItemAt(i).startsWith(String.valueOf(libro.getAutor()))) {
                                    cmbAutor.setSelectedIndex(i);
                                    break;
                                }
                            }
                            
                            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                                if (cmbCategoria.getItemAt(i).startsWith(String.valueOf(libro.getCategoria()))) {
                                    cmbCategoria.setSelectedIndex(i);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        actualizarLista();
    }

    private void limpiarCampos() {
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAnio.setText("");
        cmbAutor.setSelectedIndex(0);
        cmbCategoria.setSelectedIndex(0);
    }

    private void cargarAutores() {
        AutorDAO autorDAO = new AutorDAO();
        ArrayList<Autor> autores = autorDAO.leerTodos();
        cmbAutor.removeAllItems();
        for (Autor autor : autores) {
            cmbAutor.addItem(autor.getId() + " - " + autor.getNombre());
        }
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ArrayList<Categoria> categorias = categoriaDAO.leerTodos();
        cmbCategoria.removeAllItems();
        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria.getId() + " - " + categoria.getNombre());
        }
    }

    private void actualizarLista() {
        modeloLista.clear();
        ArrayList<Libro> libros = libroDAO.obtenerTodos();
        for (Libro libro : libros) {
            modeloLista.addElement(libro.getIsbn() + " - " + libro.getTitulo());
        }
    }
}

