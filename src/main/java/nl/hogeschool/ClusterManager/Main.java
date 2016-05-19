package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;
import ReadConfig.ReadConfig;
import java.io.IOException;

public class Main {
    public static void main(String[] Args) {
        ReadConfig read = new ReadConfig();
        SSHConnection connection = new SSHConnection();
        
        try {
            read.getConfigProperties();
            //Singleton for the connection

        } catch (Exception e) {
        } finally {
        }
    }
}
