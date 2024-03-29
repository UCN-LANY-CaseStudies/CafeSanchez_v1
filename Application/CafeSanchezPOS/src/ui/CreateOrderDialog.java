package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import businessLogic.ControllerException;
import businessLogic.OrderHandlingController;
import model.Order;
import model.OrderLine;
import model.Product;

public class CreateOrderDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private boolean accepted;

	private JPanel contentPane;
	private JPanel buttonPane;
	private JScrollPane scrollPane;
	private JTextField txtCustomerName;
	private JTextField txtTotalPrice;
	private JComboBox<Product> cboProducts;
	private JList<OrderLine> lstOrderlines;
	private JLabel lblOrderItems;
	private JButton btnAddProduct;
	private JButton btnOk;
	private JButton btnCancel;

//	public List<OrderLine> getOrderlines() {
//
//		ArrayList<OrderLine> result = new ArrayList<>();
//
//		for (int i = 0; i < lstOrderlines.getModel().getSize(); i++) {
//			OrderLine current = lstOrderlines.getModel().getElementAt(i);
//			result.add(current);
//		}
//
//		return result;
//	}

	private OrderHandlingController orderHandlingController;

	public CreateOrderDialog(OrderHandlingController orderHandlingController) {

		this.orderHandlingController = orderHandlingController;
		initialize();
		updateUI();
	}

	@Override
	public void setVisible(boolean b) {

		boolean visible = b;
		try {

			List<Product> products = orderHandlingController.getProducts();

			cboProducts.setModel(GuiHelpers.mapToComboBoxModel(products));
			lstOrderlines.setModel(new DefaultListModel<OrderLine>());

		} catch (ControllerException e) {

			visible = false;
			e.printStackTrace(); 
		}
		super.setVisible(visible);
	}

	private void addSelectedProductToOrder() {

		Product product = (Product) cboProducts.getSelectedItem();
		boolean itemAdded = false;

		ArrayList<OrderLine> orderlines = new ArrayList<OrderLine>();

		for (int i = 0; i < lstOrderlines.getModel().getSize(); i++) {
			OrderLine current = lstOrderlines.getModel().getElementAt(i);
			if (current.getProduct().getName().equals(product.getName())) {
				itemAdded = true;
				current.setQuantity(current.getQuantity() + 1);
			}
			orderlines.add(current);

		}
		if (!itemAdded) {
			OrderLine ol = new OrderLine(1, product);
			orderlines.add(ol);
		}
		lstOrderlines.setModel(GuiHelpers.mapToListModel(orderlines));

		calculateTotalPrice();
		updateUI();
	}

	private void cancel() {

		this.accepted = false;
		setVisible(false);
	}

	private void ok() {

		disableUI();
		String customerName = txtCustomerName.getText();
		List<OrderLine> orderLines = new ArrayList<OrderLine>();

		for (int i = 0; i < lstOrderlines.getModel().getSize(); i++) {
			orderLines.add(lstOrderlines.getModel().getElementAt(i));
		}

		if (orderLines.isEmpty() || customerName.isEmpty())
			return;

		Order order = new Order(customerName);
		order.setOrderLines(orderLines);

		try {

			orderHandlingController.createOrder(order);

		} catch (ControllerException e) {
			e.printStackTrace();
		}
		this.setVisible(false);
		this.accepted = true;
	}

	public boolean isAccepted() {
		return accepted;
	}

	private void calculateTotalPrice() {
		float result = 0;

		for (int i = 0; i < lstOrderlines.getModel().getSize(); i++) {
			OrderLine current = lstOrderlines.getModel().getElementAt(i);
			result += current.getQuantity() * current.getProduct().getPrice();
		}

		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
		txtTotalPrice.setText(currencyFormatter.format(result));
	}

	private void updateUI() {

		boolean enableOkButton = !txtCustomerName.getText().isBlank() && lstOrderlines.getModel().getSize() > 0;
		btnOk.setEnabled(enableOkButton);
	}
	
	private void disableUI() {
		
		btnOk.setEnabled(false);
		btnCancel.setEnabled(false);		
	}

	private void initialize() {

		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(350, 300);
		getContentPane().setLayout(new BorderLayout());
		setTitle("New Order");

		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// customer name
		JLabel lblCustomerName = new JLabel("Customer Name: ");
		lblCustomerName.setBounds(10, 30, 100, 14);
		contentPane.add(lblCustomerName);

		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(110, 27, 150, 20);
		txtCustomerName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateUI();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateUI();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateUI();
			}
		});
		contentPane.add(txtCustomerName);

		// order items
		lblOrderItems = new JLabel("Items: ");
		lblOrderItems.setBounds(10, 60, 70, 14);
		contentPane.add(lblOrderItems);

		cboProducts = new JComboBox<>();
		cboProducts.setBounds(110, 57, 150, 20);
		cboProducts.setRenderer(new ProductCellRenderer());
		contentPane.add(cboProducts);

		btnAddProduct = new JButton("+");
		btnAddProduct.setBounds(265, 57, 44, 20);
		btnAddProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				addSelectedProductToOrder();
			}

		});
		contentPane.add(btnAddProduct);

		lstOrderlines = new JList<OrderLine>();
		lstOrderlines.setCellRenderer(new OrderLineCellRenderer());
		lstOrderlines.setEnabled(false);
		scrollPane.setBounds(110, 80, 200, 53);
		scrollPane.setViewportView(lstOrderlines);

		// total price
		JLabel lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setBounds(10, 159, 75, 14);
		contentPane.add(lblTotalPrice);

		txtTotalPrice = new JTextField();
		txtTotalPrice.setColumns(10);
		txtTotalPrice.setBounds(110, 157, 150, 20);
		txtTotalPrice.setEditable(false);
		txtTotalPrice.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(txtTotalPrice);

		// button pane
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ok();
			}
		});
		buttonPane.add(btnOk);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cancel();
			}
		});
		buttonPane.add(btnCancel);

	}

	class OrderLineCellRenderer implements ListCellRenderer<OrderLine> {

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends OrderLine> list, OrderLine value, int index,
				boolean isSelected, boolean cellHasFocus) {

			String cellText = value.getQuantity() + " " + value.getProduct().getName();

			return renderer.getListCellRendererComponent(list, cellText, index, isSelected, cellHasFocus);
		}
	}

	class ProductCellRenderer implements ListCellRenderer<Product> {

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index,
				boolean isSelected, boolean cellHasFocus) {

			String renderedText = "";
			if (value != null) {
				renderedText = value.getName();
			}
			return renderer.getListCellRendererComponent(list, renderedText, index, isSelected, cellHasFocus);
		}
	}
}
