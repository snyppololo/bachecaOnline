package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Categoria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AggiungiCategoriaDAO implements GenericProcedureDAO<String> {
    @Override
    public String execute(Object... params) throws DAOException {
        Categoria cat = (Categoria) params[0];
        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call aggiungiCategoria(?,?)}");
            cs.setString(1, cat.getNomeCat());
            cs.setString(2, cat.getCatSup());
            cs.execute();
        }catch (SQLException e) {
            throw new DAOException("AggiungiCategoria error: " + e.getMessage());
        }
        return "\nCategoria "+cat.getNomeCat()+" aggiunta con successo";
    }
}
