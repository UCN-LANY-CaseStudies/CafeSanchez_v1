package businessLogic;

import java.util.List;
import java.util.stream.Collectors;

import dataAccess.Dao;
import dataAccess.DaoException;
import model.Order;
import model.Product;

public class OrderHandlingController {

	private Dao<Order> orderDao;
	private Dao<Product> productDao;

	public OrderHandlingController(Dao<Order> orderDao, Dao<Product> productDao) {

		this.productDao = productDao;
		this.orderDao = orderDao;
	}

	public boolean createOrder(Order order) throws ControllerException {

		try {
			
			Order createdOrder = orderDao.create(order);

			return createdOrder.getStatus().equals(Order.STATUS_NEW);
			
		} catch (DaoException e) {

			throw new ControllerException("An error occurred creating order", e);
		}
	}

	public List<Product> getProducts() throws ControllerException {

		try {

			return productDao.read();

		} catch (DaoException e) {

			throw new ControllerException("An error occurred reading products", e);
		}
	}

	public boolean changeOrderState(Order order) throws ControllerException {

		try {
			
			Thread.sleep(3000);
			
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
			if (order.getStatus().equals(Order.STATUS_FINISHED)) {
				orderDao.delete(order);
			} else {
				orderDao.update(order);
			}
			return true;
		} catch (Exception e) {

			throw new ControllerException("An error occurred changing state on an order", e);
		}
	}

	public List<Order> getUnfinishedOrders() throws ControllerException {

		try {
			List<Order> orders = orderDao.read();

			if (orders == null)
				return null;

			return orders.stream().filter(O -> !O.getStatus().equals(Order.STATUS_FINISHED))
					.collect(Collectors.toList());

		} catch (DaoException e) {

			throw new ControllerException("An error occurred reading active orders", e);
		}
	}

}
