package com.mycompany.mutuales;

import GUI.FormPrincipal;
import GUI.InformesForm;
import GUI.LoginForm;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;





public class Main {
    
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            // Si Nimbus no está disponible, puedes configurar otro o el predeterminado
        }
    }

    /**
     * Punto de entrada principal de la aplicación. Inicializa la base de datos,
     * carga datos iniciales, y muestra la interfaz de usuario principal.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en
     * este método).
     * @throws Exception Si ocurre un error grave durante la ejecución de la
     * aplicación.
     */
    public static void main(String[] args) throws Exception {
        try {
            setLookAndFeel();
            DataBase db = DataBase.getInstance(true);
            db.inicializar("cliente");
            new LoginForm().setVisible(true);
//            new FormPrincipal().setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
