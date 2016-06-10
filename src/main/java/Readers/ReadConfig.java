package Readers;

import Models.ConfigModel;
import Models.SSHConnectionModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadConfig {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static ArrayList<SSHConnectionModel> CONNECTION_DATA = null;
    private static ConfigModel CONFIG_DATA = null;
    private static final String CONNECTION_XML_PATH = "./src/main/resources/ConnectionsConfig.xml";
    private static final String PROPERTIES_PATH = "Config.properties";
    
    private static void readConnections() {
        try {	
            CONNECTION_DATA = new ArrayList<>();
            File inputFile = new File(CONNECTION_XML_PATH);
            
            //File inputFile = new File();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("server");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                CONNECTION_DATA.add(new SSHConnectionModel(
                                    eElement.getElementsByTagName("user").item(0).getTextContent(),
                                    eElement.getElementsByTagName("host").item(0).getTextContent(),
                                    Integer.parseInt(eElement.getElementsByTagName("port").item(0).getTextContent()),
                                    eElement.getElementsByTagName("passphrase").item(0).getTextContent(),
                                    eElement.getElementsByTagName("upload").item(0).getTextContent()));
                }
            }
      } catch (Exception e) {
            LOGGER.warning(e.getMessage());
      }
    }
    
    private static void readConfigProperties() throws IOException {
        InputStream inputStream = null;
        try {
            CONFIG_DATA = new ConfigModel();
            Properties prop = new Properties();
            
            inputStream = ReadConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
            
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_PATH + "' not fount in the classpath");
            }
            
            CONFIG_DATA.setKeyPath(prop.getProperty("private-key-path"));
            CONFIG_DATA.setKnownHostsPath(prop.getProperty("known-hosts-path"));
            CONFIG_DATA.setDownloadFolderPath(prop.getProperty("download-folder"));
            
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        } finally {
            inputStream.close();
        }
    }
    
    public static ArrayList<SSHConnectionModel> getConnections() {
        if(CONNECTION_DATA == null)
            readConnections();
        
        return CONNECTION_DATA;
    }
    
    public static ConfigModel getConfigProperties() throws IOException {
        if(CONFIG_DATA == null)
            readConfigProperties();
        
        return CONFIG_DATA;
    }
}