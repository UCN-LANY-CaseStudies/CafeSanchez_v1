package dataAccess.sqlServer;

import java.sql.Connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public abstract class BaseDao {

	protected Connection getConnection() throws SQLServerException {

		// Instantiate and returns a Connection object that encapsulates a connection to
		// the database

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser("sa");
		ds.setPassword("P@$$w0rd");
		ds.setServerName("10.236.100.115");
		ds.setDatabaseName("CafeSanchez_v1");
		ds.setEncrypt("False");

		return ds.getConnection();
	}
}
