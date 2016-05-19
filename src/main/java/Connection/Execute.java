package Connection;

import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import static net.schmizz.sshj.common.IOUtils.readFully;

public class Execute extends SSHConnection {

    public void ExecuteCommand(String Hostname, String Command) throws IOException {
        try {
            setSSHClient(Hostname);
            startSession();                
            try {
                final Command cmd = session.exec(Command);
                System.out.println(readFully(cmd.getInputStream()).toString());
            } finally {
                endSession();
            }
        } finally {
            disconnectSSHClient();
        }
    }
}