package dataAccess;

import java.util.List;

import model.Order;

public interface OrderDao {

	Order create(Order order);
	List<Order> readAll();
	boolean update(Order order);
	boolean delete(Order order);	
}
