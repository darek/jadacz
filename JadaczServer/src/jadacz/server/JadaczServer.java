/**
 * 
 */
package jadacz.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

/**
 * Main class of the JadaczServer, which is executable to run the server.
 * 
 * @author Pawel 'top' Luczak
 * @author Szymon 'tecku' Kordyaczny
 * @version 1.0
 */
public class JadaczServer {

    /**
     * Use this function to report what servant is doing at the moment.
     * 
     * @param s report info
     */
    private static void report(String s) {
	System.out.println("Server: " + s);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
	System.out.println("Starting JadaczServer...");

	JXMLConfig confRead = JXMLConfig.getInstance();
	Loger log = Loger.getInstance();

	/*
	String dbUrl = new String("jdbc:"
		+  + "://"
		+ confRead.getProperty("dbsrv", "localhost") + ":"
		+ confRead.getProperty("dbport", "3306") + "/"
		+ confRead.getProperty("dbname", "jadacz"));
*/

	
	try {
	    report("initializing JDBC connector...");
	    URI dbURL = new URI(
		"jdbc:" + confRead.getProperty("dbtype", "mysql"),
		null,
		confRead.getProperty("dbsrv", "localhost"),
		Integer.parseInt(confRead.getProperty("dbport", "3306")),
		"/"+confRead.getProperty("dbname", "jadacz"),
		null,null);
	    DBConnection.init(
		    dbURL.toString(),
		    confRead.getProperty("dbuser", "jadacz"),
		    confRead.getProperty("dbpass", "jadka"),
		    confRead.getProperty("jdbcdriver", "com.mysql.jdbc.Driver")
		    );
	    report("...JDBC connector initialization: OK");
	} catch (Exception e) {
	    report("...JDBC connector initialization: FAILED");
	    log.println(e.toString());
	    System.err.println(e.getMessage());
	    report("cannot start");
	    report("shutting down");
	    System.exit(0);
	}
	
	// getting port from config
	int port = Integer.parseInt(confRead.getProperty("port", "8060"));
	
	Socket socket = null;
	ServerSocket sSocket = null;
	Thread t = null;
	try {
	    report("opening server socket... ");
	    sSocket = new ServerSocket(port);
	    report("...server socket: OPENED");
	    report("started on "
		    + InetAddress.getLocalHost().getHostAddress() + ":" + port);
	    
	    //UserContainer userCont = UserContainer.getInstance();
	    int id = 1;
	    
	    report("listening for users...");
	    while (true) {
		socket = sSocket.accept();
		report("starting UserServant("+id+")...");
		t = new Thread(new UserServant(socket, id));
		t.start();
		//report("there's about " + userCont.size() + " users online");
		++id;
	    }
	} catch (IOException e) {
	    report("input/output exception");
	    report("shutting down");
	    System.err.println(e);
	} catch (SecurityException e) {
	    report("security exception");
	    report("shutting down");
	    System.err.println(e);
	} finally {
	    try {
		if (sSocket != null) {
		    sSocket.close();
		}
	    } catch (IOException e) {
		report("cannot close server socket");
		System.err.println(e);
	    }
	    report("stoped.");
	}

	
    } // main

}
