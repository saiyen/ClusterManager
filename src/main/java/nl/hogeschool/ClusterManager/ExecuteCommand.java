package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

public class ExecuteCommand {   
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    //Overload
    public static InputStream execute(String server_ip, String command) throws IOException, InterruptedException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(server_ip);
        return execute(server_ip, command, currentHost);
    }
    
    public static InputStream execute(String server_ip, String command, SSHClient client) throws IOException, InterruptedException {
        InputStream inputStreamOfCommand = null;
         
        try {
            Session session = client.startSession();
            
            if(session == null) {
                LOGGER.warning("Can not create session for host: " + server_ip);
            } else {
                try {
                    LOGGER.info("Session created for host: " + server_ip);
                    // Execute the command
                    inputStreamOfCommand = session.exec(command).getInputStream();
                } catch(ConnectionException | TransportException e) {
                    LOGGER.warning(e.getMessage());
                } finally {
                    Thread.sleep(1000);
                    session.close();
                    LOGGER.info("Session closed;");
                }
            } 
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
        }
        
        return inputStreamOfCommand;
    }
}