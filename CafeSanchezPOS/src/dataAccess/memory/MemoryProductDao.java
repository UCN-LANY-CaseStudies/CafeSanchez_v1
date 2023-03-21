package dataAccess.memory;

import java.util.ArrayList;
import java.util.List;

import dataAccess.ProductDao;
import model.Product;

public class MemoryProductDao implements ProductDao{

	List<Product> products = new ArrayList<>();
	
	@Override
	public Product create(Product product) {
		// Not needed
		return null;
	}

	@Override
	public List<Product> readAll() {
		// returns all products
		return products;
	}

	@Override
	public boolean update(Product product) {
		// Not needed
		return false;
	}

	@Override
	public boolean delete(Product product) {
		// Not needed
		return false;
	}
}
