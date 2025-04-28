package es.daw.adrian.biblioteca.vista;

import es.daw.adrian.biblioteca.config.ConexionDB;
import javax.swing.*;
import java.awt.*;

public class BibliotecaApp extends JFrame {

    public BibliotecaApp() {
        setTitle("Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ConexionDB conexion = new ConexionDB();
        conexion.crearTablas();
        JTabbedPane tabbedPane = new JTabbedPane();
    
        JPanel librosPanel = new VistaLibros();
       
        tabbedPane.addTab("Libros", librosPanel);
    
        JPanel autoresPanel = new VistaAutores();
        tabbedPane.addTab("Autores", autoresPanel);
    
        JPanel categoriasPanel = new VistaCategorias();
        tabbedPane.addTab("Categorías", categoriasPanel);
    
        add(tabbedPane);
        setVisible(true);
    }
}


