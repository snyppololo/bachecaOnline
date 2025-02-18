package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Utente;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;


public class RegistraUtenteDAO implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params) throws DAOException, SQLException {
        Utente utente = (Utente) params[0];
        String metodiDiContattoFormatted = (String) params[1];
        try {
            CallableStatement cs = getCallableStatement(utente, metodiDiContattoFormatted);
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("RegistraUtente error: " + e.getMessage());
        }
        return "\nUtente "+utente.getUsername()+" registrato con successo";
    }

    private static CallableStatement getCallableStatement(Utente utente, String mdcs) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs = conn.prepareCall("{call registraUtente(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1, utente.getUsername());
        cs.setString(2, utente.getPassword());
        cs.setString(3, utente.getCF());
        cs.setString(4, utente.getNome());
        cs.setString(5, utente.getCognome());
        cs.setDate(6, Date.valueOf(utente.getDataNascita()));
        cs.setString(7, utente.getR_via());
        cs.setString(8, utente.getR_civico());
        cs.setString(9, utente.getR_cap());
        cs.setString(10, utente.getF_via());
        cs.setString(11, utente.getF_civico());
        cs.setString(12, utente.getF_cap());
        cs.setString(13, mdcs);
        return cs;
    }
}
