package se.springworks.emma;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <package name="android">
        <coverage type="class, %" value="0%   (0/1)"/>
        <coverage type="method, %" value="0%   (0/1)"/>
        <coverage type="block, %" value="0%   (0/3)"/>
        <coverage type="line, %" value="0%   (0/1)"/>

        <srcfile name="UnusedStub.java">
 
 * @author bjornritzl
 *
 */
@XStreamAlias("package")
public class JavaPackage {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit
	private List<Coverage> coverage;
	
	@XStreamImplicit(itemFieldName="srcfile")
	private List<SrcFile> sourceFiles;
	
	public String getName() {
		return name;
	}
	
	public Collection<Coverage> getCoverage() {
		return coverage;
	}
	
	public Collection<SrcFile> getSourceFiles() {
		return sourceFiles;
	}
	
}
