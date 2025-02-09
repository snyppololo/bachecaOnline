package dao;

import factory.ConnectionFactory;
import model.Annuncio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CreaAnnuncioDAO implements GenericProcedureDAO<String> {
    @Override
    public String execute(Object... params){

    }

    private static CallableStatement getCallableStatement(Lesson lsn) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs = conn.prepareCall("{call creaAnnuncio(?,?,?,?,?)}");
        cs.setString(1, lsn.getCourse());
        cs.setString(2, lsn.getCommaDays());
        cs.setTime(3, lsn.getStartAt());
        cs.setTime(4, lsn.getEndAt());
        cs.setString(5, lsn.getTub());

        return cs;
    }
}
