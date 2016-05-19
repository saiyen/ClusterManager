package Connection;

import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import static net.schmizz.sshj.common.IOUtils.readFully;
import net.schmizz.sshj.connection.channel.direct.Session;

public class Execute extends SSHConnection {
    SSHConnection connection = new SSHConnection();
    
    public void executeCommand(String hostname, String command) throws IOException {
        SSHClient currentHost = connection.getListOfClients().get(hostname);
        
        try {
            Session session = currentHost.startSession();
        
            if(session == null) {
                System.out.println("Can not find session for host: " + hostname);
            } else {
                try {
                    Command cmd = session.exec(command);
                    System.out.println(readFully(cmd.getInputStream()).toString());
                } finally {
                    session.close();
                }
            }
        } finally {
            currentHost.disconnect();
        }
    }
}