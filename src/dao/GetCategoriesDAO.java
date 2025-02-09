package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetCategoriesDAO implements GenericProcedureDAO<List<Categoria>> {
    @Override
    public List<Categoria> execute(Object... params) throws DAOException, SQLException {
        List<Categoria> categories = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call getCategories()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setNomeCat(rs.getString("nome_categoria") );
                if (rs.getString("categoria_superiore") != null){
                    cat.setCatSup(rs.getString("categoria_superiore"));
                }else{
                    cat.setCatSup("root");
                }
                categories.add(cat);
            }
        } catch(SQLException e) {
            throw new DAOException("GetCategoriesDAO error: " + e.getMessage());
        }
        return categories;
    }
}
