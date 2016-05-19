package Connection;

import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class SSHConnection implements IConnection{
    private SSHClient sshConnection;
    private HashMap<String, SSHClient> listOfClients = new HashMap<>();
    
    public void makeConnection() throws IOException {
        ReadConfig read = new ReadConfig();
        ArrayList<SSHConnectionModel> connections = read.getConnectionProperties();
        
        String keyPath = ReadConfig.confData.getKeyPath();

        for (SSHConnectionModel currentConnection : connections) {
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts();
            sshConnection.connect(currentConnection.getHost());

            KeyProvider loadKey = sshConnection.loadKeys(keyPath, currentConnection.getPassphrase());
            sshConnection.authPublickey(currentConnection.getUser(), loadKey);
            
            listOfClients.put(currentConnection.getHost(), sshConnection);
        }
    }
    
    public HashMap<String, SSHClient> getListOfClients() {
            return this.listOfClients;
    }

}
