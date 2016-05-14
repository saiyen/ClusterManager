/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author ivan
 */
public class SSHConnectionModel {
    private String user;
    private String host;
    private int port;
    private String passphrase;    

    public SSHConnectionModel() {
    }

    public SSHConnectionModel(String user, String host, int port, String passphrase) {
        this.user = user;
        this.host = host;
        this.port = port;
        this.passphrase = passphrase;
    }

    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
    
    
}
