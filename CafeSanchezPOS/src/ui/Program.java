package ui;

import businessLogic.OrderHandlingController;
import database.Context;
import database.OrderDao;
import database.ProductDao;

public class Program {

	public static void main(String[] args) {
		
		Context context = new Context();

		OrderDao orderDao = new OrderDao(context);
		ProductDao productDao = new ProductDao(context);
		
		OrderHandlingController orderCtrl = new OrderHandlingController(orderDao, productDao);
		
		MainWindow main = new MainWindow(orderCtrl); 
		main.setVisible(true);
	}
}
