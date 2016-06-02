/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author denni
 */
public class PingServer {

    public boolean ping(String host) {
        try {
            Process p = Runtime.getRuntime().exec("ping " + host);

            String s = null;
            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
 
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
             
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.exit(0);
            
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
