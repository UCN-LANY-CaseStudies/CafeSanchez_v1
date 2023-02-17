package dataAccess;

import java.util.List;

import model.Product;

public interface ProductDao {

	Product create(Product product);
	List<Product> readAll();
	boolean update(Product product);
	boolean delete(Product product);	
}
