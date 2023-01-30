package businessLogic;

import java.util.List;
import java.util.stream.Collectors;

import database.OrderDao;
import database.ProductDao;
import model.Order;
import model.Product;

public class OrderHandlingController {
	
	private OrderDao orderDao;
	private ProductDao productDao;
	
	public OrderHandlingController(OrderDao orderDao, ProductDao productDao) { // constructor is private to enforce singleton
		
		this.productDao = productDao;
		this.orderDao = orderDao;
	}

	public boolean createOrder(Order order) {
		
		Order createdOrder = orderDao.createOrder(order);
		
		return createdOrder.getStatus().equals(Order.STATUS_ACTIVE);
	}

	public boolean finishOrder(Order selectedOrder) {
		
		selectedOrder.setStatus(Order.STATUS_FINISHED);
		Order updatedOrder = orderDao.updateOrder(selectedOrder);
		
		return updatedOrder.getStatus().equals(Order.STATUS_FINISHED);
	}

	public List<Product> getProducts() {

		return  productDao.getAll();
	}

	public List<Order> getActiveOrders() {
		
		List<Order> orders = orderDao.getAll();
		
		if(orders == null)
			return null; 
		
		return orders.stream().filter(O -> O.getStatus().equals(Order.STATUS_ACTIVE)).collect(Collectors.toList());		
	}
	

}
