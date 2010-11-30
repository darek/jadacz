/**
 * 
 */
package jadacz.server;


import jadacz.lib.Connection;
import jadacz.lib.Contact;
import jadacz.lib.LoginRequest;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;
import jadacz.lib.UserInfo;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is thread which listen user's requests and process
 * them. 
 * 
 * @author	Szymon 'tecku' Kordyaczny
 * @author 	Pawel 'top' Luczak
 * @version	1.0
 */
public class UserServant implements Runnable {

    /**
     * Servant identification number.
     */
    private long servantId;
    
    /**
     * Connection with user.
     */
    private Connection connection = null;
    
    /**
     * Socket of user connection for temporary use.
     * Socket is a part of Connection and connection object
     * controls socket. 
     */
    private Socket tempSocket = null;
    
    /**
     * State flag, if user is loged in.
     */
    private boolean isUserLoged = false;

    /**
     * State flag, user is being loged out.
     */
    private boolean isLogingOut = false;
    
    /**
     * User which this Servant serves
     */
    private User myUser = null;
    
    /**
     * For debug only. If true server sends reports to user. 
     */
    private boolean reportUser = false;

    /**
     * Connection with dataBase (created only while working with db)
     */
    private DBConnection dbCon = null;

    /**
     * Connection with dataBase (created only while working with db)
     */
    private UserContainer userCont = UserContainer.getInstance();
    
    /**
     * Loger for writing errors to error.log file
     */
    private static final Loger log = Loger.getInstance();   
    
    // constructor:
    
    /**
     * Creates thread object to serve user on specified socket.
     * 
     * @param socket the socket of user connection
     * @param servantId servant identification number
     */
    public UserServant(Socket socket, long servantId) throws IOException{
	this.tempSocket = socket;
	this.servantId = servantId;
	// initialiazes connection from socket
	this.connection = new Connection(tempSocket);
    }

    // methods - process*

    /**
     * Authorization process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processAuthorization(Packet packet) throws IOException {

	LoginRequest logReq = new LoginRequest(packet);
	UserInfo dbUser;

	try {
	    dbCon = new DBConnection();
	    dbUser = dbCon.getUserInfo(logReq.getJID());
	} catch(SQLException e) {
	    dbUser=null;
	    connection.send(new Packet(0,Packet.TYPE_LOGIN_FAILED,"SQL ERROR".getBytes()));
	    log.println("Can`t open DB Connection. User.processAuth");	    
	}
	finally {
	    try {
		if(dbCon!=null) dbCon.close();    
	    } catch(IOException e1){
		log.println("Can`t close DB Connection. User.processAuth");
	    }
	}

	if(dbUser!=null) {
	    if(dbUser.getPassword().equals(logReq.getPassword())) // haslo OK
	    {
		User user = new User(logReq.getJID() , connection);
		userCont.insert(user);
		myUser=user;
		connection.send(new Packet(0,
			Packet.TYPE_LOGIN_SUCCEED,
			"OK".getBytes()));
		this.isUserLoged = true;
		report("authorization succeed");
		myUser.sendMyInfoToMe();
		myUser.sendContactsToMe();
		myUser.sendStatusesToMe();
		myUser.sendArchiveMsgsToMe();
		
	    }
	    else { // haslo FAIL
		report("authorization failed for JID = "+logReq.getJID());
		connection.send(new Packet(0,Packet.TYPE_LOGIN_FAILED,"WRONG PASSWORD".getBytes()));
	    }
	}
	else { // dbUser == null
	    report("authorization failed, no such a JID = "+logReq.getJID());
	    connection.send(new Packet(0,Packet.TYPE_LOGIN_FAILED,"NO SUCH JID".getBytes()));
	}
    }

    /**
     * Registration process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processRegistration(Packet packet) throws IOException {
	UserInfo newUser = new UserInfo(packet);
	Packet replyPacket = new Packet(Packet.TYPE_REGISTER_REPLY);
	try {
	    dbCon = new DBConnection();
	    int newJID = dbCon.addUser(newUser);
	    replyPacket.setJID(newJID);
	    replyPacket.setContent("Congratulations!".getBytes());
	    report("new user registered JID = "+ newJID);
	} catch (SQLException e) {
	    replyPacket.setJID(0);
	    replyPacket.setContent("Sorry.. :(".getBytes());
	    report("user registeration failed");
	    log.println("SQLException in Registration");
	}
	finally {
	    try {
		if(dbCon!=null) dbCon.close();    
	    } catch(IOException e1){
		log.println("Can`t close DB Connection. User.processRegistration");
	    }
	}
	connection.send(replyPacket);
    }

    /**
     * Message exchange process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processMessage(Packet packet) throws IOException {
	int dest_jid = packet.getJID();

	if (dest_jid == 0) {
	    // sterowanie serwerem, tylko na czas testow! (chyba :))
	    serverCommands(packet);
	} else {
	    // normalna wiadomosc
	    packet.setJID(myUser.getJID());
	    if (userCont.contains(dest_jid)) {
		// sending msg to logged user
		User destUser = userCont.get(dest_jid);
		try {
		    report("msg from " + myUser.getJID() + " to " + dest_jid);
		    destUser.send(packet);
		    //report("msg from "+myUser.getJID()+" to "+dest_jid+" sent");
		} catch (IOException e) {
		    System.out.println(e.toString());
		}
	    } else {
		// saving msg in db
		Message arch_msg = new Message(packet);
		arch_msg.setJID(myUser.getJID());
		DBConnection dbCon = null;

		try {
		    dbCon = new DBConnection();
		    report("saving msg from " + myUser.getJID() + " to "
			    + dest_jid + " in db");
		    dbCon.addArchiveMessage(dest_jid, arch_msg);
		    //report("msg from "+myUser.getJID()+" to "+dest_jid+" added to db");
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
			if (dbCon != null)
			    dbCon.close();
		    } catch (IOException e1) {
			log.println("Can`t close DB Connection. User.processRegistration");
		    }
		}
	    }
	}
    }

    /**
     * Status change process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processStatusChange(Packet packet) throws IOException {
	Status status = new Status(packet);
	if (status.getType() == Status.TYPE_NOT_AVAILABLE
		|| status.getType() == Status.TYPE_NOT_AVAILABLE_DESCR) {
	    // logoutUser() will soon be executed so there is no status
	    isLogingOut = true;
	    report("user send loging off status "+status.getType());
	} else {
	    report("user changed status to "+status.getType());
	}
	myUser.changeStatus(status);
    }

    /**
     * File send process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processFileSend(Packet packet) throws IOException {
	int dest_jid = packet.getJID();

	if(userCont.contains(dest_jid)) {
	    if (userCont.get(dest_jid).checkJid(myUser.getJID())) {
		packet.setJID(myUser.getJID());
		User destUser = userCont.get(dest_jid);
		try {
		    destUser.send(packet);
		} catch (IOException e) {
		}
	    } else {
		report("file send to " + dest_jid + " failed (not contacted)");
		connection.send(new Packet(0,
			Packet.TYPE_FILE_SEND_REJECT,
			"YOU ARE NOT ON CONTACT LIST OF THIS USER".getBytes()));
	    }
	}
	else {
	    report("file send to " + dest_jid + " failed (user is offline)");
	    connection.send(new Packet(0,
		    Packet.TYPE_FILE_SEND_REJECT,
		    "USER IS OFFLINE".getBytes()));
	}
    }

    /**
     * Contact process.
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processContact(Packet packet) throws IOException {
	Contact contact;
	switch (packet.getType()) {
	
	case (Packet.TYPE_CONTACT_ADD):
	    contact = new Contact(packet);
	    myUser.addContact(contact);
	    // sends contact info
	    myUser.send(contact.toPacket(Packet.TYPE_CONTACT_INFO));
	    
	    // sends status info of changed contact if it's logged in
	    if (userCont.contains(contact.getJID())){
		Status status = userCont.get(contact.getJID()).getStatus();
		status.setJID(contact.getJID()); // for sure
		// sends status info to current user (this user)
		myUser.sendStatus(status);
	    }
	    
	    report("user added contact JID = " + contact.getJID());
	    break;
	
	case (Packet.TYPE_CONTACT_CHANGE):
	    contact = new Contact(packet);
	    myUser.changeContact(contact);
	    
	    // sends contact info
	    myUser.send(contact.toPacket(Packet.TYPE_CONTACT_INFO));
	    
	    // sends status info of changed contact if it's logged in
	    if (userCont.contains(contact.getJID())){
		Status status = userCont.get(contact.getJID()).getStatus();
		status.setJID(contact.getJID()); // for sure
		// sends status info to current user (this user)
		myUser.sendStatus(status);
	    }
	    
	    report("user changed contact JID = " + contact.getJID());
	    break;
	
	case (Packet.TYPE_CONTACT_DELETE):
	    myUser.removeContact(packet.getJID()); // replies in function
	    report("user removed contact JID = " + packet.getJID());
	    break;

	default:
	    // ignore
	}
    }

    /**
     * PingPong process. Sends pong respond. 
     * 
     * @param packet the packet to be processed
     * @throws IOException if an I/O error occurs
     */
    private void processPing(Packet packet) throws IOException {
	connection.send(new Packet(Packet.TYPE_PONG));
	report("replying pong");
    }

    // other methods
    
    /**
     * Use this function to report what servant is doing at the moment.
     * 
     * @param s report info
     */
    private void logoutUser() {
	isUserLoged = false;
	reportUser = false;
	if (myUser != null) {
	    byte statusType = myUser.getStatus().getType();
	    if (statusType != Status.TYPE_NOT_AVAILABLE
		    && statusType != Status.TYPE_NOT_AVAILABLE_DESCR
		    && statusType != Status.TYPE_HIDDEN
		    && statusType != Status.TYPE_HIDDEN_DESCR) {
		myUser.changeStatus(new Status(myUser.getJID(),
			Status.TYPE_NOT_AVAILABLE));
	    }
	    userCont.remove(myUser.getJID());
	}
    }
    
    /**
     * Use this function to report what servant is doing at the moment.
     * 
     * @param s report info
     */
    private void report(String s) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String time = sdf.format(Calendar.getInstance().getTime());
	if (isUserLoged && (myUser!=null)) {
	    System.out.println("["+time+"] UserServant(" + servantId + "|"
		    +myUser.getIP()+"|"+myUser.getJID()+"): " + s); // 
	    if (reportUser) {
		Message report = new Message(0, "Report: " + s);
		
		try {
		    myUser.send(report.toPacket());
		} catch (IOException e) {
		    // ignore
		}
	    }
	} else {
	    String ip = (connection != null) ?
		    connection.getIP() : "not connected"; 
	    System.out.println("["+time+"] UserServant(" + servantId + "|"
		    +ip+"): " + s); //
	}
    }
     
    /**
     * Command execution.
     * 
     * @param packet command packet (Message)
     */
    private void serverCommands(Packet packet) throws IOException {
	Message cmd = new Message(packet);
	Message cmdReply;

	if ("help".equals(cmd.getContent())) {
	    cmdReply = new Message(0,
		    "commands: help | report [on|off] | users");
	} else if ("users".equals(cmd.getContent())) {
	    String usersList;
	    
	    if (userCont.size() > 0)
	    {
		int[] jids = userCont.getJids();
		StringBuffer users = new StringBuffer(4 * jids.length);
		users.append(jids.length + " users found: ");
		for (int i=0 ; i < jids.length ; ++i)
		{
		    users.append(jids[i]+" ");
		}
		usersList = users.toString();
		
	    } else {
		usersList = userCont.size()+" users found";
	    }
	    cmdReply = new Message(0,usersList);
	
	} else if ("report on".equals(cmd.getContent())) {
	    reportUser = true;
	    cmdReply = new Message(0, "reporting is now enabled");
	} else if ("report off".equals(cmd.getContent())) {
	    reportUser = false;
	    cmdReply = new Message(0, "reporting is now disabled");
	} else if ("report".equals(cmd.getContent())) {
	    if (reportUser) {
		cmdReply = new Message(0, "report is on");
	    } else {
		cmdReply = new Message(0, "report is off");
	    }
	} else {
	    cmdReply = new Message(0, "unknown command (use: help)");
	}
	myUser.send(cmdReply.toPacket());

    }
    
    
    // run forest run!

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
	report("started");
	try {
	    // sends welcome packet
	    connection.send(new Packet(0,Packet.TYPE_WELCOME,
		    "Welcome to Jadacz server.".getBytes()));
	    //report("greeting message sent");

	    // creates empty packet to recieve
	    Packet packet= new Packet();
	    while (true) {
		//report("waiting for packet...");
		connection.receive(packet);

/*		report("...packet received" + "(" + packet.getJID()
			+ "|" + packet.getType() + "|"
			+ packet.getContentLength() + ")");
*/
		if (!isUserLoged) {
		    //-- user ISN'T loged in --

		    switch(packet.getType()) {

		    case Packet.TYPE_LOGIN_REQUEST:
			processAuthorization(packet);
			break;

		    case Packet.TYPE_REGISTER_REQUEST:
			processRegistration(packet);
			break;

		    default:
			// ignore
		    }

		} else {
		    //-- user IS loged in --

		    switch(packet.getType()) {

		    case Packet.TYPE_MESSAGE:
			processMessage(packet);
			break;

		    case Packet.TYPE_STATUS_CHANGE: // possible logout 
			processStatusChange(packet);
			break;

		    case Packet.TYPE_FILE_SEND_REQUEST:
		    case Packet.TYPE_FILE_SEND_ACCEPT:
		    case Packet.TYPE_FILE_SEND_REJECT:
		    case Packet.TYPE_FILE_SEND_DATA:
		    case Packet.TYPE_FILE_SEND_CANCEL:
		    case Packet.TYPE_FILE_SEND_COMPLETE:
			processFileSend(packet);
			break;

		    case Packet.TYPE_CONTACT_ADD:
		    case Packet.TYPE_CONTACT_CHANGE:
		    case Packet.TYPE_CONTACT_DELETE:
			processContact(packet);
			break;

		    case Packet.TYPE_PING:
			processPing(packet);
			break;

		    default:
			// ignore

		    } // eo switch   
		} // eo if
		
		if (isLogingOut) {
		    break;
		}
	    } // eo while(true)    
	} catch (IOException e) {
	    report(e.getMessage());
	    //System.out.println(e);
	} finally {
	    if (isUserLoged) {
		report("loging off user");
		logoutUser();
	    }
	    
	    try {
		if (connection != null) {
		    connection.close();
		    connection = null;
		}
	    } catch (IOException e) {
		log.println("IOException when closing connection with user.");
	    }

	}
	report("stoped");
    } // eo run()

}
