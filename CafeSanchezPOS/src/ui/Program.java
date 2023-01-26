package ui;

import businessLogic.OrderHandlingController;

public class Program {

	public static void main(String[] args) {

		MainWindow main = new MainWindow(OrderHandlingController.getInstance()); 
		main.setVisible(true);
	}
}
