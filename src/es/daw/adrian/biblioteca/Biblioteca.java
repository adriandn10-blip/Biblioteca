/*
 * Aplicación simple de biblioteca
 */
package es.daw.adrian.biblioteca;
import es.daw.adrian.biblioteca.vista.BibliotecaApp;
import javax.swing.SwingUtilities;


/**
 *
 * @author adrian
 */
public class Biblioteca {

  public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              new BibliotecaApp();
          }
      });
  }


}