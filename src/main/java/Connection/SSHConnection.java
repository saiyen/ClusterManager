/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import com.jcraft.jsch.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author ivan
 */
public class SSHConnection implements IConnection{
    HashMap<String, Session> listOfSessions = new HashMap<>();
    
    public void AuthPublicKey() {
        try{
            JSch jsch=new JSch();
            ReadConfig read = new ReadConfig();
            ArrayList<SSHConnectionModel> connections = read.getConnectionProperties();

            String keyPath = read.getConfigProperties().getKeyPath();

            for (SSHConnectionModel item : connections) {
                jsch.addIdentity(keyPath, item.getPassphrase());

                Session session=jsch.getSession(item.getUser(), item.getHost(), item.getPort());

                //Unsecure needs to add into known hosts
                //http://serverfault.com/questions/321167/add-correct-host-key-in-known-hosts-multiple-ssh-host-keys-per-hostname
                //TODO: if statment to check if the host is known
                java.util.Properties config = new java.util.Properties(); 
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                //Add all session into a list
                listOfSessions.put(item.getHost(), session);

                session.connect();
            }
        }
        catch(Exception e){
          System.out.println(e);
        } 
    }
    
    public void ExecCommand(String host, String command) throws IOException, JSchException{
        Session session = listOfSessions.get(host);
        
        if(session == null) {
            System.out.println("Can not find session for host: " + host);
        } else {
            Channel channel=session.openChannel("exec");

            ((ChannelExec)channel).setCommand(command);

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();

            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
              while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
              }
              if(channel.isClosed()){
                if(in.available()>0) continue; 
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }

            channel.disconnect();
            session.disconnect();
        }
    }
}
