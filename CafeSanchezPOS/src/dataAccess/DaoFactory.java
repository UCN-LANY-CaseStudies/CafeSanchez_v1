package dataAccess;

import dataAccess.fake.FakeOrderDao;
import dataAccess.fake.FakeProductDao;
import dataAccess.sql.SqlOrderDao;
import dataAccess.sql.SqlProductDao;

public class DaoFactory {

	private Type factoryType;

	public DaoFactory(Type factoryType) {

		this.factoryType = factoryType;
	}

	public OrderDao CreateOrderDao() {

		switch (this.factoryType) {
		case Memory:
			return new FakeOrderDao();
		case Sql:
			return new SqlOrderDao();
		default:
			throw new DataAccessException("Unknown dao type: [" + this.factoryType + "]");
		}
	}

	public ProductDao CreateProductDao() {

		switch (this.factoryType) {
		case Memory:
			return new FakeProductDao();
		case Sql:
			return new SqlProductDao();
		default:
			throw new DataAccessException("Unknown dao type: [" + this.factoryType + "]");
		}
	}
	
	public enum Type{
		Memory, 
		Sql
	}
}
