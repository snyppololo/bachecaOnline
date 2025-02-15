package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CreaAnnuncioDAO implements GenericProcedureDAO<String> {
    @Override
    public String execute(Object... params) throws DAOException {
        Annuncio annuncio = (Annuncio) params[0];
        try {
            CallableStatement cs = getCallableStatement(annuncio);
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("CreaAnnuncio error: " + e.getMessage());
        }
        return "Annuncio creato con successo!";

    }

    private static CallableStatement getCallableStatement(Annuncio ann) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs = conn.prepareCall("{call creaAnnuncio(?,?,?,?)}");
        cs.setString(1, ann.getUtente());
        cs.setString(2, ann.getTitolo());
        cs.setString(3, ann.getDescrizione());
        cs.setString(4, ann.getCategoria());
        return cs;
    }
}
