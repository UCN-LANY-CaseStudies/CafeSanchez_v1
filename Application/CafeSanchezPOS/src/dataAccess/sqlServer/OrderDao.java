package dataAccess.sqlServer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.Dao;
import dataAccess.DaoException;
import model.Order;
import model.OrderLine;
import model.Product;

public class OrderDao extends BaseDao implements Dao<Order> {

	@Override
	public List<Order> read() throws DaoException {

		// Implement call to database that gets all orders from the Orders table
		List<Order> result = new ArrayList<>();
		try {

			Connection conn = getConnection();

			String selectOrdersSql = "SELECT CustomerName, Status, Date FROM Orders ";
			String selectOrderLinesSql = "SELECT Products.Name, Products.Description, Products.Price, Quantity "
					+ "FROM Orderlines JOIN Products ON Products.Name = Orderlines.ProductName "
					+ "WHERE OrderCustomerName = ? ";

			PreparedStatement selectOrdersStatement = conn.prepareStatement(selectOrdersSql);
			ResultSet rs = selectOrdersStatement.executeQuery();

			while (rs.next()) {
				// mapping order
				Order order = new Order(rs.getString(1));
				order.setStatus(rs.getString(2));
				order.setDate(rs.getDate(3));

				// get order lines
				PreparedStatement statementSelectOrderlines = conn.prepareStatement(selectOrderLinesSql);
				statementSelectOrderlines.setString(1, order.getCustomerName());

				ResultSet rsOrderLines = statementSelectOrderlines.executeQuery();

				while (rsOrderLines.next()) {
					// mapping product
					Product p = new Product(rsOrderLines.getString(1), rsOrderLines.getString(2),
							rsOrderLines.getInt(3));
					// mapping order lines
					int quantity = rsOrderLines.getInt(4);

					OrderLine ol = new OrderLine(quantity, p);
					order.getOrderLines().add(ol);
				}

				result.add(order);
			}
			
			return result;

		} catch (SQLException e) {

			//e.printStackTrace();
			throw new DaoException("An error occurred reading orders", e);
		}
	}

	@Override
	public Order create(Order order) throws DaoException {

		// Implement call to database that creates an order in the Orders table and
		// orderlines in the Orderlines table

		try {

			Connection conn = getConnection();
			conn.setAutoCommit(false);

			try {

				String sqlInsertOrder = "INSERT INTO Orders (Date, Status, CustomerName) VALUES (?, ?, ?)";
				PreparedStatement statementInsertOrder = conn.prepareStatement(sqlInsertOrder);
				statementInsertOrder.setDate(1, new Date(System.currentTimeMillis()));
				statementInsertOrder.setString(2, Order.STATUS_NEW);
				statementInsertOrder.setString(3, order.getCustomerName());

				int rowsInserted = statementInsertOrder.executeUpdate();

				if (rowsInserted == 1) {

					String sqlInsertOrderline = "INSERT INTO Orderlines (OrderCustomerName, ProductName, Quantity) VALUES (?, ?, ?)";

					for (OrderLine ol : order.getOrderLines()) {
						PreparedStatement statementInsertOrderline = conn.prepareStatement(sqlInsertOrderline);
						statementInsertOrderline.setString(1, order.getCustomerName());
						statementInsertOrderline.setString(2, ol.getProduct().getName());
						statementInsertOrderline.setInt(3, ol.getQuantity());

						rowsInserted = statementInsertOrderline.executeUpdate();
					}
					conn.commit();
				}

				return order;

			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}

		} catch (Exception e) {

			//e.printStackTrace();
			throw new DaoException("An error occurred creating an order", e);
		}
	}

	@Override
	public Order update(Order order) throws DaoException {

		// Implement call to database that updates an order in the Orders table

		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);

			try {

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

					for (OrderLine ol : order.getOrderLines()) {
						PreparedStatement statementInsertOrderline = conn.prepareStatement(sqlInsertOrderlines);
						statementInsertOrderline.setString(1, order.getCustomerName());
						statementInsertOrderline.setString(2, ol.getProduct().getName());
						statementInsertOrderline.setInt(3, ol.getQuantity());
						statementInsertOrderline.execute();
					}
					conn.commit();
				}
				return order;

			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}

		} catch (Exception e) {

			//e.printStackTrace();
			throw new DaoException("An error occurred updating an order", e);
		}
	}

	@Override
	public boolean delete(Order order) throws DaoException {

		String deleteOrderlineSql = "DELETE FROM Orderlines WHERE OrderCustomerName = ?";
		String deleteOrderSql = "DELETE FROM Orders WHERE CustomerName = ?";

		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);

			try {

				// delete orderlines
				PreparedStatement statementDeleteOrderlines = conn.prepareStatement(deleteOrderlineSql);
				statementDeleteOrderlines.setString(1, order.getCustomerName());

				statementDeleteOrderlines.execute();

				PreparedStatement statementDeleteOrder = conn.prepareStatement(deleteOrderSql);
				statementDeleteOrder.setString(1, order.getCustomerName());

				statementDeleteOrder.execute();

				conn.commit();

				return true;

			} catch (SQLException e) {

				conn.rollback();
				throw e;
			}

		} catch (Exception e) {

			//e.printStackTrace();
			throw new DaoException("An error occurred deleting an order", e);
		}
	}
}
