package util;

import hbm.DaoNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HibernateMappingParser {
	
	//Tags
	private final String CLAZZ = "class";
	
	//Attributes
	private final String SCHEMA = "schema";
	private final String TABLE = "table";
	private final String NAME = "name";
	
	/**
	 * Finds all hibernate mapping file links starting at a single mapping file. It will dive into each
	 * file the starting file references and so on and so forth until all links have 
	 * been exhausted.
	 * 
	 * @param hbmFile
	 * @return List of Hibernate Mapping Files
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws IOException if any IO problem occured
	 * @throws SAXException if any parsing problem occured
	 */
	public List<File> getAllTableReferences(File hbmFile) 
			throws ParserConfigurationException, IOException, SAXException {
		
		return getAllTableReferences(hbmFile, new ArrayList<File>());
	}
	
	private List<File> getAllTableReferences(File hbmFile, List<File> referenceFiles) 
			throws ParserConfigurationException, IOException, SAXException {
		
		List<String> hbmForeignKeys;
		String directory = getDirectory(hbmFile);
		
		if (!referenceFiles.contains(hbmFile)) {
			referenceFiles.add(hbmFile);
		}
		else {
			//circularReference
			return null;
		}
		
		hbmForeignKeys = getHbmForeignKeys(XmlUtil.createDocument(hbmFile));
		
		for (String foreignKey : hbmForeignKeys) {
			File referenceHbmFile = createHbmFileName(directory, foreignKey);
			getAllTableReferences(referenceHbmFile, referenceFiles);
		}
		
		return referenceFiles;
	}
	
	public String getHbmClass(Document hbmFile) {
		
		Element node = (Element) hbmFile.getElementsByTagName(CLAZZ).item(0);
		return node.getAttribute(NAME);
	}
	
	public String getHbmClass(File hbmFile) throws Exception {
		
		Document hbmXmlFile = XmlUtil.createDocument(hbmFile);
		Element node = (Element) hbmXmlFile.getElementsByTagName(CLAZZ).item(0);
		String clazz = node.getAttribute(NAME);
		
		return removePackages(clazz);
	}
	
	public String getHbmTable(Document hbmFile) {
		
		Element node = (Element) hbmFile.getElementsByTagName(CLAZZ).item(0);
		return node.getAttribute(TABLE);
	}
	
	public String getHbmTable(File hbmFile) throws Exception {
		
		Document hbmXmlFile = XmlUtil.createDocument(hbmFile);
		return getHbmTable(hbmXmlFile);
	}
	
	public String getHbmSchema(Document hbmFile) {
		
		Element node = (Element) hbmFile.getElementsByTagName(CLAZZ).item(0);
		return node.getAttribute(SCHEMA);
	}
	
	private List<Element> getHbmForeignKeyNodes(Document hbmFile) {
		
		List<String> tags = new ArrayList<String>() {{
			add("one-to-many");
			add("many-to-one");
			add("one-to-one");
			add("many-to-many");
		}};
		
		List<Element> foreignKeyNodes = new ArrayList<Element>();
		
		for (String tag : tags) {
			
			NodeList nodeList = hbmFile.getElementsByTagName(tag);
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element node = (Element) nodeList.item(i);
				foreignKeyNodes.add(node);
			}
		}
		
		return foreignKeyNodes;
	}
	
	public List<String> getHbmForeignKeys(Document hbmFile) {
		
		List<String> foreignKeys = new ArrayList<String>();
		
		List<Element> foreignKeyNodes = getHbmForeignKeyNodes(hbmFile);
		
		for (Element foreignKeyNode : foreignKeyNodes) {
			
			String clazz = foreignKeyNode.getAttribute(CLAZZ);
			clazz = removePackages(clazz);
			foreignKeys.add(clazz);
		}
		
		return foreignKeys;
	}
	
	public List<String> getHbmForeignKeys(File hbmFile) throws Exception {
		
		Document hbmXmlFile = XmlUtil.createDocument(hbmFile);
		return getHbmForeignKeys(hbmXmlFile);
	}
	
	public DaoNode getDaoNode(File hbmFile) throws Exception {
		
		DaoNode daoNode = new DaoNode();
		List<Element> foreignKeyNodes;
		Document hbmXmlFile = XmlUtil.createDocument(hbmFile);
		
		daoNode.setClassName(removePackages(getHbmClass(hbmXmlFile)));
		
		foreignKeyNodes = getHbmForeignKeyNodes(hbmXmlFile);
		
		for (Element foreignKeyNode : foreignKeyNodes) {
			
			String alias = null;
			String clazz = null;
			
			if (foreignKeyNode.getTagName().matches(".*-to-many")) {
				Element parentNode = (Element) foreignKeyNode.getParentNode();
				alias = parentNode.getAttribute(NAME);
			}
			else {
				alias = foreignKeyNode.getAttribute(NAME);
			}
			
			clazz = foreignKeyNode.getAttribute(CLAZZ);
			
			daoNode.setDaoForeignKey(removePackages(clazz), alias);
		}
		
		return daoNode;
	}
	
	private File createHbmFileName(String directory, String hbmClass) {
		
		return new File(directory + hbmClass + ".hbm.xml");
	}
	
	private String removePackages(String clazz) {
		
		int endingPackageIndex = clazz.lastIndexOf(".") + 1;
		String clazzName = clazz.substring(((endingPackageIndex == -1) ? 0 : endingPackageIndex), clazz.length());
		return clazzName;
	}
	
	private String getDirectory(File file) {
		
		String fullPath = file.getPath();
		String directory = fullPath.substring(0, fullPath.lastIndexOf(File.separator) + 1);
		
		return directory;
	}
}
