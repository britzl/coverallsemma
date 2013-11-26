package se.springworks.emma;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("report")
public class EmmaReport {

	@XStreamAlias("stats")
	private Stats stats;

	@XStreamAlias("data")
	private Data data;
	
	
	public Data getData() {
		return data;
	}
}
