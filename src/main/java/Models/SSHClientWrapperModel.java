/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;

/**
 *
 * @author Ivan Ivanov
 */
public class SSHClientWrapperModel {
    private SSHClient client;
    private Session session;

    public SSHClientWrapperModel(SSHClient client, Session session) {
        this.client = client;
        this.session = session;
    }    

    public SSHClient getClient() {
        return client;
    }

    public Session getSession() {
        return session;
    }
    
    
    
    

}

