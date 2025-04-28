/*
 * Modelo básico para Libro
 */
package es.daw.adrian.biblioteca.model;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author adrian
 */

@Data
@AllArgsConstructor
public class Libro {
    String isbn,titulo;
    int anio_pub,autor,categoria;
}