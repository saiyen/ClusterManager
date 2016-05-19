package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;
import java.io.IOException;

public class Main {
    public static void main(String[] Args) {
        SSHConnection connection = new SSHConnection();
        
        try {
            connection.makeConnection();
            //SFTP download
            //SFTP upload
        } catch (Exception e) {
        } finally {
        }
    }
}
