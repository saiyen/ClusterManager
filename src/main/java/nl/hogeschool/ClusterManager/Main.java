package nl.hogeschool.ClusterManager;

import Connection.*;
import java.io.IOException;

public class Main {
    public static void main(String[] Args) throws IOException{
        SSHConnection connect = new SSHConnection();
        connect.AuthPublicKey();
    }
}
