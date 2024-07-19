package qualitymetrics.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import qualitymetrics.shared.Metrics;

public interface DBAsync {
	void insertMetrics(String app_nam, 
			String app_version, 
			String cycle, 
			int metric1, 
			int metric2, 
			int metric3, 
			int metric4, 
			int metric5, AsyncCallback<String> callback) throws Exception;
	void getMetrics(String app_nam, 
			String app_version, 
			AsyncCallback<List<Metrics>> callback) throws Exception;
}
