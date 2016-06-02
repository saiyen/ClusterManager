package Connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

public class ExecuteCommand {   
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static InputStream execute(String server_ip, String command) throws IOException, InterruptedException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(server_ip);
        InputStream inputStreamOfCommand = null;
         
        try {
            Session session = currentHost.startSession();
            
            if(session == null) {
                LOGGER.warning("Can not find session for host: " + server_ip);
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
                }
            } 
        } catch(ConnectionException | TransportException e) {
            LOGGER.warning(e.getMessage());
        }
        return inputStreamOfCommand;
    }
    
    public static String splitString(String words, int position){
        String[] arrayOfStrings = words.split("\\s+");
        String substring = arrayOfStrings[position];
        return substring;
    }
}