package Connection;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import static net.schmizz.sshj.common.IOUtils.readFully;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class Execute {

    public static void ExecuteCommand() throws IOException {

        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();
        ssh.connect("Hostname");

        try {
            KeyProvider loadKey = ssh.loadKeys("Key");
            ssh.authPublickey("Username",loadKey);
           
            final Session session = ssh.startSession();                   
            try {
                final Command cmd = session.exec("test");
                System.out.println(readFully(cmd.getInputStream()).toString());
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }
}
