/**
 * 
 */
package jadacz;

import jadacz.lib.Connection;
import jadacz.lib.FileData;
import jadacz.lib.FileInfo;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;
import jadacz.mainGUI;
import jadacz.lib.Contact;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mariusz Durkiewicz alias "BakTi"
 *
 */
public class ClientListener implements Runnable {

    /**
     * Connection with server.
     */
    private Connection connection = null;
    /**
     * Temporary socket with connection to server.
     * Socket is a part of Connection and connection object
     * controls socket. 
     */
    private Socket tempSocket = null;
    /**
     * Our GUI Interface object.
     */
    private mainGUI client = null;
    private FileReceiver reader = null;
    
    /**
     * Send message recived from server to the client
     */
    private void proceedMessage(Packet packet) {
        Message m = new Message(packet);
        client.setMessage(m);        
    }
    
    /**
     * Send file info to the client
     */
    private void proceedFileRequest(Packet packet) {
        FileInfo fi = new FileInfo(packet);
        client.setFileRequest(fi);
    }
    
    private void proceedLoginStatus(boolean status) {
        client.setLoginStatus(status);
    }
    
    private void proceedRegister(Packet packet) {
        //client.setRegisteredJID(packet.GetJID());
    }
    
    private void proceedContactInfo(Packet packet) {
        //Contact contact = new Contact(packet); 
        Contact contact = new Contact(packet);
        client.setContact(contact);
    }
    
    private void proceedStatusInfo(Packet packet) {
        Status status = new Status(packet);
        client.setStatusChange(status);
    }
    
    private void proceedContactOperationFaild(Packet packet) {
        client.setContactOperationFailed(packet.getJID(),packet.getContent());        
    }

    private void proceedContactRemoved(Packet packet) {
        client.setContactRemoved(packet.getJID());        
    }
    
    private void proceedFileRequestResponse(boolean responsekind, Packet packet) {
        client.setFileRequestResponse(responsekind, new FileInfo(packet));
    }

    private void proceedFileData(Packet packet) {
        try {
            reader.setPortion(new FileData(packet));
        } catch (NullPointerException npe) {
            System.out.println("NPE w ClientListener - proceedFileData");
        }
    }
    
    private void proccedFileCancel(Packet packet) {
        client.setFileTransferCancel(packet.getJID());
    }
    
    private void proceedFileComplete(Packet packet) {
        try {
            client.setFileTransferComplete(packet.getJID());
            reader.setEnd();
        } catch (NullPointerException npe) {
            System.out.println("NPE w ClientListener - proceedFileComplete");
        }
    }    

    private void proceedWelcome() {
        //client.setWelcome();
    }
    
    /** Close file reading object */
    public void stopReceivingFile() {
        try {
            reader.setEnd();
        } catch (NullPointerException npe) {
            System.out.println("NPE w ClientListener - stopReceivingFile");
        }
    }
    /**
     * Create file receiving object
     */
    public void beginReceivingFile(FileInfo info) {
            reader = new FileReceiver(info, client);
    }
    
    /**
     * Create thread object to listen on specific socket.
     * @param con - socket to listen on
     * @param reciver - object to where all recived data are being sent
     */
    public ClientListener(Connection con, mainGUI reciver) {
	this.client = reciver;
        this.connection = con;
    }
    
    public void run() {	
	try {
            // initialiazes connection from socket
            Packet packet= new Packet();
	    while (true) {
		connection.receive(packet);
		    switch(packet.getType()) {
                        
                        case Packet.TYPE_WELCOME: proceedWelcome(); break;
                        case Packet.TYPE_REGISTER_REPLY: proceedRegister(packet); break;
                        case Packet.TYPE_LOGIN_SUCCEED: proceedLoginStatus(true); break;
                        case Packet.TYPE_LOGIN_FAILED: proceedLoginStatus(false); break;
                        case Packet.TYPE_CONTACT_INFO: proceedContactInfo(packet); break;
                        case Packet.TYPE_STATUS_INFO: proceedStatusInfo(packet); break;
                        case Packet.TYPE_ARCHIVE_MESSAGE:  proceedMessage(packet); break;
                        case Packet.TYPE_MESSAGE:  proceedMessage(packet); break;
                        
                        case Packet.TYPE_CONTACT_REMOVED: proceedContactRemoved(packet); break; 
 //                       case Packet.TYPE_CONTACT_FAILED: proceedContactOperationFaild(packet); break;

                        case Packet.TYPE_STATUS_CHANGE: break;
                        case Packet.TYPE_FILE_SEND_REQUEST: proceedFileRequest(packet); break;
                        case Packet.TYPE_FILE_SEND_ACCEPT: proceedFileRequestResponse(true, packet); break;
                        case Packet.TYPE_FILE_SEND_REJECT: proceedFileRequestResponse(false, packet); break;
                        case Packet.TYPE_FILE_SEND_DATA: proceedFileData(packet); break;
                        case Packet.TYPE_FILE_SEND_CANCEL: proccedFileCancel(packet); break;
                        case Packet.TYPE_FILE_SEND_COMPLETE: proceedFileComplete(packet); break;
		    } // eo switch
	    } // eo while
	} catch (IOException e) {}
    } // eo run()

} // eo class
