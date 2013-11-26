package se.springworks.emma;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *     <all name="all classes">
      <coverage type="class, %" value="6%   (10/181)"/>
      <coverage type="method, %" value="5%   (61/1145)"/>
      <coverage type="block, %" value="4%   (801/20401)"/>
      <coverage type="line, %" value="4%   (190.5/4349)"/>

      <package name="android">

 * @author bjornritzl
 *
 */
@XStreamAlias("all")
public class All {

	@XStreamAlias("name")
	private String name;
	
	@XStreamImplicit(itemFieldName="coverage")
	private List<Coverage> coverage;
	
	@XStreamImplicit(itemFieldName="package")
	private List<JavaPackage> packages;
	
	public String getName() {
		return name;
	}
	
	public Collection<Coverage> getCoverage() {
		return coverage;
	}
	
	public Collection<JavaPackage> getPackages() {
		return packages;
	}
}
