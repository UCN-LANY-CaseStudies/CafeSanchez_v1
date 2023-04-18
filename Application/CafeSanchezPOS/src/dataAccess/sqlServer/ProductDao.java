package dataAccess.sqlServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.Dao;
import dataAccess.DaoException;
import model.Product;

public class ProductDao extends BaseDao implements Dao<Product> {

	@Override
	public Product create(Product product) throws DaoException {
		// not used
		throw new DaoException("Create product is not available");
	}

	@Override
	public List<Product> read() throws DaoException {

		// call to database that gets all products from the Products table

		String sql = "SELECT * FROM Products ORDER BY Name";
		ArrayList<Product> result = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				String name = rs.getString(1);
				String description = rs.getString(2);
				float price = rs.getFloat(3);

				Product p = new Product(name, description, price);

				result.add(p);
			}

			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DaoException("An error occurred reading products", e);
		}
	}

	@Override
	public Product update(Product product) throws DaoException {
		// not used
		throw new DaoException("Create product is not available");
	}

	@Override
	public boolean delete(Product product) throws DaoException {
		// not used
		throw new DaoException("Create product is not available");
	}
}
