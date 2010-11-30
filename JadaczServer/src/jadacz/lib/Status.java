/**
 *  
 */
package jadacz.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * Class which represents user activity status.
 * 
 * @author 	Szymon 'tecku' Kordyaczny
 * @version     1.0
 * @see Packet
 */
public class Status implements Sendable {

    // constants

    /**
     * User's status is not specified.
     * Setting this type is disallowed.
     */
    public static final byte TYPE_NOT_SPECIFIED = 0;
    /**
     * User is not available.
     */
    public static final byte TYPE_NOT_AVAILABLE = 1;
    /**
     * User is not available, but he left description.
     */
    public static final byte TYPE_NOT_AVAILABLE_DESCR = 2;
    /**
     * User is available.
     */
    public static final byte TYPE_AVAILABLE = 3;
    /**
     * User is available and status has a description.
     */
    public static final byte TYPE_AVAILABLE_DESCR = 4;
    /**
     * User is available, but he's busy right now.
     */
    public static final byte TYPE_BUSY = 5;
    /**
     * User is available, but he's busy right now
     * and status has a description.
     */
    public static final byte TYPE_BUSY_DESCR = 6;
    /**
     * User is available, but another users see this status
     * as not available.
     * This status is sent only by clients, server translate
     * it to <code>TYPE_NOT_AVAILABLE</code>.
     */
    public static final byte TYPE_HIDDEN = 7;
    /**
     * User is available, but another users see this status
     * as not available with description.
     * This status is sent only by clients, server translate
     * it to <code>TYPE_NOT_AVAILABLE_DESCR</code>.
     */
    public static final byte TYPE_HIDDEN_DESCR = 8;
    /**
     * User is online, but he's away.
     */
    public static final byte TYPE_BE_RIGHT_BACK = 9;
    /**
     * User invites for conversation.
     */
    public static final byte TYPE_EAT_ME = 10;
    /**
     * User has blocked recipment of this status info.
     */
    public static final byte TYPE_BLOCKED = 11;
    /**
     * User invites for conversation with description.
     */
    public static final byte TYPE_EAT_ME_DESCR = 12;
    /**
     * User is online, but he's away.
     */
    public static final byte TYPE_BE_RIGHT_BACK_DESCR = 13;
    // attributes

    /**
     * The JID number.
     */
    private int jid = 0;

    /**
     * The type of status.
     */
    private byte type = TYPE_NOT_SPECIFIED;

    /**
     * The description of status.
     */
    private String description = null;

    // constructors

    /**
     * Create empty default status. (<code>Status.TYPE.NOT_AVAILABLE</code>)
     */
    public Status() {
	this.setType(TYPE_NOT_AVAILABLE);
    }

    /**
     * Create status from packet.
     * 
     * @param packet
     */
    public Status(Packet packet) {
	this.loadFromPacket(packet);
    }

    /**
     * Create status from another status.
     * 
     * @param status source status
     */
    public Status(Status status) {
	this.setJID(status.getJID());
	this.setType(status.getType());
	this.setDescription(status.getDescription());
    }

    /**
     * Creates status object with all specified properties.
     * 
     * @param jid the JID number
     * @param type the type of status
     * (one of <code>Status.TYPE_*</code>)
     */
    public Status(int jid, byte type) {
	this(jid, type, null);
    }

    // getters/setters

    /**
     * Creates status object with all specified properties.
     * 
     * @param jid the JID number
     * @param type the type of status
     * (one of <code>Status.TYPE_*</code>)
     * @param description the description to some of status types
     */
    public Status(int jid, byte type, String description) {
        this.setJID(jid);
        this.setType(type);
        this.setDescription(description);
    }

    /**
     * Gets the JID number of status owner.
     * 
     * @return the JID number
     */
    public int getJID() {
	return this.jid;
    }

    /**
     * Sets the JID number of status.
     * 
     * @param jid the JID number to set
     */
    public void setJID(int jid) {
	this.jid = new Integer(jid);
    }

    /**
     * Gets the type of status. Should be one of 
     * <code>Status.TYPE_*</code>.
     * 
     * @return the type
     */
    public byte getType() {
	return this.type;
    }

    /**
     * Sets the type of status. One of <code>Status.TYPE_*</code>.
     * 
     * @param type the type to set
     */
    public void setType(byte type) {
	this.type = new Byte(type);
    }

    /**
     * Gets the description of status owner or <code>null</code>
     * if description is not set.
     * 
     * @return the description
     */
    public String getDescription() {
	if (this.description == null) {
	    return new String("");
	}
	else return new String(this.description);
    }

    /**
     * Sets the description of status. If description is set
     * to <code>null</code> it means that there is no description
     * indeed.
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
	if(description==null) description = new String("");
	this.description = new String(description);
    }

    // methods 

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());

	try {
	    this.setType(istream.readByte());
	    this.setDescription(istream.readUTF());
	} catch (IOException e) {
	    // it should be ok :)
	}
    }

    /** 
     * Gets the packet with default type for this class - 
     * <code>Packet.TYPE_STATUS_CHANGE</code>
     * 
     * @see jadacz.lib.Sendable#toPacket()
     */
    public Packet toPacket() {
	return toPacket(Packet.TYPE_STATUS_CHANGE);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket(byte)
     */
    public Packet toPacket(byte type) {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeByte(this.type);
	    ostream.writeUTF(this.description); 
	} catch (IOException e) {
	    // it should be ok :)
	}

	return new Packet(this.jid, type, buffer.toByteArray());
    }

    /**
     * Allows to write FileInfo object to stream as String.
     */
    public String toString() {
        return new String("Status( jid:"+this.getJID()+
        	" | type:"+this.getType()+" | descr:"+this.getDescription()+" )");
    }
    
    /**
     * Allows to write FileInfo object to stream as String.
     */
    public String toShortString() {
        return new String("Status("+this.getJID()+
        	"|"+this.getType()+"|"+this.getDescription()+")");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /**
     * Checks if current Status object is equal to (has the same values) as the given in param.
     * @param status object to equal with
     * @return true if equal, false if not
     */
    public boolean equals(Status status) {
	if (this.getJID() == status.getJID()
		&& this.getType() == status.getType()
		&& this.getDescription().equals(status.getDescription())
	)
	{
	    return true;
	}
	return false;
    }




}
