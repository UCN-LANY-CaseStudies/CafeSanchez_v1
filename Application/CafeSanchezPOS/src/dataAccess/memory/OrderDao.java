package dataAccess.memory;

import java.util.ArrayList;
import java.util.List;

import dataAccess.Dao;
import model.Order;

public class OrderDao implements Dao<Order> {
	
	private List<Order> orders = new ArrayList<>();


	@Override
	public Order create(Order order) {

		orders.add(order);	
		return order;
	}

	@Override
	public List<Order> read() {

		return orders;
	}

	@Override
	public Order update(Order order) {

		return order;
	}

	@Override
	public boolean delete(Order order) {

		order.setStatus(Order.STATUS_FINISHED);
		return true;
	}

}
