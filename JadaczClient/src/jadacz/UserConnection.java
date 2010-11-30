/*
 * UserConection.java
 *
 * Created on 10 luty 2007, 09:21
 *
 */

package jadacz;

import jadacz.lib.FileData;
import jadacz.lib.LoginRequest;
import jadacz.lib.UserInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import jadacz.lib.Connection;
import jadacz.lib.Contact;
import jadacz.lib.Packet;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;
import jadacz.lib.FileInfo;
import java.net.UnknownHostException;

/**
 * Class designed to comunicate GUI with server
 * @author Mariusz Durkiewicz alias "BakTi"
 */
public class UserConnection {
    
    
    private InetAddress serverAddress = null;
    private int connectionPort;
    private Socket socket = null;
    private Connection connection = null;
    private ClientListener listener = null; 
    private mainGUI main = null;
    private FileSender sender = null;
    
    public static final byte ccADD = 0;
    public static final byte ccDELETE = 1;
    public static final byte ccMODIFY = 2;
    
    /**
     * Creates a new instance of UserConection
     * @param address server address
     * @param port number of the port
     * @param reciver Object where all data recived from server will be sent
     */
    public UserConnection(String address, int port, mainGUI reciver) throws UnknownHostException {
        this.serverAddress = InetAddress.getByName(address);
        this.connectionPort = port;
        this.main = reciver;
        try {
            this.socket = new Socket(this.serverAddress, port);
            connection = new Connection(this.socket);
        } catch(IOException e) { 
            System.out.println("UserConnection: Nie stworzono obiektu klasy Connection");
        }
        this.listener = new ClientListener(connection, reciver);
        Thread t = new Thread(listener);
        t.start();
    }    

    /**
     * Send messages
     * @param msg - message to send
     */
    public int sendMessage(Message msg) {
        try {
            connection.send(msg.toPacket());
            return 1;
        } catch (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendMessage");
            return 0;
        } 
    } 
    
    
    /**
     * Sends file info for file request operation
     */
    public int sendFileInfo(FileInfo file) {
        try {
            connection.send(file.toPacket());
            return 1;
        } catch (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendFileInfo");
            return 0;
        }
    }

    /**
     * Send answer on file transfer request to other user
     */
    public int sendFileInfoResponse(byte response, FileInfo info, FileInfo old) {
        try {
            Packet packet = old.toPacket();
            packet.setType(response);
            if (response == Packet.TYPE_FILE_SEND_ACCEPT) {
                listener.beginReceivingFile(info);
            }
            connection.send(packet);
            return 1;
        } catch (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendFileInfoResponse");
            return 0;
        }
    }
    
    /**
     * Begin sending file
     */
    public void startFileSend(FileInfo info) {
        sender = new FileSender(info, this, main);
        Thread t = new Thread(sender);
        t.start();
    }
    
    /** Stops sending file */
    public void stopFileSend(int jid) {
        sender.setEnd();
    }
    
    /** Stop receiving file  */
    public void stopFileRecive() {
        listener.stopReceivingFile();
    }
    
    /**
     * Send portion of file
     */
    public int sendFileData(FileData data) {
        try {
            connection.send(data.toPacket());
            return 1;
        } catch  (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendFileData");
            return 0;
        }
    }
    
    /**
     * Sends information that all file data has beed sended.
     */
    public int sendFileTransferComplete() {
        try {
            connection.send(new Packet(Packet.TYPE_FILE_SEND_COMPLETE));
            return 1;
        } catch (IOException ex) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendFileTransferComplete");
            return 0;
        }
    }
    
    /**
     * Sends information that user resign.
     */
    public int sendFileTransferCancel() {
        try {
            connection.send(new Packet(Packet.TYPE_FILE_SEND_CANCEL));
            return 1;
        } catch (IOException ex) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendFileTransferComplete");
            return 0;
        }
    }
    
    /**
     * Sends Login request to server.
     */
    public int sendLoginRequest(LoginRequest login) {
        try {
            connection.send(login.toPacket());
            return 1;
        } catch  (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendLoginRequest");
            return 0;
        }
    }
    
    /**
     * Sends our status to server.
     */
    public int sendStatus(Status status) {
        try {
            connection.send(status.toPacket());
            return 1;
        } catch  (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendStatus");
            return 0;
        }
    }
 
    /**
     * Sends our status to server.
     */
    public int sendRegisterInfo(UserInfo info) {
        try {
            connection.send(info.toPacket());
            return 1;
        } catch  (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendRegisterInfo");
            return 0;
        }
    }
    
    /**
     * Add, remove or modify contact.
     */
    public int sendContactChange(Contact contact, byte action) {
        try {
            Packet packet = contact.toPacket();
            switch (action) {
                case ccADD: packet.setType(Packet.TYPE_CONTACT_ADD); break;
                case ccDELETE: packet.setType(Packet.TYPE_CONTACT_DELETE); break;
                case ccMODIFY: packet.setType(Packet.TYPE_CONTACT_CHANGE); break;
            }
            connection.send(packet);
            return 1;
        } catch  (IOException e) {
            System.out.println("UserConnection: Nie wyslano pakietu z sendContactChange");
            return 0;
        }
    }
    
    
}
