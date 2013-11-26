package se.springworks.coveralls;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoverallsReport {

	public static class SourceFile {
	    private static final Pattern NEWLINE = Pattern.compile("\r\n|\r|\n");

		@JsonProperty
		private String name;
		
		@JsonProperty
		private String source;

		@JsonProperty
		private Integer[] coverage; 
		
		public SourceFile(String name, String source) {
	        int lines = 1;
	        StringBuffer replaced = new StringBuffer(source.length());
	        Matcher matcher = NEWLINE.matcher(source);
	        while (matcher.find()) {
	            lines++;
	            matcher.appendReplacement(replaced, "\n");
	        }
	        matcher.appendTail(replaced);
	        this.source = replaced.toString();
	        this.coverage = new Integer[lines];
	        this.name = name;
		}
		
		public void addCoverage(int line) {
			coverage[line - 1]++;
		}
	}

	
	@JsonProperty
	private String serviceJobId;
	
	@JsonProperty
	private String serviceName;
	
	@JsonProperty
	private List<SourceFile> sourceFiles;
	
	
	public void addSourceFile(SourceFile sf) {
		sourceFiles.add(sf);
	}
	
	public void setServiceJobId(String id) {
		serviceJobId = id;
	}
	
	public void setServiceName(String name) {
		serviceName = name;
	}
}
