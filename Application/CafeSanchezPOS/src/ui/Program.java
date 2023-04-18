package ui;

import businessLogic.OrderHandlingController;
import dataAccess.Dao;
import dataAccess.DaoFactory;
import model.Order;
import model.Product;

public class Program {

	public static void main(String[] args) {

		try {
			DaoFactory daoFactory = new DaoFactory(DaoFactory.For.SqlServer);

			Dao<Order> orderDao = daoFactory.CreateOrderDao();
			Dao<Product> productDao = daoFactory.CreateProductDao();

			OrderHandlingController orderCtrl = new OrderHandlingController(orderDao, productDao);

			MainWindow main = new MainWindow(orderCtrl);
			main.setVisible(true);
		
		} catch (Exception e) {
			
			// TODO: Log exception
			// TODO: Inform user
		}
	}
}
