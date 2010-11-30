/**
 * 
 */
package jadacz.test;

import java.io.IOException;

import jadacz.lib.*;


/**
 * @author Szymon 'tecku' Kordyaczny
 *
 */
public class TestClientListener implements Runnable {

    private Connection connection = null;
    
    public TestClientListener(Connection con){
	this.connection = con;
    } 
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
	
	
	try {
	    while (true) {
		Packet packet = new Packet();
		connection.receive(packet);

		switch (packet.getType()) {
		case Packet.TYPE_ARCHIVE_MESSAGE:
		    Message amsg = new Message(packet);
		    System.out.println("Archive"+amsg.toShortString());
		    break;
		    
		case Packet.TYPE_MESSAGE:
		    Message msg = new Message(packet);
		    System.out.println(msg.toShortString());
		    break;

		case Packet.TYPE_STATUS_INFO: // possible logout 
		    Status status = new Status(packet);
		    System.out.println(status.toShortString());
		    break;

		case Packet.TYPE_FILE_SEND_REQUEST:
		    FileInfo fi = new FileInfo(packet);
		    System.out.println(fi.toShortString());
		    break;
		case Packet.TYPE_FILE_SEND_ACCEPT:
		case Packet.TYPE_FILE_SEND_REJECT:
		case Packet.TYPE_FILE_SEND_DATA:
		case Packet.TYPE_FILE_SEND_CANCEL:
		case Packet.TYPE_FILE_SEND_COMPLETE:
		    System.out.println("Packet(TYPE_FILE_SEND_* = "
			    + packet.getType() + ")");
		    break;

		case Packet.TYPE_CONTACT_INFO:
		    Contact contact = new Contact(packet);
		    System.out.println(contact.toShortString());
		    break;
		    
		case Packet.TYPE_REGISTER_INFO:
		    UserInfo userinfo = new UserInfo(packet);
		    System.out.println(userinfo.toShortString());
		    break;

		default:
		    System.out.println("Packet(type = " + packet.getType()
			    + ")");
		    // ignore

		} // eo switch
	    }
	} catch (IOException e) {
	    //System.out.println("IOException in TestClientListener");
	}
    }

}
