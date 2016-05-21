package Connection;

import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import java.io.InputStream;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;

public class Execute {
    private Command cmd;
    
    public InputStream executeCommand(String hostname, String command) throws IOException {
        SSHClient currentHost = SSHConnection.getListOfClients().get(hostname);
        
        try {
            Session session = currentHost.startSession();
        
            if(session == null) {
                System.out.println("Can not find session for host: " + hostname);
            } else {
                try {
                    // Execute the command
                    cmd = session.exec(command);            
                    //System.out.println(readFully(cmd.getInputStream()).toString());
                } finally {
                    session.close();
                }
            }
        } finally {
            currentHost.disconnect();
        }
        return cmd.getInputStream();
    }
}