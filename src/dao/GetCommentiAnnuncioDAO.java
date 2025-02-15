package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import model.ChatPreview;
import model.Commento;
import model.Messaggio;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCommentiAnnuncioDAO implements GenericProcedureDAO<List<Commento>> {
    @Override
    public List<Commento> execute(Object... params) throws DAOException, SQLException {
        Annuncio ann = (Annuncio) params[0];
        List<Commento> commenti = new ArrayList<Commento>();
        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call getCommentiAnnuncio(?)}");
            cs.setInt(1, ann.getIdAnnuncio());

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Commento c = new Commento();
                c.setIdCommento(rs.getInt("id_commento"));
                c.setUtente(rs.getString("utente"));
                c.setIdAnnuncio(rs.getInt("annuncio"));
                c.setTimestamp(rs.getTimestamp("ts_commento").toLocalDateTime());
                c.setTesto(rs.getString("testo"));
                commenti.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commenti;
    }
}
