package Connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

public class Execute {   
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static InputStream executeCommand(String server_IP, String command, String ReturnOutput) throws IOException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(server_IP);
        InputStream inputStreamOfCommand = null;
         
        try {
            Session session = currentHost.startSession();
            
            if(session == null) {
                LOGGER.warning("Can not find session for host: " + server_IP);
            } else {
                try {
                    LOGGER.info("Session created for host: " + server_IP);
                    // Split the string to see if it is about the command getAllContainers
                    String commandType = splitString(ReturnOutput, 1);
                    String containerEngineType = splitString(ReturnOutput, 0);
                    // Execute the command
                    inputStreamOfCommand = session.exec(command).getInputStream();                
                } catch(ConnectionException | TransportException e) {
                    LOGGER.warning(e.getMessage());
                } finally {
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