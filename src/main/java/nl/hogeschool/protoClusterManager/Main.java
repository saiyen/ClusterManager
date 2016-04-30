/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.protoClusterManager;

public class Main {
    public static void main(String[] Args){
        
        // Make a container manually and set it's container ID 
        Container container = new Container();
        container.setContainerID(1);
        
        // run the above container by using Docker
        Docker.startContainer(container);
    }
}
