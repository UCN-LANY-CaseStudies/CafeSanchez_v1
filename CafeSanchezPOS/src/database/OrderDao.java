package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.Orderline;
import model.Product;

public class OrderDao {

	private Context context;

	public OrderDao(Context context) {

		this.context = context;
	}

	public List<Order> getAll() {

		// TODO: Implement call to database that gets all orders from the Orders table
		List<Order> result = new ArrayList<>();
		try {

			Connection conn = context.getConnection();

			String sql = "SELECT CustomerName, Status, Date FROM Orders ";

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				// mapping order
				Order order = new Order(rs.getString(1));
				order.setStatus(rs.getString(2));
				order.setDate(rs.getDate(3));

				addOrderlines(conn, order);

				result.add(order);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}

	public Order getSingle(String customerName) {

		// Implement call to database that gets a single order from the Orders table

		try {

			Connection conn = context.getConnection();

			String sqlSelectOrders = "SELECT CustomerName, Status, Date FROM Orders WHERE CustomerName = ? ";

			PreparedStatement statement = conn.prepareStatement(sqlSelectOrders);
			statement.setString(1, customerName);

			ResultSet rsOrders = statement.executeQuery();

			if (rsOrders.next()) {
				// mapping order
				Order order = new Order(rsOrders.getString(1));
				order.setStatus(rsOrders.getString(2));
				order.setDate(rsOrders.getDate(3));

				addOrderlines(conn, order);

				return order;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	public Order createOrder(Order order) {

		// Implement call to database that creates an order in the Orders table and
		// orderlines in the Orderlines table

		try {

			Connection conn = context.getConnection();
			conn.setAutoCommit(false);

			String sqlInsertOrder = "INSERT INTO Orders (Date, Status, CustomerName) VALUES (?, ?, ?)";
			PreparedStatement statementInsertOrder = conn.prepareStatement(sqlInsertOrder);
			statementInsertOrder.setDate(1, new Date(System.currentTimeMillis()));
			statementInsertOrder.setString(2, Order.STATUS_ACTIVE);
			statementInsertOrder.setString(3, order.getCustomerName());

			int rowsInserted = statementInsertOrder.executeUpdate();

			if (rowsInserted == 1) {
				order.setStatus(Order.STATUS_ACTIVE);

				String sqlInsertOrderline = "INSERT INTO Orderlines (OrderCustomerName, ProductName, Quantity) VALUES (?, ?, ?)";

				for (Orderline ol : order.getOrderlines()) {
					PreparedStatement statementInsertOrderline = conn.prepareStatement(sqlInsertOrderline);
					statementInsertOrderline.setString(1, order.getCustomerName());
					statementInsertOrderline.setString(2, ol.getProduct().getName());
					statementInsertOrderline.setInt(3, ol.getQuantity());

					rowsInserted = statementInsertOrderline.executeUpdate();

					if (rowsInserted == 0) {
						conn.rollback();
						return null;
					}
				}

				conn.commit();

				return order;
			}

			conn.rollback();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	public Order updateOrder(Order order) {

		// TODO: Implement call to database that updates an order in the Orders table

		try {

			Connection conn = context.getConnection();
			conn.setAutoCommit(false);

			String sqlUpdateOrder = "UPDATE Orders SET Status = ? WHERE CustomerName = ? ";
			// update order
			PreparedStatement statement = conn.prepareStatement(sqlUpdateOrder);
			statement.setString(1, order.getStatus());
			statement.setString(2, order.getCustomerName());
			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated == 1) {
				// clear orderlines
				String sqlDeleteOrderlines = "DELETE FROM Orderlines WHERE OrderCustomerName = ?";
				PreparedStatement statementDeleteOrderlines = conn.prepareStatement(sqlDeleteOrderlines);
				statementDeleteOrderlines.setString(1, order.getCustomerName());
				statementDeleteOrderlines.execute();

				// add orderlines
				String sqlInsertOrderlines = "INSERT INTO Orderlines (OrderCustomerName, ProductName, Quantity) VALUES (?, ?, ?)";

				for (Orderline ol : order.getOrderlines()) {
					PreparedStatement statementInsertOrderline = conn.prepareStatement(sqlInsertOrderlines);
					statementInsertOrderline.setString(1, order.getCustomerName());
					statementInsertOrderline.setString(2, ol.getProduct().getName());
					statementInsertOrderline.setInt(3, ol.getQuantity());
					statementInsertOrderline.execute();
				}

				conn.commit();

				return order;

			} else {

				conn.rollback();
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	private void addOrderlines(Connection conn, Order order) throws SQLException {

		String sqlSelectOrderlines = "SELECT Products.Name, Products.Description, Products.Price, Quantity "
				+ "FROM Orderlines " + "JOIN Products ON Products.Name = Orderlines.ProductName "
				+ "WHERE OrderCustomerName = ? ";

		// get orderlines
		PreparedStatement statementSelectOrderlines = conn.prepareStatement(sqlSelectOrderlines);

		statementSelectOrderlines.setString(1, order.getCustomerName());

		ResultSet rsOrderlines = statementSelectOrderlines.executeQuery();

		while (rsOrderlines.next()) {
			// mapping product
			Product p = new Product(rsOrderlines.getString(1), rsOrderlines.getString(2), rsOrderlines.getInt(3));
			// mapping orderlines
			int quantity = rsOrderlines.getInt(4);

			Orderline ol = new Orderline(quantity, p);
			order.addOrderline(ol);
		}
	}
}
