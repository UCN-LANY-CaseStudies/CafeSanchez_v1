package ui;

import java.time.DayOfWeek;

import businessLogic.OrderHandlingController;
import dataAccess.DaoFactory;
import dataAccess.OrderDao;
import dataAccess.ProductDao;

public class Program {

	public static void main(String[] args) {
		
		DaoFactory daoFactory = new DaoFactory(DaoFactory.Type.Sql);

		OrderDao orderDao = daoFactory.CreateOrderDao();
		ProductDao productDao = daoFactory.CreateProductDao();

		OrderHandlingController orderCtrl = new OrderHandlingController(orderDao, productDao);

		MainWindow main = new MainWindow(orderCtrl);
		main.setVisible(true);
	}
}
