package se.springworks.emma;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <coverage type="method, %" value="0%   (0/1)"/>
 * @author bjornritzl
 *
 */
@XStreamAlias("coverage")
public class Coverage {

	@XStreamAlias("type")
	@XStreamAsAttribute
	private String type;
	
	@XStreamAlias("value")
	@XStreamAsAttribute
	private String value;
	
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isMethodCoverage() {
		return type.toLowerCase().startsWith("method");
	}
	
	public boolean isBlockCoverage() {
		return type.toLowerCase().startsWith("block");
	}

	public boolean isLineCoverage() {
		return type.toLowerCase().startsWith("line");
	}
	
	public int getValueAsPercent() {
		int percentIndex = value.indexOf('%');
		if(percentIndex == -1) {
			return 0;
		}
		
		return Integer.parseInt(value.substring(0, percentIndex));
	}
	
	public int getActualValue() {
		int leftParenthesis = value.indexOf('(');
		int divider = value.indexOf('/');
		return Integer.parseInt(value.substring(leftParenthesis + 1, divider));
	}
	
	public int getMaxValue() {
		int divider = value.indexOf('/');
		int rightParenthesis = value.indexOf(')');
		return Integer.parseInt(value.substring(divider + 1, rightParenthesis));
	}
}
