package com.mycompany.mutuales;

import enumeradores.TipoMutual;

import static interfaz.IConsultaSql.consulta_complementaria_alta;
import static interfaz.IConsultaSql.consulta_complementaria_existe;
import static interfaz.IConsultaSql.consulta_complementaria_modificacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

public class InfoComplementaria {

    private int idComplementaria, idMutual, concep1, concep2;
    private String origen, destino;
    private TipoMutual tipoMutual;

    public InfoComplementaria(int idMutual, TipoMutual tipoMutual, String origen, String destino, int concep1, int concep2) throws SQLException {

        this.idMutual = idMutual;
        this.tipoMutual = tipoMutual;
        this.origen = origen;
        this.destino = destino;
        this.concep1 = concep1;
        this.concep2 = concep2;
    }

    public InfoComplementaria(TipoMutual tipoMutual) throws SQLException {
        this.tipoMutual = tipoMutual;
    }

    public InfoComplementaria(TipoMutual tipoMutual, String origen, String destino, int concep1, int concep2) throws SQLException {

        this.tipoMutual = tipoMutual;
        this.origen = origen;
        this.destino = destino;
        this.concep1 = concep1;
        this.concep2 = concep2;

    }

    public void setConcep1(int concep1) {
        this.concep1 = concep1;
    }

    public void setConcep2(int concep2) {
        this.concep2 = concep2;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getIdMutual() {
        return idMutual;
    }

    public void setIdMutual(int idMutual) {
        this.idMutual = idMutual;
    }

    public int getConcep1() {
        return concep1;
    }

    public String getDestino() {
        return destino;
    }

    public TipoMutual getTipoMutual() {
        return tipoMutual;
    }

    public InfoComplementaria(int idComplementaria) {
        this.idComplementaria = idComplementaria;
    }

    public int getIdComplementaria() {
        return idComplementaria;
    }

    public void insertarEnBaseDeDatos() throws SQLException {
        if (!existeComplementaria()) {
            Timestamp fechaActual = Utilidades.obtenerFechaActual();
            try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_complementaria_alta)) {
                pstmt.setInt(1, this.idMutual);
                pstmt.setString(2, this.tipoMutual.getTipoMutualAsString());
                pstmt.setString(3, this.origen);
                pstmt.setString(4, this.destino);
                pstmt.setInt(5, this.concep1);
                pstmt.setInt(6, this.concep2);
                pstmt.setTimestamp(7, fechaActual);
                pstmt.setTimestamp(8, fechaActual);
                pstmt.executeUpdate();
            }
        }
    }

    public void modificarEnBaseDeDatos(String nuevoorigen, String nuevodestino, int nuevoconcep1, int nuevoconep2) throws SQLException {

        Timestamp fechaActual = Utilidades.obtenerFechaActual();

        try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_complementaria_modificacion)) {

            pstmt.setString(1, nuevoorigen);
            pstmt.setString(2, nuevodestino);
            pstmt.setInt(3, nuevoconcep1);
            pstmt.setInt(4, nuevoconep2);
//            pstmt.setTimestamp(8, fechaActual);
            pstmt.setInt(5, this.idComplementaria);
            pstmt.executeUpdate();
        }
    }

    public void completarInformacion() throws SQLException {
        // Consulta para contar los registros en la tabla Mutual
        String contarRegistrosSQL = "SELECT * FROM complementaria WHERE id ='" + this.idComplementaria + "'";
        ResultSet resultSet = DataBase.getInstance(true).consulta(contarRegistrosSQL);
        if (resultSet.next()) {
            this.idMutual = resultSet.getInt(2);
            this.tipoMutual = Utilidades.obtenerTipoArchivo(resultSet.getString(3));
            this.origen = resultSet.getString(4);
            this.destino = resultSet.getString(5);
            this.concep1 = Integer.parseInt(resultSet.getString(6));
            if (resultSet.getString(7) != null) {
                this.concep2 = Integer.parseInt(resultSet.getString(7));
            } else {
                this.concep2 = 0;
            }
        }
    }

    public String getOrigen() {
        return origen;
    }

    public int getConcep2() {
        return concep2;
    }

    private boolean existeComplementaria() throws SQLException {

        boolean existe = false;
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_complementaria_existe);

        pstmt.setInt(1, this.idMutual);
        pstmt.setString(2, this.tipoMutual.name());
        pstmt.setInt(3, this.concep1);

        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            existe = count > 0;
        }
        pstmt.close();
        return existe;
    }

}
