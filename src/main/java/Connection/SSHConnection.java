package Connection;

import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.DisconnectReason;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class SSHConnection {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static HashMap<String, SSHClient> NODES_COLLECTION = new HashMap<>();
    
    public static void makeConnections() throws IOException {

        ArrayList<SSHConnectionModel> connections = ReadConfig.getConnections();
        String keyPath = ReadConfig.getConfigProperties().getKeyPath();
        String knownHostsPath = ReadConfig.getConfigProperties().getKnownHostsPath();

        for (SSHConnectionModel currentConnection : connections) {
            SSHClient sshConnection;
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts(new File(knownHostsPath));
            boolean connectionFlag = false;
            
            try {
                sshConnection.connect(currentConnection.getHost());
                connectionFlag = true;
            } catch (TransportException e) {
                if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                    String msg = e.getMessage();
                    String[] split = msg.split("`");
                    String fingerPrint = split[3];
                    sshConnection = new SSHClient();
                    sshConnection.addHostKeyVerifier(fingerPrint);
                    sshConnection.connect(currentConnection.getHost());
                    connectionFlag = true;
                } else {
                    throw e;
                }
                
                LOGGER.warning(e.getMessage());
            }

            try {
                KeyProvider loadKey = sshConnection.loadKeys(keyPath, currentConnection.getPassphrase());
                sshConnection.authPublickey(currentConnection.getUser(), loadKey);
                LOGGER.info("Authenticated successfully");
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
                connectionFlag = false;
            }
            
            if(connectionFlag)
                NODES_COLLECTION.put(currentConnection.getHost(), sshConnection);
        }
    }
    
    public static HashMap<String, SSHClient> getListOfClients() throws IOException {
        if(NODES_COLLECTION.isEmpty())
            makeConnections();
        
            return NODES_COLLECTION;
    }

}
