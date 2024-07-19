package qualitymetrics.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import qualitymetrics.shared.Metrics;

@RemoteServiceRelativePath("DB")
public interface DB extends RemoteService {
	String insertMetrics(String app_nam, 
			String app_version, 
			String cycle, 
			int metric1, 
			int metric2, 
			int metric3, 
			int metric4, 
			int metric5) throws Exception;
	List<Metrics> getMetrics(String app_nam, 
			String app_version) throws Exception;
}