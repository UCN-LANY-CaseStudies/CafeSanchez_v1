package dataAccess.memory;

import java.util.ArrayList;
import java.util.List;

import dataAccess.OrderDao;
import model.Order;

public class MemoryOrderDao implements OrderDao {
	
	private List<Order> orders = new ArrayList<>();


	@Override
	public Order create(Order order) {

		orders.add(order);	
		return order;
	}

	@Override
	public List<Order> readAll() {

		return orders;
	}

	@Override
	public boolean update(Order order) {

		return true;
	}

	@Override
	public boolean delete(Order order) {

		order.setStatus(Order.STATUS_FINISHED);
		return true;
	}

}
