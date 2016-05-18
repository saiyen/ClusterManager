package Connection;

import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public abstract class SSHConnection {
    private SSHClient sshClient;
    protected Session session;
    
    public void setSSHClient() throws IOException{
        sshClient = new SSHClient();
        sshClient.loadKnownHosts();
        sshClient.connect("HostName");
        
        KeyProvider loadKey = sshClient.loadKeys("Key");
        sshClient.authPublickey("root",loadKey);
    }
    
    public SSHClient getSSHClient(){
        return sshClient;
    }
    
    public void disconnectSSHClient() throws IOException{
        sshClient.disconnect();
    }
    
    public void startSession() throws ConnectionException, TransportException{
        session = getSSHClient().startSession();      
    }
    
    public void endSession() throws TransportException, ConnectionException{
        session.close();
    }
}
