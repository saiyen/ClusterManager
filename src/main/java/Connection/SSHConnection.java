package Connection;

import Models.SSHConnectionModel;
import Readers.ReadConfig;
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
        if(keyPath == null) 
            throw new IOException("The key path is null!");
        
        if(knownHostsPath == null)
            throw new IOException("The known hosts path is null!");


        for (SSHConnectionModel currentConnection : connections) {
            SSHClient sshConnection;
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts(new File(knownHostsPath));
            boolean isConnected = false;
            
            try {
                sshConnection.connect(currentConnection.getHost());
                isConnected = true;
            } catch (TransportException e) {
                try {
                    if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                        String msg = e.getMessage();
                        String[] split = msg.split("`");
                        String fingerPrint = split[3];
                        sshConnection = new SSHClient();
                        sshConnection.addHostKeyVerifier(fingerPrint);
                        sshConnection.connect(currentConnection.getHost());
                        isConnected = true;
                        LOGGER.info("Fingerprint verified and the connection is maded.");
                    } else {
                        LOGGER.warning(e.getMessage());
                    }
                } catch (Exception ex) {
                    LOGGER.warning(ex.getMessage());
                }
            } catch (Exception e) {
                LOGGER.warning("Can not connect host: " + e.getMessage());
            }

            try {
                KeyProvider loadKey = sshConnection.loadKeys(keyPath, currentConnection.getPassphrase());
                sshConnection.authPublickey(currentConnection.getUser(), loadKey);
                LOGGER.info("Authenticated successfully");
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
                isConnected = false;
            }
            
            if(isConnected)
                NODES_COLLECTION.put(currentConnection.getHost(), sshConnection);
        }
    }
    
    public static HashMap<String, SSHClient> getListOfClients() throws IOException {
        if(NODES_COLLECTION.isEmpty())
            makeConnections();
        
            return NODES_COLLECTION;
    }

}
