package dao;

import exception.DAOException;
import factory.ConnectionFactory;
import utils.Credentials;
import utils.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginProcedureDAO implements GenericProcedureDAO<Credentials> {

    @Override
    public Credentials execute(Object... params) throws DAOException {
        String username = (String) params[0];
        String password = (String) params[1];
        String role;
        Role loggedRole = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.executeQuery();
            role = cs.getString(3);
        } catch(SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        }

        if (role.equals(Role.admin.toString())) {
            loggedRole = Role.admin;
        }else if(role.equals(Role.user.toString())) {
            loggedRole = Role.user;
        }


        return new Credentials(username, password, loggedRole);
    }

}
