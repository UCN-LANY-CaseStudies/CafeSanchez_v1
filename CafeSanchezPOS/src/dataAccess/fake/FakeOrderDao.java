package dataAccess.fake;

import java.util.ArrayList;
import java.util.List;

import dataAccess.OrderDao;
import model.Order;

public class FakeOrderDao implements OrderDao {
	
	List<Order> orders = new ArrayList<>();

	@Override
	public Order create(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

}
