/*
 * FileSender.java
 *
 * Created on 14 luty 2007, 23:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jadacz;

import jadacz.lib.FileData;
import jadacz.lib.FileInfo;
import jadacz.lib.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Mariusz Durkiewicz alas "BakTi"
 */
public class FileSender implements Runnable {
    
    private UserConnection connection = null;
    private FileInfo finfo;
    private FileInputStream finStream = null;
    private mainGUI main = null;
    private boolean isEnd = false;
    
    /** Creates a new instance of FileSender */
    public FileSender(FileInfo file, UserConnection con, mainGUI stat_rec) {
        this.finfo = file;
        this.connection = con;
        this.main = stat_rec;
   }
    
   /** Set sending termination flag */ 
   public void setEnd() {
       isEnd = true;
   }

   /** Main Runnable function */
    public void run() {
        long offset = 0;
        int onebyte;
        int datalength = 2048;
        byte [] buffer;
        try {
            finStream = new FileInputStream(finfo.getFilename());
//            System.out.println("Otwieram plik do wysylania : " + finfo.getFilename());
            System.out.println(finStream.available());
            while (!isEnd) {
                if (finStream.available() == 0 ) { break; }
                System.out.println("Bajtow :" +finStream.available());
                if (finStream.available() > datalength) { buffer = new byte[datalength]; } else { buffer = new byte[finStream.available()]; }
                onebyte = finStream.read(buffer);
                FileData data = new FileData(this.finfo.getJID(), offset, datalength, buffer);
                connection.sendFileData(data);
                main.setFileFtansferStatus(offset/finfo.getFilesize(), offset);
                offset += datalength;
            }
            finStream.close();
        } 
        catch (FileNotFoundException ex) {
            System.out.println("Nie odnaleziono pliku");
        }
        catch (IOException ioe) {
            System.out.println("I/O Error in FileSender.run() - can't read file");
        }
        catch (SecurityException se) {
            System.out.println("Brak dostepu do pliku");
        }
        System.out.println("Koncze wysylanie");
        connection.sendFileTransferComplete();
    }
    
}
