/*
 * FileReciver.java
 *
 * Created on 15 luty 2007, 20:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jadacz;

import jadacz.lib.FileData;
import jadacz.lib.FileInfo;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for file receiving operation
 * @author Mariusz Durkiewicz alas "BakTi"
 */
public class FileReceiver {
    
    private byte [] data = null;
    private boolean isEnd = false;
    private mainGUI main = null;
    private FileInfo info = null;
    private long offset = 0;
    private FileOutputStream foutStream = null;
    
    /** Creates a new instance of FileReciver */
    public FileReceiver(FileInfo finfo, mainGUI main) {
        this.info = finfo;
        this.main = main;
        data = null;
        try {
            foutStream = new FileOutputStream(this.info.getFilename());
            System.out.println("Otorzono plik do zapisu " + info.getFilename());
        } catch (FileNotFoundException ex) {
            System.out.println("Nie Otorzono pliku !!");
        }
    }
    
    /**
     * Write a portion of data to file
     */
    public void setPortion(FileData part) {
        data = part.getData();
        if (offset > info.getFilesize()) { setEnd(); }
        try {
            foutStream.write(part.getData());
            main.setFileFtansferStatus(offset/info.getFilesize(), offset);
        } catch (IOException ex) {
            System.out.println("Can't write to file");
        } catch (NullPointerException npe) {
            System.out.println("NPE w FileReceiver run 2");
        }
        offset += part.getData().length;
    }
    
    /** Ends file stream */
    public void setEnd() {
        try { foutStream.close(); } catch (IOException ex) { System.out.println("cant close file - fileReceiver"); }
    }
}
