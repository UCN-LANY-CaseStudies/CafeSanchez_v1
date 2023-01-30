package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.*;

import businessLogic.OrderHandlingController;
import model.Order;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList<Order> listActiveOrders;
	private JButton btnNewOrder;
	private JButton btnFinishOrder;

	private OrderHandlingController orderCtrl;

	public MainWindow(OrderHandlingController orderCtrl) {
		super("Orders");
		this.orderCtrl = orderCtrl;

		initialize();
		
		reloadOrders();
	}

	private void finishSelectedOrder() {

		Order selectedOrder = listActiveOrders.getSelectedValue();
		if (selectedOrder != null && orderCtrl.finishOrder(selectedOrder)) {

			reloadOrders();
		}
	}

	private void openNewOrderDialog() {

		CreateOrderDialog dialog = new CreateOrderDialog(this.orderCtrl);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

		if (dialog.isAccepted()) {
			reloadOrders();
		}
	}

	private void reloadOrders() {
		List<Order> activeOrders = orderCtrl.getActiveOrders();
		if (activeOrders != null)
			listActiveOrders.setModel(GuiHelpers.mapToListModel(activeOrders));
		listActiveOrders.updateUI();
	}

	// Renderer for active orders list
	public class OrderCellRenderer implements ListCellRenderer<Order> {

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Order> list, Order value, int index,
				boolean isSelected, boolean cellHasFocus) {

			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
			
			String cellText = value.getCustomerName() + ": "+ currencyFormatter.format(value.getTotalPrice());

			return renderer.getListCellRendererComponent(list, cellText, index, isSelected, cellHasFocus);
		}

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

		contentPane.add(panel, BorderLayout.SOUTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		listActiveOrders = new JList<>();
		listActiveOrders.setCellRenderer(new OrderCellRenderer());
		scrollPane.setViewportView(listActiveOrders);

		btnNewOrder = new JButton("New Order");
		btnNewOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openNewOrderDialog();
			}

		});
		panel.add(btnNewOrder);

		btnFinishOrder = new JButton("Finish Order");
		btnFinishOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				finishSelectedOrder();
			}
		});
		panel.add(btnFinishOrder);

	}
}
