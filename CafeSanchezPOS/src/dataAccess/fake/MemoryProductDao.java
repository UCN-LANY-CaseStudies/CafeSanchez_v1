package dataAccess.fake;

import java.util.ArrayList;
import java.util.List;

import dataAccess.ProductDao;
import model.Product;

public class MemoryProductDao implements ProductDao{

	List<Product> products = new ArrayList<>();
	
	@Override
	public Product create(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Product product) {
		// TODO Auto-generated method stub
		return false;
	}
}
