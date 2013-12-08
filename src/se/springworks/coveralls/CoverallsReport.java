package se.springworks.coveralls;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
{
  "service_job_id": "1234567890",
  "service_name": "travis-ci",
  "source_files": [
    {
      "name": "example.rb",
      "source": "def four\n  4\nend",
      "coverage": [null, 1, null]
    },
    {
      "name": "two.rb",
      "source": "def seven\n  eight\n  nine\nend",
      "coverage": [null, 1, 0, null]
    }
  ]
}
 * @author bjornritzl
 *
 */
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
	        System.out.println(name + " has " + lines + " lines");
	        this.source = replaced.toString();
	        this.coverage = new Integer[lines];
	        this.name = name;
		}
		
		public void setNoCoverage(int line) {
			coverage[line - 1] = 0;
		}
		
		public void addCoverage(int line) {
			if(coverage[line - 1] == null) {
				coverage[line - 1] = 0;
			}
			coverage[line - 1]++;
		}
	}

	
	@JsonProperty(value="service_job_id")
	private String serviceJobId;
	
	@JsonProperty("service_name")
	private String serviceName;
	
	@JsonProperty("repo_token")
	private String repoToken;
	
	@JsonProperty("source_files")
	private List<SourceFile> sourceFiles = new ArrayList<SourceFile>();
	
	
	public void addSourceFile(SourceFile sf) {
		sourceFiles.add(sf);
	}
	
	public void setServiceJobId(String id) {
		serviceJobId = id;
	}
	
	public void setServiceName(String name) {
		serviceName = name;
	}
	
	public void setRepoToken(String token) {
		repoToken = token;
	}
}
