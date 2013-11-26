package se.springworks.emma;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <method name="IInAppBillingService$Stub (): void">
              <coverage type="method, %" value="0%   (0/1)"/>
              <coverage type="block, %" value="0%   (0/7)"/>
              <coverage type="line, %" value="0%   (0/3)"/>
            </method>
 * @author bjornritzl
 *
 */
@XStreamAlias("method")
public class ClassMethod {
	
	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit
	private List<Coverage> coverage;
	
	
	public String getName() {
		return name;
	}
	
	public Collection<Coverage> getCoverage() {
		return coverage;
	}
}
