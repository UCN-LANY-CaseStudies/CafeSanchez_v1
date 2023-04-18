package dataAccess;

import model.Order;
import model.Product;

public class DaoFactory {

	private For factoryType;

	public DaoFactory(For factoryType) {

		this.factoryType = factoryType;
	}

	public Dao<Order> CreateOrderDao() throws DaoException {

		switch (this.factoryType) {
		case Memory:
			return new dataAccess.memory.OrderDao();
		case SqlServer:
			return new dataAccess.sqlServer.OrderDao();
		default:
			throw new DaoException("Unknown dao type: [" + this.factoryType + "]");
		}
	}

	public Dao<Product> CreateProductDao() throws DaoException {

		switch (this.factoryType) {
		case Memory:
			return new dataAccess.memory.ProductDao();
		case SqlServer:
			return new dataAccess.sqlServer.ProductDao();
		default:
			throw new DaoException("Unknown dao type: [" + this.factoryType + "]");
		}
	}
	
	public enum For{
		Memory, 
		SqlServer,
	}
}
