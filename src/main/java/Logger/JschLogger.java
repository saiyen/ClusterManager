/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logger;

/**
 *
 * @author ivan
 */
public class JschLogger implements com.jcraft.jsch.Logger {
    static java.util.Hashtable name=new java.util.Hashtable();
    static{
        name.put(new Integer(DEBUG), "DEBUG: ");
        name.put(new Integer(INFO), "INFO: ");
        name.put(new Integer(WARN), "WARN: ");
        name.put(new Integer(ERROR), "ERROR: ");
        name.put(new Integer(FATAL), "FATAL: ");
    }
    public boolean isEnabled(int level){
        return true;
    }
    public void log(int level, String message){
        System.err.print(name.get(new Integer(level)));
        System.err.println(message);
      
        writeToLog(name.get(new Integer(level)), message);
    }
    
    public void writeToLog(Object neshto, String message){
        //TODO: da zapisha vuv file.
    }
}
