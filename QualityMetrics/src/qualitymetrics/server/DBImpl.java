package qualitymetrics.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import qualitymetrics.client.DB;
import qualitymetrics.shared.Metrics;

@SuppressWarnings("serial")
public class DBImpl extends RemoteServiceServlet implements DB {
	
	private static final String URL = "jdbc:sqlserver://DESKTOP-AINF3M7\\SQLEXPRESS:1433;databaseName=quality_metrics;encrypt=false;";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456789";
	
	public static Connection getConnetion() throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public String insertMetrics(String app_nam, 
			String app_version, 
			String cycle, 
			int metric1, 
			int metric2, 
			int metric3, 
			int metric4, 
			int metric5) throws Exception {
		
		try {
			String sql = "INSERT INTO metrics (app_nam, app_version, cycle, metric1, metric2, metric3, metric4, metric5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = getConnetion().prepareStatement(sql);
			statement.setString(1, app_nam);
			statement.setString(2, app_version);
			statement.setString(3, cycle);
			statement.setInt(4, metric1);
			statement.setInt(5, metric2);
			statement.setInt(6, metric3);
			statement.setInt(7, metric4);
			statement.setInt(8, metric5);
			statement.executeUpdate();
			return "Insert successful";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Insert failed: " + e.getMessage();
		} finally {
			if (getConnetion() != null) {
				getConnetion().close();
			}
		}
	}
	
	public List<Metrics> getMetrics(String app_nam, 
			String app_version) throws Exception {
		List<Metrics> listMetrics = new ArrayList<Metrics>();
		
		ResultSet Result = null;
		try {
			String sql = "SELECT " +
						"AVG(metric1) AS metric1, " +
						"AVG(metric2) AS metric2, " +
						"AVG(metric3) AS metric3, " +
						"AVG(metric4) AS metric4, " +
						"AVG(metric5) AS metric5 " +
						"FROM metrics " +
						"WHERE app_nam = ? AND app_version = ?";
			PreparedStatement statement = getConnetion().prepareStatement(sql);
			statement.setString(1, app_nam);
			statement.setString(2, app_version);
			Result = statement.executeQuery();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (getConnetion() != null) {
				getConnetion().close();
			}
		}
		
		if (Result != null) {
			while(Result.next()) {
				Metrics objMetrics;
				objMetrics = new Metrics(Result.getInt("metric1"), 
						Result.getInt("metric2"), 
						Result.getInt("metric3"), 
						Result.getInt("metric4"), 
						Result.getInt("metric5"));
				listMetrics.add(objMetrics);
			}
		}

		return listMetrics;
	}
}
 