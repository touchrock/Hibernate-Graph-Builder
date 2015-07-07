package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Properties;

/**
 * A utility class with the purpose of interacting with hibernate mapping files, and mapping database table
 * names to hibernate mapping files.
 * @author e1054909
 *
 */
public class HibernateFileUtility {
	
	private String defaultHbmDirectory = "src/test/resources/";
	private File hbmDirectory;
	private HibernateMappingParser parser = new HibernateMappingParser();
	
	private FilenameFilter hbmFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".hbm.xml")) ? true : false;
		}
	};
	
	public HibernateFileUtility() throws Exception {
		hbmDirectory = new File(defaultHbmDirectory);
		checkForHbmFileDirectory();
	}
	
	public HibernateFileUtility(File hbmDirectory) throws FileNotFoundException {
		this.hbmDirectory = hbmDirectory;
		checkForHbmFileDirectory();
	}
	
	private void setHbmDirectory() throws Exception {
		
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("test/resources/UnitTest.properties");
		props.load(in);
		in.close();
		
		String hbmDirectoryStr = props.getProperty("HibernateMappingDirectory", defaultHbmDirectory);
		
		hbmDirectory = new File(hbmDirectoryStr);
	}
	
	/**
	 * The method will find an Hibernate mapping file for case <b>insensitive</b> Hibernate class name<br>
	 * @param className
	 * @return File, hbmFile
	 * @throws Exception 
	 */
	public File findHbmFile(String className) throws Exception {
		
		
		
		for (File file : hbmDirectory.listFiles(hbmFilter)) {
			if (parser.getHbmClass(file).equalsIgnoreCase(className)) {
				return file;
			}
		}
		
		throw new FileNotFoundException(className + " not found in " + hbmDirectory);
	}
	
	private void checkForHbmFileDirectory() throws FileNotFoundException {
		
		if (!hbmDirectory.exists() && hbmDirectory.isDirectory()) {
			throw new FileNotFoundException(hbmDirectory + " doesn't exist.");
		}
	}
	
	public File getHbmDirectory() {
		return hbmDirectory;
	}
}
