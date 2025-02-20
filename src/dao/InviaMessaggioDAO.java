package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Messaggio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class InviaMessaggioDAO implements GenericProcedureDAO<String>{

    @Override
    public String execute(Object... params) throws DAOException {
        Messaggio mess = (Messaggio) params[0];
        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call scriviMessaggio(?,?,?,?)}");
            cs.setString(1, mess.getMittente());
            cs.setString(2, mess.getDestinatario());
            cs.setInt(3, mess.getIdAnnuncio());
            cs.setString(4, mess.getTesto());
            cs.execute();

        }catch (SQLException e){
            throw new DAOException("InviaMessaggio error: "+ e.getMessage());
        }
        return "Messaggio inviato con successo!";
    }
}
