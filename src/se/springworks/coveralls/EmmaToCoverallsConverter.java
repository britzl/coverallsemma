package se.springworks.coveralls;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import se.springworks.coveralls.CoverallsReport.SourceFile;
import se.springworks.emma.ClassMethod;
import se.springworks.emma.EmmaReport;
import se.springworks.emma.JavaClass;
import se.springworks.emma.JavaPackage;
import se.springworks.emma.SrcFile;

public class EmmaToCoverallsConverter {

	
	public static CoverallsReport convert(EmmaReport er, Set<String> sourceFolders) {
		CoverallsReport cr = new CoverallsReport();
		
		Collection<JavaPackage> packages = er.getData().getAll().getPackages();
		for(JavaPackage p : packages) {
			handlePackage(cr, p, sourceFolders);	
		}

		return cr;
	}
	
	private static void handlePackage(CoverallsReport report, JavaPackage p, Set<String> sourceFolders) {
		String packageAsPath = p.getName().replace('.', '/');
		
		for(SrcFile srcFile : p.getSourceFiles()) {
			File file = getFile(packageAsPath, srcFile, sourceFolders);
			if(file != null) {
				String source = SourceLoader.load(file);
				if(source != null) {
					SourceFile coverallsFile = new SourceFile(srcFile.getName(), source);
					report.addSourceFile(coverallsFile);
					handleSourceFile(coverallsFile, srcFile);
				}
			}
		}
	}
	
	private static void handleSourceFile(SourceFile sourceFile, SrcFile emmaFile) {
		for(JavaClass jc : emmaFile.getClasses()) {
			for(ClassMethod method : jc.getMethods()) {
			}
		}
	}
	
	
	private static File getFile(String packagePath, SrcFile srcFile, Set<String> sourceFolders) {
		for(String folder : sourceFolders) {
			File file = new File(folder + '/' + packagePath + '/' + srcFile.getName());
			if(file.exists()) {
				return file;
			}
		}
		return null;
	}
	
}
