package se.springworks.coveralls;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

public class SourceLoader {

    
    public static String load(final File file) {
        final StringWriter sw = new StringWriter();
		try {
			Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)));
	        final char[] buffer = new char[4096];
	        int n = 0;
	        while ( -1 != ( n = reader.read( buffer ) ) )
	        {
	            sw.write( buffer, 0, n );
	        }
	        sw.flush();
	        reader.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return sw.toString();
    }
}