package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DisattivaNotificheDAO implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params){
        Annuncio annuncio = (Annuncio) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call disattivaNotifichePerAnnuncio(?,?)}");
            cs.setString(1, LoggedUser.getUsername());
            cs.setInt(2, annuncio.getIdAnnuncio());
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Notifiche disattivate con successo\n";
    }
}
