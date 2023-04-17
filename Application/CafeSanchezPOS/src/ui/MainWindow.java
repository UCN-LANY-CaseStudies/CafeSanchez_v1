package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.OrderHandlingController;
import model.Order;
import model.OrderLine;
import model.Product;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList<Order> listActiveOrders;
	private JButton btnNewOrder;
	private JButton btnProcessOrder;
	private JButton btnOrderReady;
	private JButton btnFinishOrder;

	private OrderHandlingController orderCtrl;

	public MainWindow(OrderHandlingController orderCtrl) {
		super("Orders");
		this.orderCtrl = orderCtrl;

		initialize();

		reloadOrders();
	}

	private void changeOrderState() {

		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				Order selectedOrder = listActiveOrders.getSelectedValue();
				if (selectedOrder != null && orderCtrl.changeOrderState(selectedOrder)) {

					reloadOrders();
				}
			}
		});
	}

	private void openNewOrderDialog() {

		List<Product> products = orderCtrl.getProducts();

		CreateOrderDialog dialog = new CreateOrderDialog(products);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

		if (dialog.isAccepted()) {

			Executors.newSingleThreadExecutor().execute(new Runnable() {

				@Override
				public void run() {

					Order order = new Order(dialog.getCustomerName());
					order.setOrderLines(dialog.getOrderlines());
					orderCtrl.createOrder(order);

					reloadOrders();
				}
			});
		}
	}

	private void reloadOrders() {

		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				List<Order> activeOrders = orderCtrl.getUnfinishedOrders();
				if (activeOrders != null)
					listActiveOrders.setModel(GuiHelpers.mapToListModel(activeOrders));
				// listActiveOrders.updateUI();
			}
		});
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);

		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		FlowLayout layout = (FlowLayout) panel.getLayout();
		layout.setAlignment(FlowLayout.RIGHT);

		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(panel, BorderLayout.SOUTH);

		// orderlist
		listActiveOrders = new JList<>();
		listActiveOrders.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				Order selectedOrder = listActiveOrders.getSelectedValue();
				btnProcessOrder.setEnabled(selectedOrder != null && selectedOrder.getStatus().equals(Order.STATUS_NEW));
				btnOrderReady
						.setEnabled(selectedOrder != null && selectedOrder.getStatus().equals(Order.STATUS_ACTIVE));
				btnFinishOrder
						.setEnabled(selectedOrder != null && selectedOrder.getStatus().equals(Order.STATUS_READY));
			}
		});
		scrollPane.setViewportView(listActiveOrders);

		// new order button
		btnNewOrder = new JButton("New");
		btnNewOrder.addActionListener(e -> openNewOrderDialog());
		panel.add(btnNewOrder);

		// process order button
		btnProcessOrder = new JButton("Process");
		btnProcessOrder.setEnabled(false);
		btnProcessOrder.addActionListener(e -> changeOrderState());
		panel.add(btnProcessOrder);

		// order ready
		btnOrderReady = new JButton("Ready");
		btnOrderReady.setEnabled(false);
		btnOrderReady.addActionListener(e -> changeOrderState());
		panel.add(btnOrderReady);

		// finish order button
		btnFinishOrder = new JButton("Finish");
		btnFinishOrder.setEnabled(false);
		btnFinishOrder.addActionListener(e -> changeOrderState());
		panel.add(btnFinishOrder);

	}
}
