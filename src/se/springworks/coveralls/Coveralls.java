package se.springworks.coveralls;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import se.springworks.emma.EmmaReport;
import se.springworks.emma.Parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Coveralls {

	private Parser emmaParser = new Parser();

	private String[] sourceFolders = { "../androidutils/androidutils-lib/src" };
	
	public void parse() {
		EmmaReport emmaReport = emmaParser.parse(new File("coverage.xml"));		
		
		CoverallsReport coverallsReport = EmmaToCoverallsConverter.convert(emmaReport, new HashSet<String>(Arrays.asList(sourceFolders)));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String json = null;
		try {
			 json = mapper.writeValueAsString(coverallsReport);
		}
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("JSON = " + json);
	}
	
	

	public static void main(String args[]) {
		Coveralls p = new Coveralls();
		p.parse();
	}

}