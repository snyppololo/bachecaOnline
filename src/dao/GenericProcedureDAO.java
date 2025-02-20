package dao;

import exception.AnnuncioGiaVendutoException;
import exception.DAOException;

public interface GenericProcedureDAO<P> {

    P execute(Object... params) throws DAOException, AnnuncioGiaVendutoException;

}
