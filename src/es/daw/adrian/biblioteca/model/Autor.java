/*
 * Modelo básico para Autor
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
public class Autor {
    int id;
    String nombre,nacionalidad;
    
    
    
  
}