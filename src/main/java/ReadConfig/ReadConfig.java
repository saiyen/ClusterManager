/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReadConfig;

import Models.SSHConnectionModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ivan
 */
public class ReadConfig {
    SSHConnectionModel configData = new SSHConnectionModel();
    InputStream inputStream;
    
    public SSHConnectionModel getConnctionProperties() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileNeme = "ConnConfig.properties";
            
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileNeme);
            
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileNeme + "' not fount in the classpath");
            }
            
            configData.setUser(prop.getProperty("user"));
            configData.setHost(prop.getProperty("host"));
            configData.setPort(Integer.parseInt(prop.getProperty("port")));
            configData.setKeyPath(prop.getProperty("pKeyPath"));
            configData.setPassphrase(prop.getProperty("passphrase"));
            
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return configData;
    }
}
