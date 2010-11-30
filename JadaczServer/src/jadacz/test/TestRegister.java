/**
 * 
 */
package jadacz.test;

import jadacz.lib.Connection;
import jadacz.lib.Packet;
import jadacz.lib.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Szymon 'tecku' Kordyaczny
 *
 */
public class TestRegister {
    
    //private static String server = "91.90.179.161";
    private static String server = "127.0.0.1";
    private static int port = 8060;
    private static Socket socket;
    private static String pass = "pass";
    private static int myJID = 112;
    private static Connection connection = null;
    private static boolean logged =  false;
    private static int tryCount = 3;

    private static void sleep(int time) {
	try {
	    Thread.sleep(time);
	} catch (InterruptedException e) {
	}
    }
    
    public static void main(String[] args) {
	System.out.println("Jadacz - Test Text Client");
        System.out.println();
        
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
 
        try {      
            System.out.println("Connecting with "+server+":"+port);
            socket = new Socket(server,port);
	    connection = new Connection(socket);
	    Packet p = new Packet();
	    
	    // receive welcome
	    connection.receive(p);
	    System.out.println("Connected: " + new String(p.getContent()));

	    // send login request
	    UserInfo ui = new UserInfo("niczek", "imionko", "Krakow", 1999,
		    false, "test@test.pl", "pass");
	    
	    System.out.println("Wysylam dane do rejestracji...");
	    connection.send(ui.toPacket(Packet.TYPE_REGISTER_REQUEST));
	    
	    connection.receive(p);
	    
	    if (p.getType() == Packet.TYPE_REGISTER_REPLY) {
		if (p.getJID() != 0) {
		    String tekst = new String(p.getContent());
		    System.out.println("Udalo sie zarejestrowac: " + tekst);
		    System.out.println("Twoj numer: " + p.getJID());
		} else {
		    System.out.println("Nie udalo sie zarejestrowac.");
		}
	    } else {
		System.out.println("Blad");
	    }
   
            
            
	} catch (UnknownHostException e) {
	    System.out.println("Unknown host.");
	} catch (IOException e) {
	    System.out.println("IOException");
	} finally {
	    if (connection != null) {
		try {
		    connection.close();
		} catch (IOException e) {
		    
		}
	    }
	}
	

    }
}
