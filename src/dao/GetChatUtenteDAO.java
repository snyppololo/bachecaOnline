package dao;

import exception.AnnuncioGiaVendutoException;
import exception.DAOException;
import factory.ConnectionFactory;
import model.ChatPreview;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetChatUtenteDAO implements GenericProcedureDAO<List<ChatPreview>> {
    @Override
    public List<ChatPreview> execute(Object... params) throws SQLException {
        List<ChatPreview> chatPreviews = new ArrayList<ChatPreview>();

        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call getChatUtente(?)}");
            cs.setString(1, LoggedUser.getUsername());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                ChatPreview cp = new ChatPreview();
                cp.setAltroUtente(rs.getString("altro_utente"));
                cp.setIdAnnuncio(rs.getInt("M.annuncio"));
                cp.setTitoloAnnuncio(rs.getString("A.titolo"));
                cp.setUltimaAttivita(rs.getTimestamp("ultima_attivita").toLocalDateTime());
                chatPreviews.add(cp);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return chatPreviews;
    }
}
