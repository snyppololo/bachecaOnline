package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.ChatPreview;
import model.Messaggio;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetMessaggiChatDAO implements GenericProcedureDAO<List<Messaggio>> {
    @Override
    public List<Messaggio> execute(Object... params) throws DAOException {
        ChatPreview cp = (ChatPreview) params[0];
        List<Messaggio> messaggi = new ArrayList<Messaggio>();
        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call getMessaggiChat(?,?,?)}");
            cs.setString(1, LoggedUser.getUsername());
            cs.setString(2, cp.getAltroUtente());
            cs.setInt(3, cp.getIdAnnuncio());

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Messaggio m = new Messaggio();
                m.setIdMessaggio(rs.getInt("id_messaggio"));
                m.setMittente(rs.getString("mittente"));
                m.setDestinatario(rs.getString("destinatario"));
                m.setIdAnnuncio(rs.getInt("annuncio"));
                m.setTimestamp(rs.getTimestamp("ts_messaggio").toLocalDateTime());
                m.setTesto(rs.getString("testo"));
                messaggi.add(m);
            }
        } catch (SQLException e) {
            throw new DAOException("GetMessaggiChat error: " + e.getMessage());
        }
        return messaggi;
    }
}
