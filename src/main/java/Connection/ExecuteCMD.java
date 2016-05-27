package Connection;

import Models.SSHClientWrapperModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.schmizz.sshj.connection.channel.direct.Session;

public class ExecuteCMD {   
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public void executeCommand(String server_IP, String command, String ReturnOutput) throws IOException {
        SSHClientWrapperModel currentHost = SSHConnection.getListOfClients().get(server_IP);
 
        try {
            Session session = currentHost.getSession();
            LOGGER.warning("Can not find session for host: " + server_IP);
            if(session == null) {
                System.out.println("Can not find session for host: " + server_IP);
                LOGGER.warning("Can not find session for host: " + server_IP);
            } else {
                try {
                    LOGGER.info("Session created for host: " + server_IP);
                    // Execute the command
                    InputStream inputStreamOfCommand = session.exec(command).getInputStream();
                    // Read the executed command
                    CMDReader.addCmdToList(inputStreamOfCommand,ReturnOutput, server_IP);
                } catch(Exception e) {
                    LOGGER.warning(e.getMessage());
                }
            } 
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
        } 
    }
}