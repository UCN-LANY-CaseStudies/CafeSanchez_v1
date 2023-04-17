package dataAccess;

import dataAccess.memory.MemoryOrderDao;
import dataAccess.memory.MemoryProductDao;
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
			return new MemoryOrderDao();
		case Sql:
			return new SqlOrderDao();
		default:
			throw new DaoException("Unknown dao type: [" + this.factoryType + "]");
		}
	}

	public ProductDao CreateProductDao() {

		switch (this.factoryType) {
		case Memory:
			return new MemoryProductDao();
		case Sql:
			return new SqlProductDao();
		default:
			throw new DaoException("Unknown dao type: [" + this.factoryType + "]");
		}
	}
	
	public enum Type{
		Memory, 
		Sql
	}
}
