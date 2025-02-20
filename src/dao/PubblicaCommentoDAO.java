package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Commento;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PubblicaCommentoDAO implements GenericProcedureDAO<String> {
    @Override
    public String execute(Object... params) throws DAOException {
        Commento commento = (Commento) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call pubblicaCommento(?,?,?)}");
            cs.setString(1, LoggedUser.getUsername());
            cs.setInt(2, commento.getIdAnnuncio());
            cs.setString(3, commento.getTesto());
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("PubblicaCommento error: " + e.getMessage());
        }
        return "Commento pubblicato con successo!";
    }
}
