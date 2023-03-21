package dataAccess.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.ProductDao;
import model.Product;

public class SqlProductDao extends SqlBaseDao implements ProductDao {
	
	@Override
	public List<Product> readAll(){
		
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
			
		} catch (SQLException e) {

			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public Product create(Product product) {
		// not used
		return product;
	}

	@Override
	public boolean update(Product product) {
		// not used
		return false;
	}

	@Override
	public boolean delete(Product product) {
		// not used
		return false;
	}
}
