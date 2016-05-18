package nl.hogeschool.ClusterManager;

import Connection.*;
import Logger.JschLogger;
import ReadConfig.ReadConfig;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import java.io.IOException;

public class Main {
    public static void main(String[] Args){
        try {
            //JSch.setLogger(new JschLogger());
            ReadConfig read = new ReadConfig();
            read.getConnectionProperties();

            SSHConnection connect = new SSHConnection();
            connect.AuthPublicKey();
            
            //127.0.0.1 is only for test
            connect.ExecCommand("127.0.0.1", "ping -c 1 google.com");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
