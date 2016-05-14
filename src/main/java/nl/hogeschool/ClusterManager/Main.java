package nl.hogeschool.ClusterManager;

import Connection.*;
import ReadConfig.ReadConfig;
import java.io.IOException;

public class Main {
    public static void main(String[] Args) throws IOException{
        
        ReadConfig read = new ReadConfig();
        read.getConnectionProperties();
        
        SSHConnection connect = new SSHConnection();
        connect.AuthPublicKey();
    }
}
