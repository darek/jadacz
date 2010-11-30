/**
 * 
 */
package jadacz.server;

import jadacz.lib.Connection;
import jadacz.lib.Contact;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;
import jadacz.lib.UserInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class to manage logged user.
 * 
 * @author Pawel 'top' Luczak
 * @author Szymon 'tecku' Kordyaczny
 * @version 1.0
 */
public class User {
    /**
     * Loger for logging errors.
     */
    private static Loger log = Loger.getInstance();

    /**
     * Connection with database.
     */
    private DBConnection dbCon;

    /**
     * Container with all conected users.
     */
    private static UserContainer userCont = UserContainer.getInstance();

    /**
     * ID of user.
     */
    private int jid;

    /**
     * User status.
     */
    private Status status;

    /**
     * List of contacs (onlu Jadacz ID)
     */
    private Set<Integer> contactJids = new TreeSet<Integer>();

    /**
     * Connection to this user.
     */
    private Connection connection = null;

    /**
     * Ip number of the connection.
     */
    private String ip = null;
    
    
    /**
     * Creates User with specified params.  
     * 
     * @param jid user Jadacz ID
     * @param con connection with user
     */
    public User(int jid, Connection con) {
	this.connection = con;
	this.jid = jid;
	// hidden status at start, user should change it after logon
	this.status = new Status(jid, Status.TYPE_HIDDEN);
	// should be called outside the class
	//this.loadContactJidsAndSendToMe();
	this.ip = this.connection.getIP();
    }

    /**
     * Changes contact information. (Contact MUST be in ContactList)
     * 
     * @param contact contact to change
     */
    public void changeContact(Contact contact) {
	if (contactJids.contains(new Integer(contact.getJID()))) {
	    try {
		dbCon = new DBConnection();
		dbCon.updateContact(this.jid, contact);
	    } catch (SQLException e) {
		log.println("Can`t open DB Connection. User.changeContact");
	    } finally {
		try {
		    dbCon.close();
		} catch (IOException e) {
		    log.println("Can`t close DB Connection. User.changeContact");
		}
	    }
	} else
	    try {
		connection.send(new Packet(0,
			Packet.TYPE_CONTACT_OPERATION_FAILED,
			"WRONG JID".getBytes()));
	    } catch (IOException e) {
		log.println(e.toString() + this.jid
			+ " User.changeContact FAIL");
	    }
    }

    /**
     * Adds new Contact to ContactList.
     * 
     * @param contact contact to add
     */
    public void addContact(Contact contact) {
	try {
	    dbCon = new DBConnection();
	    dbCon.addContact(this.jid, contact);
	    contactJids.add(new Integer(contact.getJID()));
	} catch (SQLException e) {
	    log.println("User.addContact");
	}
	finally {
	    try {
		dbCon.close();
	    } catch (IOException e) {
		log.println("Can`t close DB Connection. User.addContact");
	    }
	}
    }

    /**
     * Sends packet to user.
     * 
     * @param packet packet to send
     * @throws IOException thrown if error
     */
    public void send(Packet packet) throws IOException {
	try {
	    this.connection.send(packet);
	} catch (IOException e) {
	    System.out.println("Can`t send packet to user " + this.jid);
	    log.println(e.toString());
	    throw new IOException("Can`t send packet");
	}
    }

    /**
     * Removes contact from ContactList.
     * 
     * @param jid_rem Jadacz ID of contact to be removed
     */
    public void removeContact(int jid_rem) {
	try {
	    dbCon = new DBConnection();
	    dbCon.removeContact(this.jid, jid_rem);
	    contactJids.remove(new Integer(jid_rem));
	} catch (SQLException e) {
	    log.println("User.removeContact");
	    try {
		connection.send(new Packet(0,
			Packet.TYPE_CONTACT_OPERATION_FAILED,
			"FAIL".getBytes()));
	    } catch (IOException e1) {
		log.println(e1.toString()+" "+this.jid+" User.removeContact");
	    }
	}
	finally {
	    try {
		dbCon.close();
	    } catch (IOException e) {
		log.println("Can`t close DB Connection. User.removeContact");
	    }
	}
	try {
	    connection.send(new Packet(0,
		    Packet.TYPE_CONTACT_REMOVED,
		    "OK".getBytes()));
	} catch (IOException e) {
	    log.println(e.toString() +" "+ this.jid +" User.removeContact");
	}
    }

    /**
     * Changes user status.
     * 
     * @param newStatus status to set
     */
    public void changeStatus(Status newStatus){

	status = new Status(newStatus);
	status.setJID(this.jid);
	
	Iterator<Integer> it = contactJids.iterator();
	int jid;
	while (it.hasNext()) {
	    jid = it.next();
	    if (userCont.contains(jid)
		    && userCont.get(jid).checkJid(status.getJID())) {
		userCont.get(jid).sendStatus(status);
	    }
	}
    }

    /**
     * Checks if User has some Jadacz ID in his ContactList.
     * 
     * @param sJid Jadacz ID to check
     * @return Whether the asked Jadacz ID is in ContactList of User
     */
    public boolean checkJid(int sJid) {
	return contactJids.contains(new Integer(sJid));
    }

    /**
     * Gets Jadacz ID of this user.
     * @return Jadacz ID of user.
     */
    public int getJID() {
	return this.jid;
    }
    
    /**
     * Gets status of this user.
     * @return user's status.
     */
    public Status getStatus() {
	return new Status(this.status);
    }
    
    /**
     * Gets user ip number.
     * 
     * @return ip number as String
     */
    public String getIP() {
	return this.ip;
    }
    
    /**
     * Sends Contact List to user and saves it on this object for status
     * notification feature.
     */
    public void sendContactsToMe() {
	Contact[] contacts;
	try {
	    dbCon = new DBConnection();
	    contacts = dbCon.getContacts(this.jid);
	} catch (SQLException e1) {
	    contacts = null;
	    log.println("Can`t open DB Connection. User.loadContactJids");
	}
	finally {
	    try{
		dbCon.close();
	    } catch (IOException e) {
		log.println("Can`t close DB Connection. User.loadContactJids");
	    }
	}

	if(contacts != null) {
	    // user have some contacts
	    Packet packet;

	    for(int i=0 ; i < contacts.length ; ++i) {
		// adds contact jid to contactJids Set
		contactJids.add(new Integer(contacts[i].getJID()));
		packet = contacts[i].toPacket(Packet.TYPE_CONTACT_INFO);
		
		try {
		    // sends contact info to this user
		    connection.send(packet);
		} catch (IOException e) {
		    log.println(e.toString());
		    log.println("couldn`t send contact("
			    +contacts[i].getJID()+") to "+this.jid);
		}
	    }
	}
    }
    
    
    /**
     * Sends messages from Message archive to user (after LogOn).
     */
    public void sendArchiveMsgsToMe() {
	Message[] msgArray = null;
	try {
	    dbCon = new DBConnection();
	    msgArray = dbCon.getArchiveMessages(this.jid);
	    dbCon.removeArchiveMessages(this.jid);
	} catch (SQLException e) {
	    log.println("sendArchiveMsgsToMe(): SQLException: "+e.getMessage());
	} finally {
	    try {
		dbCon.close();
	    } catch (IOException e) {
		log.println("Can`t close DB Connection. User.sendArchMsg");
	    }
	}

	if(msgArray != null) {
	    for(int i = 0 ; i < msgArray.length ; ++i) {
		try {
		    connection.send(msgArray[i].toPacket(
			    Packet.TYPE_ARCHIVE_MESSAGE));
		} catch (IOException e) {
		    log.println(e.toString() + "User.sendArchMsg sending");
		}
	    }
	}

    }
    
    /**
     * Sends status info about some user to this user.
     * 
     * @param status status to send
     */
    public void sendStatus(Status status){
	if (status.getType() == Status.TYPE_HIDDEN) {
	    status.setType(Status.TYPE_NOT_AVAILABLE);
	}
	if (status.getType() == Status.TYPE_HIDDEN_DESCR) {
	    status.setType(Status.TYPE_NOT_AVAILABLE_DESCR);
	}

	try {
	    connection.send(status.toPacket(Packet.TYPE_STATUS_INFO));
	} catch (IOException e) {
	    // ignore
	}
    }
    
    /**
     * Sends statuses of my contact jids.
     */
    public void sendStatusesToMe(){
	Iterator<Integer> it = contactJids.iterator();
	int jid;
	while(it.hasNext()) {
	    jid = it.next();
	    // send status only if user is logged in
	    if (userCont.contains(jid)
		    && userCont.get(jid).checkJid(this.jid) ) {
		this.sendStatus(userCont.get(jid).getStatus());
	    }
	}
    }
    
    /**
     * Sends user's info to him.
     */
    public void sendMyInfoToMe() {
	UserInfo myInfo = null;
	try {
	    dbCon = new DBConnection();
	    myInfo = dbCon.getUserInfo(this.jid);
	} catch (SQLException e) {
	    log.println("User.sendMyInfoToMe");
	} finally {
	    try {
		dbCon.close();
	    } catch (IOException e) {
		log.println("Can`t close DB Connection. User.sendMyInfoToMe");
	    }
	}
	
	try {
	    myInfo.setPassword("");
	    connection.send(myInfo.toPacket(Packet.TYPE_REGISTER_INFO));
	} catch (IOException e) {
	    // ignore
	}
    }
    

}
