/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.daw.adrian.biblioteca.dao;

import es.daw.adrian.biblioteca.config.ConexionDB;
import es.daw.adrian.biblioteca.model.Categoria;
import java.sql.*;
import java.util.ArrayList;


public class CategoriaDAO {
    private Connection conexion;

    public CategoriaDAO() {
        this.conexion = ConexionDB.obtenerConexion();
    }

    public void crear(Categoria categoria) {
        String sql = "INSERT INTO Categoria (nombre) VALUES (?);";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Categoria> leerTodos() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria;";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categorias.add(new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void actualizar(Categoria categoria) {
        String sql = "UPDATE Categoria SET nombre = ? WHERE id = ?;";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?;";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}