package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtil {
	
	/**
	 * Input a File object that represents an XML file on the system. Turns 
	 * the File object into a Document object.
	 * 
	 * @param file, file that will be turned into an XML Document
	 * @return XML Document
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws IOException if any IO error occured
	 * @throws SAXException if any parsing error occured
	 */
	public static Document createDocument(File file)
			throws ParserConfigurationException, IOException, SAXException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document xmlFile = null;
		
		docFactory.setValidating(false);
		docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	
		docBuilder = docFactory.newDocumentBuilder();
		xmlFile = docBuilder.parse(file);

		return xmlFile;
	}
	
	public static String createString(Document document) throws IOException, TransformerException {
		
		String result = null;
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(document), new StreamResult(sw));
        result = sw.toString();
        sw.close();
    
        return result;
	}
	
	public static void overwriteXmlFile(File xmlFile, Document xml) throws TransformerException, FileNotFoundException {
	
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(new PrintWriter(new FileOutputStream(xmlFile, false)));
		Source input = new DOMSource(xml);

		transformer.transform(input, output);
	}
}
