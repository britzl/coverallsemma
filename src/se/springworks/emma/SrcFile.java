package se.springworks.emma;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *  <srcfile name="IInAppBillingService.java">
          <coverage type="class, %" value="0%   (0/2)"/>
          <coverage type="method, %" value="0%   (0/12)"/>
          <coverage type="block, %" value="0%   (0/499)"/>
          <coverage type="line, %" value="0%   (0/152)"/>

          <class name="IInAppBillingService$Stub">
            <coverage type="class, %" value="0%   (0/1)"/>
            <coverage type="method, %" value="0%   (0/4)"/>
            <coverage type="block, %" value="0%   (0/224)"/>
            <coverage type="line, %" value="0%   (0/69)"/>
	</srcfile>

 * @author bjornritzl
 *
 */
@XStreamAlias("srcfile")
public class SrcFile {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit
	private List<Coverage> coverage;

	@XStreamImplicit(itemFieldName="class")
	private List<JavaClass> classes;
	
	
	public String getName() {
		return name;
	}
	
	public Collection<Coverage> getCoverage() {
		return coverage;
	}
	
	public Collection<JavaClass> getClasses() {
		return classes;
	}
}
