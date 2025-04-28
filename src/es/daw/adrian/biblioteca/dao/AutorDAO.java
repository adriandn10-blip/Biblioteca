package es.daw.adrian.biblioteca.dao;

import es.daw.adrian.biblioteca.config.ConexionDB;
import es.daw.adrian.biblioteca.model.Autor;
import java.sql.*;
import java.util.ArrayList;

public class AutorDAO {
    private ConexionDB conexion;
    
    public AutorDAO() {
        this.conexion = new ConexionDB();
    }
    
    public boolean insertar(Autor autor) {
        String sql = "INSERT INTO Autor (nombre, nacionalidad) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getNacionalidad());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar autor: " + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Autor> leerTodos() {
        ArrayList<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autor";
        try {
            Statement stmt = conexion.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Autor autor = new Autor(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad")
                );
                autores.add(autor);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener autores: " + e.getMessage());
        }
        return autores;
    }
    
    public Autor obtenerPorId(int id) {
        String sql = "SELECT * FROM Autor WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Autor autor = new Autor(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad")
                );
                rs.close();
                pstmt.close();
                return autor;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener autor: " + e.getMessage());
        }
        return null;
    }
    
    public boolean actualizar(Autor autor) {
        String sql = "UPDATE Autor SET nombre = ?, nacionalidad = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getNacionalidad());
            pstmt.setInt(3, autor.getId());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar autor: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Autor WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar autor: " + e.getMessage());
            return false;
        }
    }
}
