/**
 * 
 */
package jadacz.test;

import jadacz.lib.Connection;
import jadacz.lib.Contact;
import jadacz.lib.LoginRequest;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Logon and Message test Bot (also can reply to some simple msgs)
 * @author Szymon 'tecku' Kordyaczny
 * @author Pawel 'top' Luczak
 */
public class TestBot {
    
    private static String srv = "topdrive.one.pl";
    private static int port = 8060;
    private static Socket s;
    private static String pass = "pass";
    private static int myJID = 113;
    
    
    private static void sleep(int time) {
	try {
	    Thread.sleep(time);
	} catch (InterruptedException e) {
	}
    }
    
    public static void main(String[] args) {
	Connection con = null;
	try {
	    s = new Socket(srv,port);
	    con = new Connection(s);
	    Packet packet = new Packet();
	    
	    // receive hello
	    con.receive(packet);
	    System.out.println(packet.getType());
	    
	    // send login request
	    LoginRequest logReq = new LoginRequest(myJID,(float) 0.1, pass);
	    con.send(logReq.toPacket(Packet.TYPE_LOGIN_REQUEST));
	    
	    con.receive(packet);
	    if (packet.getType() == Packet.TYPE_LOGIN_SUCCEED) {
		Status s = new Status(0,
			Status.TYPE_AVAILABLE_DESCR,
			"Jestem tylko g³upim botem");
		con.send(s.toPacket(Packet.TYPE_STATUS_CHANGE));
		
		while (true) {
		    Packet p1 = new Packet();
		    con.receive(p1);

		    if (p1.getType() == Packet.TYPE_MESSAGE) {
			Message msg = new Message(p1);
			System.out.println("Msg received: " + msg.getJID()
				+ "|" + msg.getTime() + "|" + msg.getContent());
			
			sleep(2000);
			if ("jestes glupi".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "sam jesteœ g³upi");
			    con.send(msgReply.toPacket());
			}
			if ("co tam?".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "jakos leci");
			    con.send(msgReply.toPacket());
			}
			if ("czeœæ".equals(msg.getContent())
				|| "czesc".equals(msg.getContent())
				|| "cze".equals(msg.getContent())
				|| "hey".equals(msg.getContent())) {
			    Message msgReply = new Message(msg.getJID(),
				    "no czeœæ");
			    con.send(msgReply.toPacket());
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
	    else throw new IOException();
	    
	    
	    
	} catch (UnknownHostException e) {
	    System.out.println(e.toString() + " 1");
	} catch (IOException e) {
	    System.out.println(e.toString() + " 2");
	} finally {
	    if (con != null) {
		try {
		    con.close();
		} catch (IOException e) {
		    
		}
	    }
		
	}
    }
}
