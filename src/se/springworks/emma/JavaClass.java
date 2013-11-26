package se.springworks.emma;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <class name="IInAppBillingService$Stub">
            <coverage type="class, %" value="0%   (0/1)"/>
            <coverage type="method, %" value="0%   (0/4)"/>
            <coverage type="block, %" value="0%   (0/224)"/>
            <coverage type="line, %" value="0%   (0/69)"/>

            <method name="IInAppBillingService$Stub (): void">
              <coverage type="method, %" value="0%   (0/1)"/>
              <coverage type="block, %" value="0%   (0/7)"/>
              <coverage type="line, %" value="0%   (0/3)"/>
            </method>
    </class>
 * @author bjornritzl
 *
 */
@XStreamAlias("class")
public class JavaClass {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit
	private List<Coverage> coverage;
	
	@XStreamImplicit(itemFieldName="method")
	private List<ClassMethod> methods;
	
	public String getName() {
		return name;
	}
	
	public Collection<Coverage> getCoverage() {
		return coverage;
	}
	
	public Collection<ClassMethod> getMethods() {
		return methods;
	}
}
