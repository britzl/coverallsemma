package se.springworks.emma;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("data")
public class Data {

	@XStreamAlias("all")
	private All all;
	
	
	public All getAll() {
		return all;
	}
}
