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
public class ConfigModel {
    private String keyPath;
    private String downloadFolderPath;
    private String knownHostsPath;

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getDownloadFolderPath() {
        return downloadFolderPath;
    }

    public void setDownloadFolderPath(String downloadFolderPath) {
        this.downloadFolderPath = downloadFolderPath;
    }

    public String getKnownHostsPath() {
        return knownHostsPath;
    }

    public void setKnownHostsPath(String knownHostsPath) {
        this.knownHostsPath = knownHostsPath;
    }
    
    
    
    
}
