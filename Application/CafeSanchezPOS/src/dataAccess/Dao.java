package dataAccess;

import java.util.List;

import model.Order;

public interface Dao<TEntity> {

	TEntity create(TEntity order) throws DaoException;
	List<TEntity> read() throws DaoException;
	TEntity update(TEntity order) throws DaoException;
	boolean delete(TEntity order) throws DaoException;	
}
