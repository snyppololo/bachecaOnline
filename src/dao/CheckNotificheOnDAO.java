package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import utils.LoggedUser;

import java.sql.*;

public class CheckNotificheOnDAO implements GenericProcedureDAO<Boolean> {
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Annuncio ann = (Annuncio) params[0];
        boolean output = false;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call checkNotificheOn(?,?,?)}");
            cs.setString(1, LoggedUser.getUsername());
            cs.setInt(2, ann.getIdAnnuncio());
            cs.registerOutParameter(3, java.sql.Types.BOOLEAN);
            cs.execute();

            output = cs.getBoolean(3);
        } catch (SQLException e) {
            throw new DAOException("CheckNotifiche error: " + e.getMessage());
        }
        return output;
    }
}
