package Connection;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;

public class Execute {
    List<String> result;
    
    public List<String> executeCommand(String server_IP, String command, String ReturnOutput) throws IOException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(server_IP);
        
        try {
            Session session = currentHost.startSession();
        
            if(session == null) {
                System.out.println("Can not find session for host: " + server_IP);
            } else {
                try {
                    // Execute the command
                    InputStream inputStreamOfCommand = session.exec(command).getInputStream();
                    result = StreamReader.inputReaderToText(inputStreamOfCommand,ReturnOutput, server_IP);
                    //System.out.println(readFully(cmd.getInputStream()).toString());
                } finally {
                    session.close();
                }
            }
        } finally {
            currentHost.disconnect();
        }
        return result;
    }
}