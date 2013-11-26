package se.springworks.emma;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * stats>
    <packages value="45"/>
    <classes value="181"/>
    <methods value="1145"/>
    <srcfiles value="123"/>
    <srclines value="4349"/>
  </stats>
 * @author bjornritzl
 *
 */
@XStreamAlias("stats")
public class Stats {
	
	public class Stat {
		private int value;
	}
	
	private Stat packages;
	private Stat classes;
	private Stat methods;
	private Stat srcfiles;
	private Stat srclines;
}
