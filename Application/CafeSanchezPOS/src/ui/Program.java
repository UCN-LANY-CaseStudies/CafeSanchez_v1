package ui;

import businessLogic.OrderHandlingController;
import dataAccess.Dao;
import dataAccess.DaoFactory;
import model.Order;
import model.Product;

public class Program {

	public static void main(String[] args) {
		
		DaoFactory daoFactory = new DaoFactory(DaoFactory.For.SqlServer);

		Dao<Order> orderDao = daoFactory.CreateOrderDao();
		Dao<Product> productDao = daoFactory.CreateProductDao();

		OrderHandlingController orderCtrl = new OrderHandlingController(orderDao, productDao);

		MainWindow main = new MainWindow(orderCtrl);
		main.setVisible(true);
	}
}
