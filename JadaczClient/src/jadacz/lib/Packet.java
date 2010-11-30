/**
 * 
 */
package jadacz.lib;

/**
 * Packet is a class used to transfer any data between clients
 * and server. Every kind of data should be serialized to Packet
 * before sending.
 * 
 * @author 	Szymon 'tecku' Kordyaczny
 * @version     1.0
 */
public class Packet {

    // constants
    
    /**
     * Type is not specified.
     */
    public static final byte TYPE_NOT_SPECIFIED= 0;
    /**
     * Greeting.
     */
    public static final byte TYPE_WELCOME= 1;
    /**
     * Authorization request.
     */
    public static final byte TYPE_LOGIN_REQUEST= 2;
    /**
     * Authorization succeed.
     */
    public static final byte TYPE_LOGIN_SUCCEED= 3;
    /**
     * Authorization failed.
     */
    public static final byte TYPE_LOGIN_FAILED= 4;
    /**
     * Text message.
     */
    public static final byte TYPE_MESSAGE= 5;
    /**
     * Archive message (sent when recipment were offline).
     */
    public static final byte TYPE_ARCHIVE_MESSAGE= 6;
    /**
     * Status change request.
     */
    public static final byte TYPE_STATUS_CHANGE= 7;
    /**
     * Status information.
     */
    public static final byte TYPE_STATUS_INFO= 8;
    /**
     * User registration request.
     */
    public static final byte TYPE_REGISTER_REQUEST= 9;
    /**
     * Reply to user registration request.
     */
    public static final byte TYPE_REGISTER_REPLY= 10;
    /**
     * Change user registration info.
     */
    public static final byte TYPE_REGISTER_CHANGE= 11;
    /**
     * User registration info.
     */
    public static final byte TYPE_REGISTER_INFO= 12;
    /**
     * File send request.
     */
    public static final byte TYPE_FILE_SEND_REQUEST= 13;
    /**
     * File send acceptation.
     */
    public static final byte TYPE_FILE_SEND_ACCEPT= 14;
    /**
     * File send rejection.
     */
    public static final byte TYPE_FILE_SEND_REJECT= 15;
    /**
     * File send data.
     */
    public static final byte TYPE_FILE_SEND_DATA= 16;
    /**
     * File send cancellation.
     */
    public static final byte TYPE_FILE_SEND_CANCEL= 17;
    /**
     * File send complete notification.
     */
    public static final byte TYPE_FILE_SEND_COMPLETE= 18;
    /**
     * Contact information.
     */
    public static final byte TYPE_CONTACT_INFO= 19;
    /**
     * Contact addition.
     */
    public static final byte TYPE_CONTACT_ADD= 20;
    /**
     * Contact modification.
     */
    public static final byte TYPE_CONTACT_CHANGE= 21;
    /**
     * Contact anihilation :).
     */
    public static final byte TYPE_CONTACT_DELETE= 22;
    /**
     * Contact has been removed.
     */
    public static final byte TYPE_CONTACT_REMOVED= 23;
    /**
     * Contact operation failed.
     */
    public static final byte TYPE_CONTACT_OPERATION_FAILED= 24;
    /**
     * Ping.
     */
    public static final byte TYPE_PING= 25;
    /**
     * Pong - reply to ping.
     */
    public static final byte TYPE_PONG= 26;
    /**
     * User whants get password to his email.
     */
    public static final byte TYPE_PASSWORD_REMIND= 27;
    
    // attributes
    
    /**
     * JID number.
     */
    private int jid= 0;
    /**
     * Type of packet.
     */
    private byte type= Packet.TYPE_NOT_SPECIFIED;
    /**
     * Length of content array.
     */
    private int contentLength= 0;
    /**
     * Byte array with serialized data.
     */
    private byte[] content= null;
    
    /**
     * Creates empty packet.
     */
    
    // constructors
    
    public Packet() {
    }

    /**
     * Creates packet of specified type.
     * 
     * @param type Type of packet.
     */
    
    public Packet(byte type) {
	this.type = type;
    }

    /**
     * Creates packet of specified type and both (destination, source)
     * JID numbers.
     * 
     * @param jid_dest JID destination number.
     * @param type Type of packet.
     */
    public Packet(int jid_dest, byte type) {
	this.jid = jid_dest;
	this.type = type;
    }

    /**
     * Creates packet of specified type, both (destination, source)
     * JID numbers and data content from byte array. 
     * 
     * @param jid_dest JID destination number.
     * @param type Type of packet.
     * @param content Byte array with serialized data.
     */
    public Packet(int jid_dest, byte type, byte[] content) {
	this.jid = jid_dest;
	this.type = type;
	setContent(content);
    }

    /**
     * @return the content of packet
     */
    public byte[] getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
	this.contentLength = content.length;
	this.content = new byte[this.contentLength];
	System.arraycopy(content, 0, this.content, 0, content.length);
    }

    /**
     * @return the length of packet's content
     */
    public int getContentLength() {
        return this.contentLength;
    }

    /**
     * @return the JID number of packet
     */
    public int getJID() {
        return this.jid;
    }

    /**
     * @param jid_dest the JID destination number to set
     */
    public void setJID(int jid_dest) {
        this.jid = jid_dest;
    }

    /**
     * @return the type of packet
     */
    public byte getType() {
        return this.type;
    }

    /**
     * @param type the type of packet to set
     */
    public void setType(byte type) {
        this.type = type;
    }
    
    
}
