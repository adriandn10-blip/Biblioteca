package es.daw.adrian.biblioteca.dao;

import es.daw.adrian.biblioteca.config.ConexionDB;
import es.daw.adrian.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {
    private ConexionDB conexion;
    
    public LibroDAO() {
        this.conexion = new ConexionDB();
    }
    
 
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO Libro (isbn, titulo, anio_publicacion, autor_id, categoria_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, libro.getIsbn());
            pstmt.setString(2, libro.getTitulo());
            pstmt.setInt(3, libro.getAnio_pub());
            pstmt.setInt(4, libro.getAutor());
            pstmt.setInt(5, libro.getCategoria());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }
    
  
    public ArrayList<Libro> obtenerTodos() {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libro";
        try {
            Statement stmt = conexion.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("autor_id"),
                    rs.getInt("categoria_id")
                );
                libros.add(libro);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }
    
 
 
    public Libro obtenerPorIsbn(String isbn) {
        String sql = "SELECT * FROM Libro WHERE isbn = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("autor_id"),
                    rs.getInt("categoria_id")
                );
                rs.close();
                pstmt.close();
                return libro;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener libro: " + e.getMessage());
        }
        return null;
    }
    
    
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE Libro SET titulo = ?, anio_publicacion = ?, autor_id = ?, categoria_id = ? WHERE isbn = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, libro.getTitulo());
            pstmt.setInt(2, libro.getAnio_pub());
            pstmt.setInt(3, libro.getAutor());
            pstmt.setInt(4, libro.getCategoria());
            pstmt.setString(5, libro.getIsbn());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }
    
 
    public boolean eliminar(String isbn) {
        String sql = "DELETE FROM Libro WHERE isbn = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}
