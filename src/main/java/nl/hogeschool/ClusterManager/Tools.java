/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;

/**
 *
 * @author Ivan Ivanov
 */
public class Tools {
    public static SSHConnectionModel searchUploadPath(String hostname) {
        for (SSHConnectionModel uploadPath : ReadConfig.getConnections()) {
            if(hostname == uploadPath.getHost())
                return uploadPath;
        }
        
        return null;
    }
}
