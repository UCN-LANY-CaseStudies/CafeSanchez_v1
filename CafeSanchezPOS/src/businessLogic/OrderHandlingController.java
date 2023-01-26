package businessLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Order;
import model.Product;

public class OrderHandlingController {

	// singleton
	private static OrderHandlingController instance;

	public static OrderHandlingController getInstance() {

		if (instance == null) {

			instance = new OrderHandlingController();
		}
		return instance;
	}

	private OrderHandlingController() { // controller is private to enforce singleton

		// Loading products from file
		try {
			File productsFile = new File("Products.txt");
			Scanner reader = new Scanner(productsFile);
			int id = 0;
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split(";");
				
				products.add(new Product(++id, data[0], Float.parseFloat(data[1])));				
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	// data
	private List<Product> products = new ArrayList<Product>();
	private List<Order> orders = new ArrayList<Order>();

	public boolean setOrderStatusToFinished(Order selectedOrder) {
		
		selectedOrder.setStatus(Order.STATUS_FINISHED);
		return true;
	}

	public List<Order> getActiveOrders() {

		List<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if (order.getStatus() == Order.STATUS_ACTIVE) {
				result.add(order);
			}
		}
		return result;
	}

	public boolean createNewOrder(Order order) {
		order.setStatus(Order.STATUS_ACTIVE);
		orders.add(order);		
		return true;
	}

	public List<Product> getAllProducts() {

		return products;
	}

}
