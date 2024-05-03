package com.mycompany.mutuales;

import interfaz.IConsultaSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * La clase Mutual representa una entidad Mutual en la base de datos. Contiene
 * métodos para insertar, eliminar y modificar registros de mutual en la base de
 * datos.
 */
public class Mutual implements IConsultaSql {

    private boolean estado = Boolean.TRUE;
    private String nombre;
    private int id;

    /**
     * Crea una nueva instancia de la clase Mutual con el ID y la descripción
     * proporcionados.
     *
     * @param nombre
     */
    public Mutual(String nombre) {
        this.nombre = nombre;
        estado = Boolean.TRUE;
    }

    public Mutual(int id) {
        this.id = id;
    }

    public Mutual(int id, String nombre, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        estado = this.estado;
    }

    public Mutual() {
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Inserta un registro de mutual en la base de datos. Este método marca la
     * mutual como activa en la base de datos.
     */
    public void insertarEnBaseDeDatos() throws SQLException {
        if (!existeMutual()) {
            Timestamp fechaActual = Utilidades.obtenerFechaActual();
            String sql = consulta_mutual_alta;
            try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(sql)) {
                pstmt.setString(1, this.nombre);
                pstmt.setBoolean(2, this.estado);
                pstmt.setTimestamp(3, fechaActual);
                pstmt.setNull(4, java.sql.Types.TIMESTAMP);
                pstmt.setTimestamp(5, fechaActual);
                pstmt.executeUpdate();
            }
        }
    }

    /**
     * Modifica una mutual existente en la base de datos y la marca como
     * activa.El nuevo ID y descripción se establecen en el registro
     * correspondiente.
     *
     * @param nuevaDescripcion La nueva descripción para la mutual.
     */
    public void modificarMutualActiva(String nuevaDescripcion) throws SQLException {

        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_mutual_modificar);
        pstmt.setString(1, nuevaDescripcion);

        pstmt.setTimestamp(2, Utilidades.obtenerFechaActual());
        pstmt.setInt(3, this.id); // Identifica la mutual por su ID original
        pstmt.setBoolean(4, true); // Asegura que la mutual esté activa
        pstmt.executeUpdate();

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Marca una mutual como inactiva en la base de datos.
     */
    public void eliminarLogicaMutualEnBaseDeDatos() throws SQLException {
        Timestamp fechaActual = Utilidades.obtenerFechaActual();
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_mutual_baja_logica);
        pstmt.setBoolean(1, false); // Marcar la mutual como inactiva
        pstmt.setTimestamp(2, fechaActual); // Establecer la fecha de baja
        pstmt.setTimestamp(3, fechaActual); // Establecer la fecha de modificacion
        pstmt.setInt(4, this.id); // Identificar la mutual por su ID
        pstmt.executeUpdate();
        pstmt.close();
    }

    public boolean existeMutual() throws SQLException {
        boolean existe = false;
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_mutual_existe);
        pstmt.setString(1, this.nombre);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            existe = count > 0;
        }
        pstmt.close();
        return existe;
    }

    public void eliminarFisicaMutualEnBaseDeDatos() throws SQLException {
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(
                consulta_mutual_baja_fisica);
        pstmt.setInt(1, Utilidades.obtenerIdMutual(this.nombre));
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void activar() throws SQLException {
        try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(
                consulta_mutual_activar)) {
            pstmt.setInt(1, this.id); // Identificar la mutual por su ID
            pstmt.executeUpdate();
        }
    }
}
