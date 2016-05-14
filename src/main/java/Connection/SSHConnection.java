/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import ReadConfig.ReadConfig;
import com.jcraft.jsch.*;
import java.io.*;
/**
 *
 * @author ivan
 */
public class SSHConnection implements IConnection{
    public void AuthPublicKey() {
        try{
            JSch jsch=new JSch();
            ReadConfig read = new ReadConfig();
            
            String user = read.getConnectionProperties().get(0).getUser();;
            String host = read.getConnectionProperties().get(0).getHost();
            int port = read.getConnectionProperties().get(0).getPort();
            String passphrase = read.getConnectionProperties().get(0).getPassphrase();
            String keyPath = read.getConfigProperties().getKeyPath();
            
            jsch.addIdentity(keyPath, passphrase);

            Session session=jsch.getSession(user, host, port);

            //Unsecure needs to add into known hosts
            //http://serverfault.com/questions/321167/add-correct-host-key-in-known-hosts-multiple-ssh-host-keys-per-hostname
            //TODO: if statment to check if the host is known
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            Channel channel=session.openChannel("exec");
            //For more command use n\
            ((ChannelExec)channel).setCommand("ping -c 1 google.com");

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();

        //        Channel channel=session.openChannel("shell");
        //
        //        channel.setInputStream(System.in);
        //        channel.setOutputStream(System.out);

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
        catch(Exception e){
          System.out.println(e);
        } 
    }
}
