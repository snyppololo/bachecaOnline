package dao;

import exception.DAOException;
import factory.ConnectionFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportAnnunciVendutiDAO implements GenericProcedureDAO<String> {
    @Override
    public String execute(Object... params) throws DAOException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call reportPercentualeAnnunciVendutiUtente}");
            ResultSet rs = cs.executeQuery();
            System.out.printf("\n%-18s %-18s %-15s %-10s%n", "Utente", "Annunci pubblicati", "Annunci venduti", "Percentuale");
            while (rs.next()) {
                System.out.printf("%-18s %-18s %-15s %-10s%n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            throw new DAOException("ReportAnnunciVenduti error: " + e.getMessage());
        }

        return "\nReport generato con successo in data " + LocalDate.now();
    }
}
