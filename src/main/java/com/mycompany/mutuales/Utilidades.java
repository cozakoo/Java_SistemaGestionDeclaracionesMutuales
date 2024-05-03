package com.mycompany.mutuales;

import enumeradores.TipoMutual;
import interfaz.IConsultaSql;
import static interfaz.IConsultaSql.consulta_mutual_existe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Utilidades {

    public static String obtenerTipoMutual_str(TipoMutual t_mutual) {

        if (TipoMutual.A == t_mutual) {
            return "Prestamo";
        } else {
            return "RECLAMO";
        }
    }

    /**
     * Obtiene el nombre de un mes a partir de su número y el año actual.
     *
     * @param numeroMes El número del mes (0 para enero, 1 para febrero, etc.).
     * @return El nombre del mes en formato "NombreMes Año".
     */
    public static String obtenerNombreMes(int numeroMes) {

        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return nombresMeses[numeroMes - 1];
    }

    public static int obtenerIdMutual(String nombre) throws SQLException {
        // Consulta para contar los registros en la tabla Mutual
        String contarRegistrosSQL = "SELECT id FROM mutual WHERE nombre ='" + nombre + "'";
        ResultSet resultSet = DataBase.getInstance(true).consulta(contarRegistrosSQL);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1; // En caso de error, consideramos que la tabla está vacía
    }

    public static String obtenerNombreMutual(int id) {
        try {
            ResultSet consulta = DataBase.getInstance(true).consulta("SELECT nombre from mutual \n"
                    + "where id = " + id);
            consulta.next();
            return consulta.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " no encontrado";
    }

    public static TipoMutual obtenerTipoArchivo(String tipoArchivo) {
        return "R".equals(tipoArchivo) ? TipoMutual.R : TipoMutual.A;
    }

    public static TipoMutual obtenerTipoArchivoCheck(javax.swing.JRadioButton reclamoRadioButton) {
        return reclamoRadioButton.isSelected() ? TipoMutual.R : TipoMutual.A;
    }

    public static String obtenerTipoArchivoMutual(javax.swing.JRadioButton reclamoRadioButton) {
        return obtenerTipoMutual(reclamoRadioButton).equals("R") ? "Reclamo" : "Prestamo";
    }

    public static String obtenerTipoMutual(javax.swing.JRadioButton reclamoRadioButton) {
        return reclamoRadioButton.isSelected() ? "R" : "A";
    }

    public static boolean validarSeleccion(JRadioButton PrestamojRadioButton, JRadioButton ReclamojRadioButton) {
        if (!PrestamojRadioButton.isSelected() && !ReclamojRadioButton.isSelected()) {

            JOptionPane.showMessageDialog(null,
                    "Ya existe una complementaria con las mismas caracteristicas",
                    "Error",
                    JOptionPane.ERROR
            );

            JOptionPane.showMessageDialog(null,
                    "Seleccione el Tipo de Complementaria",
                    "Error",
                    JOptionPane.ERROR
            );

//            new Notificacion("Seleccione el Tipo de Complementaria").setVisible(true);
            return false;
        }
        return true;
    }

    public static boolean validarCampos(
            JTextField OrigenjTextField,
            JTextField DestinojTextField1,
            JTextField Concepto1jTextField2,
            JTextField Concepto2jTextField3) {

        return !OrigenjTextField.getText().isEmpty()
                && !DestinojTextField1.getText().isEmpty()
                && !Concepto1jTextField2.getText().isEmpty()
                && !Concepto2jTextField3.getText().isEmpty();
    }

    public static boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int validarYConvertirAEntero(String valor) {
        if (valor.matches("\\d+")) {
            return Integer.parseInt(valor);
        } else {
            // Puedes manejar la situación de error según tus necesidades.
            // En este caso, simplemente retorno -1 como valor predeterminado.
            return -1;
        }
    }

    public static javax.swing.ComboBoxModel<String> obtenerMutuales() throws SQLException {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

        try {
            ResultSet mutuales = DataBase.getInstance(true).consulta(IConsultaSql.consulta_mutual_listado);

            while (mutuales.next()) {
                modelo.addElement(mutuales.getString("nombre"));
            }
        } finally {
            return modelo;
        }
    }

    public static String convertirPrimeraLetraMayuscula(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizar = true;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizar = true;
            } else if (capitalizar) {
                c = Character.toTitleCase(c);
                capitalizar = false;
            } else {
                c = Character.toLowerCase(c);
            }
            result.append(c);
        }

        return result.toString();
    }

    public static Timestamp obtenerFechaActual() {
        // Obtén la fecha y hora actual del sistema
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }

    public static String agregarExtensionTxt(String cadena) {
        // Obtener el texto principal antes de la extensión
        String textoPrincipal = cadena.substring(0, cadena.lastIndexOf('.'));

        // Convertir la extensión a minúsculas
        String extension = cadena.substring(cadena.lastIndexOf('.')).toLowerCase();

        // Unir el texto principal con la extensión
        return textoPrincipal + extension;
    }

    public static boolean existeMutualEnBaseDeDatos(String nombre) throws SQLException {
        boolean existe = false;
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_mutual_existe);
        pstmt.setString(1, nombre);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            existe = count > 0;
        }
        pstmt.close();
        return existe;
    }

    public static boolean mensajeConfirmar(String pregunta, String titulo) {
        // Cambiar el texto del botón "Yes" a "Si"
        UIManager.put("OptionPane.yesButtonText", "Si");

        // Pregunta al usuario para confirmar la operación
        int confirmacion = JOptionPane.showConfirmDialog(null,
                pregunta, titulo,
                JOptionPane.YES_NO_OPTION);

        // Restaurar el texto original del botón "Yes"
        UIManager.put("OptionPane.yesButtonText", "Yes");

        return confirmacion == JOptionPane.YES_OPTION;
    }

    public static void mensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void mensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Exito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static int obtenerIdComplementaria(String nombreMutual, String tipo, int concepto) throws SQLException {

        // Consulta para contar los registros en la tabla Mutual
        String contarRegistrosSQL
                = "SELECT\n"
                + "c.id\n"
                + "FROM\n"
                + "complementaria c\n"
                + "LEFT JOIN\n"
                + "Mutual m ON c.id_mutual = m.id\n"
                + "\n"
                + "WHERE \n"
                + "m.nombre = '" + nombreMutual + "'"
                + "AND c.tipo_archivo = '" + tipo + "'"
                + "AND c.concepto1 = '" + concepto + "'";

        ResultSet resultSet = DataBase.getInstance(true).consulta(contarRegistrosSQL);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1; // En caso de error, consideramos que la tabla está vacía

    }

    public static String obtenerOrigenMutual(String nombreMutual) {
        // Consulta para obtener el origen de mi archivo
        String consulta
                = "";
        return null;


    }

}
