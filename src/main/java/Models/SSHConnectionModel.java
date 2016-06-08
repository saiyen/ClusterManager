package Models;

public class SSHConnectionModel {
    private String user;
    private String host;
    private String hostname;
    private int port;
    private String passphrase;    
    private String uploadPath;

    public SSHConnectionModel() {
    }

    public SSHConnectionModel(String user, String host, String hostname ,int port, String passphrase, String uploadPath) {
        this.hostname = hostname;
        this.user = user;
        this.host = host;
        this.port = port;
        this.passphrase = passphrase;
        this.uploadPath = uploadPath;
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

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }  

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
}
