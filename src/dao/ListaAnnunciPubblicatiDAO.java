package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import utils.LoggedUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaAnnunciPubblicatiDAO implements GenericProcedureDAO<List<Annuncio>>{
    @Override
    public List<Annuncio> execute(Object... params) throws DAOException, SQLException {
        List<Annuncio> annunci = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call listaAnnunciPubblicatiUtente(?)}");
            cs.setString(1, LoggedUser.getUsername());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Annuncio ann = new Annuncio();
                ann.setIdAnnuncio(rs.getInt("id_annuncio") );
                ann.setUtente(rs.getString("utente"));
                ann.setTitolo(rs.getString("titolo"));
                ann.setDescrizione(rs.getString("descrizione"));
                ann.setCategoria(rs.getString("categoria"));
                ann.setDataPubblicazione(rs.getDate("data_pubblicazione"));
                ann.setDataVendita(rs.getDate("data_vendita"));
                annunci.add(ann);
            }
        } catch(SQLException e) {
            throw new DAOException("ListaAnnunciPubblicatiDAO error: " + e.getMessage());
        }
        return annunci;
    }
}
