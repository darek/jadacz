/**
 * 
 */
package jadacz.test;

import jadacz.lib.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Szymon 'tecku' Kordyaczny
 *
 */
public class TestClient {
    
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
	    System.out.println("Connected: "+new String(p.getContent()));
	    
	    
	    for (int i = 1; i <= tryCount; ++i) {
		System.out.print("Enter your JID number: ");
		input = in.readLine();
		myJID = Integer.parseInt(input);

		System.out.print("Enter your password: ");
		input = in.readLine();
		pass = new String(input);

		// send login request
		LoginRequest logReq = new LoginRequest(myJID,(float)0.1,pass);
		connection.send(logReq.toPacket(Packet.TYPE_LOGIN_REQUEST));
		
		connection.receive(p);
		if (p.getType() == Packet.TYPE_LOGIN_SUCCEED){
		    logged = true;
		    break;
		} else if (p.getType() == Packet.TYPE_LOGIN_FAILED) {
		    System.out.print("Login failed: ");
		    System.out.println(new String(p.getContent()));
		    if (i < tryCount){
			System.out.println("Please try again...");
		    } else {
			System.out.println("You forgot your password. Bye.");
		    }
		}	    
	    }
            if (!logged) {
        	System.exit(1);
            }
            
            System.out.println("Login succeed.");
            Thread t = new Thread(new TestClientListener(connection));
            t.start();
           
	    while(true){
		sleep(1000);
	        System.out.print(myJID+"@jadacz# ");

	        String line = in.readLine();

	        String[] cmdargs = line.split(" ");
	        String cmd = cmdargs[0];

	        if ("".equals(cmd)){
	            // do nothing
	        }else if ("help".equals(cmd)){
	            // help
	            System.out.println("Commands:");
	            System.out.println("msg <jid> <message_text>");
	            System.out.println("addcontact <jid> <nick>");
	            System.out.println("help");
	            System.out.println("exit");
	        } else if ("msg".equals(cmd)){
	            
	            
	            // message send
	            if (cmdargs.length > 2){
	        	StringBuffer text = new StringBuffer(cmdargs[2]);
	        	for (int i=3 ; i < cmdargs.length ; ++i){
	        	    text.append(" "+cmdargs[i]); 
	        	}
	        	Message msg = new Message(
	        		Integer.parseInt(cmdargs[1]),
	        		text.toString());
	        	connection.send(msg.toPacket());
	        	System.out.println("Message sent to "+cmdargs[1]);
	            } else {
	        	System.out.println("use: msg <jid> <message_text>");
	            }
	            
	                 
	        } else if ("addcontact".equals(cmd)){
	            
	            
	            // add contact
	            if (cmdargs.length > 2){
	        	StringBuffer text = new StringBuffer(cmdargs[2]);
	        	for (int i=3 ; i < cmdargs.length ; ++i){
	        	    text.append(" "+cmdargs[i]); 
	        	}
	        	Contact contact = new Contact(
	        		Integer.parseInt(cmdargs[1]),
	        		text.toString());
	        	connection.send(contact.toPacket(Packet.TYPE_CONTACT_ADD));
	        	System.out.println("Contact added: "+cmdargs[1]+" "+text);
	            } else {
	        	System.out.println("use: addcontact <jid> <nick>");
	            }
	            
	            
	        } else if ("exit".equals(cmd) || "quit".equals(cmd)){
	           
	            // exit
	            System.out.println("End.");
	            break;
	            
	        } else {
	            System.out.println(
	        	    "Unknown command '"+cmd
	        	    +"'. Use 'help' to see the list of commands.");
	        }
	        

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
	

	
	/*Connection connection = null;
	try {
	    
	    
	    
	    
	    
	    
	    // receive hello
	    connection.receive(packet);
	    System.out.println(packet.getType());
	    
	    // send login request
	    LoginRequest logReq = new LoginRequest(myJID,(float) 0.1, pass);
	    connection.send(logReq.toPacket(Packet.TYPE_LOGIN_REQUEST));
	    
	    connection.receive(packet);
	    if (packet.getType() == Packet.TYPE_LOGIN_SUCCEED) {
		Status s = new Status(0,
			Status.TYPE_AVAILABLE_DESCR,
			"Jestem tylko g³upim botem");
		connection.send(s.toPacket(Packet.TYPE_STATUS_CHANGE));
		
		while (true) {
		    Packet p1 = new Packet();
		    connection.receive(p1);

		    if (p1.getType() == Packet.TYPE_MESSAGE) {
			Message msg = new Message(p1);
			System.out.println("Msg received: " + msg.getJID()
				+ "|" + msg.getTime() + "|" + msg.getContent());
			
			sleep(2000);
			if ("jestes glupi".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "sam jesteœ g³upi");
			    connection.send(msgReply.toPacket());
			}
			if ("co tam?".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "jakos leci");
			    connection.send(msgReply.toPacket());
			}
			if ("czeœæ".equals(msg.getContent())
				|| "czesc".equals(msg.getContent())
				|| "cze".equals(msg.getContent())
				|| "hey".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "no czeœæ");
			    connection.send(msgReply.toPacket());
			}
		    }
		    
		    if (p1.getType() == Packet.TYPE_ARCHIVE_MESSAGE) {
			Message msg = new Message(p1);
			System.out.println("ArchMsg received: " + msg.getJID()
				+ "|" + msg.getTime() + "|" + msg.getContent());
		    }
		    
		    if (p1.getType() == Packet.TYPE_CONTACT_INFO) {
			Contact contact = new Contact(p1);
			System.out.println("Contact received: "
				+ contact.getJID() + "  nick:"
				+ contact.getNick());
		    }
		    
		    if (p1.getType() == Packet.TYPE_STATUS_INFO) {
			Status status = new Status(p1);
			System.out.println("Status received: "
				+ status.getJID() + "  type: "
				+ status.getType());
		    }

		}
	    }
	    
	    
	    

		
	}*/
    }
}
