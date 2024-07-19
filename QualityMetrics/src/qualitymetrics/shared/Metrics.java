package qualitymetrics.shared;

import java.io.Serializable;

public class Metrics implements Serializable {
	
	private int Metric1;
	private int Metric2;
	private int Metric3;
	private int Metric4;
	private int Metric5;
	
	public Metrics() {
		
	}
	
	public Metrics(int metric1, 
			int metric2, 
			int metric3, 
			int metric4, 
			int metric5) {
		Metric1 = metric1;
		Metric2 = metric2;
		Metric3 = metric3;
		Metric4 = metric4;
		Metric5 = metric5;
	}

	public int getMetric1() {
		return Metric1;
	}

	public void setMetric1(int metric1) {
		Metric1 = metric1;
	}

	public int getMetric2() {
		return Metric2;
	}

	public void setMetric2(int metric2) {
		Metric2 = metric2;
	}

	public int getMetric3() {
		return Metric3;
	}

	public void setMetric3(int metric3) {
		Metric3 = metric3;
	}

	public int getMetric4() {
		return Metric4;
	}

	public void setMetric4(int metric4) {
		Metric4 = metric4;
	}

	public int getMetric5() {
		return Metric5;
	}

	public void setMetric5(int metric5) {
		Metric5 = metric5;
	}
}
