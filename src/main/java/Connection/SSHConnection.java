package Connection;

import Interfaces.IConnection;
import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.DisconnectReason;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class SSHConnection implements IConnection{
    private static HashMap<String, SSHClient> listOfClients = new HashMap<>();
    
    public static void makeConnections() throws IOException {
        ReadConfig read = new ReadConfig();
        ArrayList<SSHConnectionModel> connections = read.getConnectionProperties();
        
        read.getConfigProperties();
        String keyPath = ReadConfig.confData.getKeyPath();

        for (SSHConnectionModel currentConnection : connections) {
            SSHClient sshConnection;
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts();
            
            try {
                sshConnection.connect(currentConnection.getHost());
            } catch (TransportException e) {
                if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                    String msg = e.getMessage();
                    String[] split = msg.split("`");
                    String vc = split[3];
                    sshConnection = new SSHClient();
                    sshConnection.addHostKeyVerifier(vc);
                    sshConnection.connect(currentConnection.getHost());
                } else {
                    throw e;
                }
            }

            KeyProvider loadKey = sshConnection.loadKeys(keyPath, currentConnection.getPassphrase());
            sshConnection.authPublickey(currentConnection.getUser(), loadKey);
            
            listOfClients.put(currentConnection.getHost(), sshConnection);
        }
    }
    
    public static HashMap<String, SSHClient> getListOfClients() throws IOException {
        if(listOfClients.isEmpty())
            makeConnections();
        
            return listOfClients;
    }

}
