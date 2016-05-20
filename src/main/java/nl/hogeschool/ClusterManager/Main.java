package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;

public class Main {
    public static void main(String[] Args) {
        try {
            SSHConnection SSHConnection = new SSHConnection();
            SSHConnection.makeConnection();
            
            Docker.getAllContainers();
        } catch (Exception e) {
        } finally {
        }
    }
}
