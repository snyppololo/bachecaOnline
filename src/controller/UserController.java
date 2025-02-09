package controller;

import factory.ConnectionFactory;
import model.Annuncio;
import utils.Role;
import view.UserView;

import java.io.IOException;
import java.sql.SQLException;

public class UserController implements Controller{
    @Override
    public void start() {
        try {
            ConnectionFactory.changeRole(Role.user);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        while (true){
            int choice;
            try {
                choice = UserView.showMenu();
                switch (choice){
                    case 1 ->
                }
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

}
