package se.springworks.emma;

import java.io.File;

import com.thoughtworks.xstream.XStream;

public class Parser {

	public EmmaReport parse(File file) {
		XStream xs = new XStream();
		xs.processAnnotations(EmmaReport.class);
		return  (EmmaReport)xs.fromXML(file);
	}

}
