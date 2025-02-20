package dao;

import exception.DAOException;
import factory.ConnectionFactory;

import java.sql.*;

public class TestDAO implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params) throws DAOException {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Chiamata alla stored procedure
            stmt = conn.prepareCall("{CALL registraUtente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            // Imposta i parametri della stored procedure
            stmt.setString(1, "mario.rossi");
            stmt.setString(2, "password123");
            stmt.setString(3, "RSSMRA85M01H501Z");
            stmt.setString(4, "Mario");
            stmt.setString(5, "Rossi");
            stmt.setDate(6, Date.valueOf("1985-01-01"));
            stmt.setString(7, "Via Roma");
            stmt.setString(8, "10");
            stmt.setString(9, "00100");
            stmt.setString(10, "Via Milano");
            stmt.setString(11, "20");
            stmt.setString(12, "20100");
            stmt.setString(13, "Test:mario@gmail.com:1;Telefono:1234567890:0;Email:mario2@gmail.com:0");

            // Esegui la procedura
            boolean hasResults = stmt.execute();

            // Itera sui risultati (in caso di SELECT di debug)
            while (hasResults) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    System.out.println("DEBUG SQL: " + rs.getString(1)); // Legge la prima colonna di ogni risultato
                }
                hasResults = stmt.getMoreResults(); // Passa al risultato successivo (se presente)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "SOS";
    }
}
