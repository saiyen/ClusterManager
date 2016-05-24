package Connection;

import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class SSHConnection implements IConnection{
    private static HashMap<String, SSHClient> listOfClients = new HashMap<>();
    
    public static void makeConnections() throws IOException {
        ReadConfig read = new ReadConfig();
        read.getConfigProperties();
        ArrayList<SSHConnectionModel> connections = read.getConnectionProperties();
        String keyPath = ReadConfig.confData.getKeyPath();

        for (SSHConnectionModel currentConnection : connections) {
            SSHClient sshConnection;
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts();
            sshConnection.connect(currentConnection.getHost());

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
