/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import Models.SSHConnectionModel;
import Readers.ReadConfig;

/**
 *
 * @author Ivan Ivanov
 */
public class Tools {
    public static SSHConnectionModel searchInConnections(String hostname) {
        for (SSHConnectionModel connectionModel : ReadConfig.getConnections()) {
            if(hostname.equals(connectionModel.getHost()))
                return connectionModel;
        }
        
        return null;
    }
}