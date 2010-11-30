/**
 * 
 */
package jadacz.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import com.sun.jmx.snmp.Timestamp;

/**
 * Singleton used for logging to error.log
 * @author Pawel 'top' Luczak
 * 
 * @version 0.11
 */
public class Loger {

    /**
     * Writer user in Loger.
     */
    private static BufferedWriter out;

    /**
     * Address of Loger.
     */
    private final static Loger ourInstance = new Loger();

    /**
     * Level of loging.
     */
    private static byte logLevel = 0;

    public final static byte NORMAL = 0;
    public final static byte DEBUG = 1;

    /**
     * Loger contructor.
     */

    private Loger() {
	try {
	    out = new BufferedWriter(new FileWriter("error.log", false));
	} catch (IOException e) {
	    System.out.println("Loger Error.");
	}
    }

    /**
     * Gets Loger instance.
     * 
     * @return Loger instance
     */
    public static Loger getInstance() { 
	return ourInstance; 
    }

    /**
     * Writes one line in error.log with time.
     * 
     * @param error Text to write in error.log
     * @param level Level of logging (use Loger.NORMAL or Loger.DEBUG)
     */
    public void println(String error,byte level) {
	synchronized (out) {
	    if(level<=logLevel) {
		try {

		    out.write(
			    new Date(new Timestamp().getDateTime()).toString() + " " + 
			    new Time(new Timestamp().getDateTime()).toString() 
			    + " - " + error);
		    out.newLine();
		    out.flush();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    
    /**
     * Writes one line in error.log with time.
     * Old, still in code not to change all Loger use. 
     * 
     * @param error Text to write in error.log
     */
    public void println(String error) {
	synchronized(out) {
		try {

		    out.write(
			    new Date(new Timestamp().getDateTime()).toString() + " " + 
			    new Time(new Timestamp().getDateTime()).toString() 
			    + " - " + error);
		    out.newLine();
		    out.flush();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
    }
    
    /**
     * Sets level of logging.
     * Available options are: Loger.NORMAL , Loger.DEBUG
     * Any other options sets logging level to NORMAL.
     * 
     * @param newLevel new logging level
     */
    public static void setLogLevel(byte newLevel) {
	if(newLevel == Loger.DEBUG || newLevel == Loger.NORMAL) {
	    logLevel = newLevel;
	}
	else {
	    logLevel = Loger.NORMAL;
	}
    }
}
