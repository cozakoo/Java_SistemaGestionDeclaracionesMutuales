package com.mycompany.mutuales;

import enumeradores.TipoMutual;
import interfaz.IConsultaSql;
import interfaz.IInfoComplementaria;
import interfaz.IMutual;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La clase DataBase proporciona una interfaz para conectarse a la base de datos
 * y ejecutar operaciones relacionadas con la misma. También se encarga de la
 * inicialización y carga de datos iniciales en la base de datos.
 */
public class DataBase implements IMutual, IConsultaSql {

    private static DataBase instance;
    private static Connection conection = null;
    private static Statement stmt = null;
    private PreparedStatement pstmt = null;
    private String sql,
            jdbcUrl = "jdbc:postgresql://192.168.10.76:5432/mutualesServer",
            usuario = "postgres",
            contrasena = "admin";

    /**
     * Obtiene una instancia única de la clase DataBase.
     *
     * @param isClient Indica si la instancia se utiliza en el cliente.
     * @return Una instancia de la clase DataBase.
     */
    public static DataBase getInstance(boolean isClient) {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    /**
     * Crea las tablas en la base de datos si no existen. Define las tablas
     * 'Mutual', 'InfoComplementariaMutual' y 'Usuario'.
     *
     * @throws SQLException Si ocurre un error al ejecutar la operación de
     * creación de tablas.
     * @throws Exception Si ocurre un error inesperado.
     */
    public void crearTablas() throws SQLException, Exception {

        stmt.execute(consulta_mutual_create);
        stmt.execute(consulta_usuario_create);
        stmt.execute(consulta_complementaria_create);
        stmt.execute(consulta_create_informe);

    }

    public void crearTablaComplementaria() throws SQLException {
        cargarDatosInfoComplemenaria();
    }

    /**
     * Carga los datos iniciales en la tabla 'Mutual' de la base de datos. Los
     * datos se obtienen de la matriz DATOS_MUTUAL.
     *
     * @throws SQLException Si ocurre un error al ejecutar la inserción de
     * datos.
     */
    private void cargarDatosMutual() throws SQLException {
        sql = consulta_mutual_alta;
        pstmt = conection.prepareStatement(sql);

//        for (String[] fila : DATOS_MUTUAL) {
//            int id = Integer.parseInt(fila[0]);
//            String descripcion = fila[1];
//            Mutual mutual = new Mutual(id, descripcion);
//            mutual.insertarEnBaseDeDatos();
//        }
    }

    /**
     * Carga los datos iniciales en la tabla 'InfoComplementariaMutual' de la
     * base de datos. Los datos se obtienen de la matriz DATOS_COMPLEMENTARIOS
     * de la interfaz IInfoComplementaria.
     *
     * @throws SQLException Si ocurre un error al ejecutar la inserción de
     * datos.
     */
    public void cargarDatosInfoComplemenaria() throws SQLException {
        sql = consulta_complementaria_alta;
        pstmt = conection.prepareStatement(sql);

        for (String[] fila : IInfoComplementaria.DATOS_COMPLEMENTARIOS) {
            int idMutual = Integer.parseInt(fila[0]);
            String tipoArchivo = fila[1];
            String origen = fila[2];
            String destino = fila[3];
            int concep1 = Integer.parseInt(fila[4]);
            int concep2 = (fila.length == 6) ? Integer.parseInt(fila[5]) : 0;

            InfoComplementaria complementaria;

            if ("A".equals(tipoArchivo)) {
                complementaria = new InfoComplementaria(idMutual, TipoMutual.A, origen, destino, concep1, concep2);
            } else {
                complementaria = new InfoComplementaria(idMutual, TipoMutual.R, origen, destino, concep1, concep2);
            }
            complementaria.insertarEnBaseDeDatos();
        }
    }

    /**
     * Inicializa la conexión con la base de datos y crea tablas necesarias en
     * caso de que no existan.
     *
     * @param modo El modo de funcionamiento, que puede ser "server" o "client".
     * @throws SQLException Si ocurre un error al establecer la conexión con la
     * base de datos.
     * @throws Exception Si ocurre un error inesperado.
     */
    public void inicializar(String modo) throws SQLException, Exception {
        if ("server".equals(modo)) {
            conection = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
            stmt = conection.createStatement();
            this.crearTablas();
            cargarMutual();
            cargarComplementaria();
        } else {
            jdbcUrl = "jdbc:postgresql://192.168.10.76:5432/mutualesServer";
            conection = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
            stmt = conection.createStatement();

        }
    }

    /**
     * Carga los datos iniciales en la tabla 'Mutual' si esta está vacía. Si la
     * tabla ya contiene datos, se muestra un mensaje informativo.
     *
     * @throws SQLException Si ocurre un error al ejecutar la inserción de
     * datos.
     * @throws Exception Si ocurre un error inesperado.
     */
    public void cargarMutual() throws SQLException, Exception {
        if (tablaMutualEstaVacia()) {
            cargarDatosMutual();
        } else {
            System.out.println("La tabla Mutual ya contiene datos. No se realizará ninguna carga.");
        }
    }

    private boolean tablaMutualEstaVacia() throws SQLException {
        // Consulta para contar los registros en la tabla Mutual
        String contarRegistrosSQL = "SELECT COUNT(*) FROM Mutual";
        ResultSet resultSet = stmt.executeQuery(contarRegistrosSQL);
        if (resultSet.next()) {
            int cantidadRegistros = resultSet.getInt(1);
            return cantidadRegistros == 0; // Retorna true si la tabla está vacía
        }
        return true; // En caso de error, consideramos que la tabla está vacía
    }

    public boolean tablaComplementariaEstaVacia() throws SQLException {
        // Consulta para contar los registros en la tabla Mutual
        String contarRegistrosSQL = "SELECT COUNT(*) FROM complementaria";
        ResultSet resultSet = stmt.executeQuery(contarRegistrosSQL);
        if (resultSet.next()) {
            int cantidadRegistros = resultSet.getInt(1);
            return cantidadRegistros == 0; // Retorna true si la tabla está vacía
        }
        return true; // En caso de error, consideramos que la tabla está vacía
    }

    /**
     * Ejecuta una consulta SQL y devuelve un ResultSet con los resultados.
     *
     * @param query La consulta SQL a ejecutar.
     * @return Un ResultSet con los resultados de la consulta.
     */
    public ResultSet consulta(String query) {
        try {
            this.sql = query;
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Consulta vacía");
        }
        return null;
    }

    /**
     * Obtiene un PreparedStatement para ejecutar una consulta preparada.
     *
     * @param sql La consulta preparada.
     * @return Un PreparedStatement listo para su ejecución.
     * @throws SQLException Si ocurre un error al preparar la consulta.
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conection.prepareStatement(sql);
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    Connection getConnection() {
        return conection;
    }

    private void cargarComplementaria() throws SQLException {
        if (tablaComplementariaEstaVacia()) {
            cargarDatosInfoComplemenaria();
        } else {
            System.out.println("La tabla Complementaria ya contiene datos. No se realizará ninguna carga.");
        }
    }

}
