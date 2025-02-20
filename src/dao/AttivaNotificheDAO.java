package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AttivaNotificheDAO implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params) throws DAOException {
        Annuncio annuncio = (Annuncio) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call attivaNotifichePerAnnuncio(?,?)}");
            cs.setString(1, LoggedUser.getUsername());
            cs.setInt(2, annuncio.getIdAnnuncio());
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("AttivaNotificheDAO error: "+e.getMessage());
        }
        return "Notifiche attivate con successo";
    }
}
