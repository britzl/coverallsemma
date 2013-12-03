package se.springworks.emma.report;

import java.util.HashMap;

import com.vladium.emma.report.AbstractReportGenerator;
import com.vladium.emma.report.IReportGenerator;

public class EmmaReportGeneratorFactory {

	private static HashMap<String, IReportGenerator> generators = new HashMap<String, IReportGenerator>();
	
	static {
		registerGenerator("html", new com.vladium.emma.report.html.ReportGenerator ());
		registerGenerator("txt", new com.vladium.emma.report.txt.ReportGenerator ());
		registerGenerator("xml", new com.vladium.emma.report.xml.ReportGenerator ());
		registerGenerator("coveralls", new se.springworks.emma.report.coveralls.ReportGenerator());
	}
	
	public static void registerGenerator(String type, IReportGenerator generator) {
		generators.put(type, generator);
	}
	
	public static IReportGenerator get(String type) {
		return generators.get(type);
	}
	
}
