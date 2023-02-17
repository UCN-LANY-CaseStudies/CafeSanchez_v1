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
		
		return createdOrder.getStatus().equals(Order.STATUS_NEW);
	}

	public List<Product> getProducts() {

		return  productDao.getAll();
	}
	
	public boolean changeOrderState(Order order) {

		switch (order.getStatus()) {
			case Order.STATUS_NEW:
				order.setStatus(Order.STATUS_ACTIVE);
				break;
			case Order.STATUS_ACTIVE:
				order.setStatus(Order.STATUS_READY);
				break;
			case Order.STATUS_READY:
				order.setStatus(Order.STATUS_FINISHED);
				break;
			default:
				return false;
		}
		if(order.getStatus().equals(Order.STATUS_FINISHED)) {
			orderDao.deleteOrder(order);
		}	
		else {
			orderDao.updateOrder(order);			
		}

		return true;
	}

	public List<Order> getUnfinishedOrders() {
		
		List<Order> orders = orderDao.getAll();
		
		if(orders == null)
			return null; 
		
		return orders.stream().filter(O -> !O.getStatus().equals(Order.STATUS_FINISHED)).collect(Collectors.toList());		
	}
	

}
