package controller;

import factory.ConnectionFactory;
import utils.Role;
import view.AdminView;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController implements Controller{
    @Override
    public void start() {
        try{
            ConnectionFactory.changeRole(Role.admin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while(true){
            int choice;
            try{
                choice = AdminView.showMenu();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }


    }
}
