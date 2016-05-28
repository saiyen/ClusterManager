package Connection;

import Models.SSHClientWrapperModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.schmizz.sshj.connection.channel.direct.Session;

public class Execute {   
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static void executeCommand(String server_IP, String command, String ReturnOutput) throws IOException {
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
                    // Split the string to see if it is about the command getAllContainers
                    String commandType = splitString(ReturnOutput, 1);
                    String containerEngineType = splitString(ReturnOutput, 1);
                    // Execute the command
                    InputStream inputStreamOfCommand = session.exec(command).getInputStream();
                    
                    if("getAllContainers".equals(commandType)){
                        // Read the executed command and them to a list
                        CommandOutputReader.addOutputToList(inputStreamOfCommand,containerEngineType, server_IP);
                    }
                } catch(Exception e) {
                    LOGGER.warning(e.getMessage());
                }
            } 
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
        } 
    }
    
    public static String splitString(String words, int position){
        String[] arrayOfStrings = words.split("\\s+");
        String substring = arrayOfStrings[position];
        return substring;
    }
}