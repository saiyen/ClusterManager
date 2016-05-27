package Connection;

import java.io.IOException;
import java.io.InputStream;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;

public class ExecuteCMD {    
    public void executeCommand(String server_IP, String command, String ReturnOutput) throws IOException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(server_IP);
        
        try {
            Session session = currentHost.startSession();
        
            if(session == null) {
                System.out.println("Can not find session for host: " + server_IP);
            } else {
                try {
                    // ExecuteCMD the command
                    InputStream inputStreamOfCommand = session.exec(command).getInputStream();
                    // Read the executed command
                    CMDReader.addCmdToList(inputStreamOfCommand,ReturnOutput, server_IP);
                } finally {
                    session.close();
                }
            }
        } finally {
            currentHost.disconnect();
        }
    }
}