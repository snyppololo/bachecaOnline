package dao;

import exception.AnnuncioGiaVendutoException;
import factory.ConnectionFactory;
import model.Annuncio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AnnuncioVendutoDAO implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params) throws AnnuncioGiaVendutoException {
        Annuncio ann = (Annuncio) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call contrassegnaAnnuncioVenduto(?,?)}");
            cs.setInt(1, ann.getIdAnnuncio());
            cs.setDate(2, Date.valueOf(LocalDate.now()));
            cs.execute();
        }catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                throw new AnnuncioGiaVendutoException(e.getMessage());
            }else{
                e.printStackTrace();
            }
        }
        return "Annuncio contrassegnato come venduto con successo";
    }
}
