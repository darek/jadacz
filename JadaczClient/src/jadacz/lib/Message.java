/**
 * 
 */
package jadacz.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.jmx.snmp.Timestamp;

/**
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 *
 */
public class Message implements Sendable {
    
    /**
     * User JID (changed on server, same in FileData
     */
    private int jid;
    
    /**
     * Timestamp of the Message
     */
    private long time;
    
    /**
     * Message content
     */
    private String content;
    
    /**
     * Creates new Message based on another Message
     * @param m source Message
     */
    public Message(Message m)
    {
	Timestamp cos  = new Timestamp();
	this.time = cos.getDateTime();
	this.setJID(m.getJID());
	this.setContent(m.getContent());
    }

    /**
     * Creates empty Message with current system time.
     *
     */
    public Message()
    {
	Timestamp cos  = new Timestamp();
	this.time = cos.getDateTime();
	this.setJID(0);
	this.setContent("");
    }

    /**
     * Creates Message from packet.
     * 
     * @param packet source packet
     */
    public Message(Packet packet)
    {
	this.loadFromPacket(packet);
    }

    /**
     * Creates empty Message for user from param with empty content.
     * 
     * @param jid user JID 
     */
    public Message(int jid)
    {
	Timestamp cos  = new Timestamp();
	this.setTime(cos.getDateTime());
	this.setJID(jid);
	this.setContent("");
    }

    /**
     * Creates Message for user from param with content from param.
     * 
     * @param jid_new
     * @param content
     */
    public Message(int jid_new, String content)
    {
	Timestamp cos  = new Timestamp();
	this.setTime(cos.getDateTime());
	this.setJID(jid_new);
	this.setContent(content);
    }

    /**
     * Gets Message JID.
     * 
     * @return jid
     */
    public int getJID()
    { return new Integer(this.jid); }

    /**
     * Gets Message timestamp.
     * 
     * @return time
     */
    public long getTime()
    { return new Long (time); }

    /**
     * Gets Message content.
     * 
     * @return content
     */
    public String getContent()
    { return new String(content); }

    /**
     * Sets JID.
     * 
     * @param jid_new new jid
     */
    public void setJID(int jid_new)
    { this.jid = new Integer(jid_new); }

    /**
     * Sets time (param is Long!).
     * @param new_time time to set
     */
    public void setTime (long new_time)
    { this.time = new Long(new_time); }

    /**
     * Sets Message content.
     * 
     * @param new_content content to set
     */
    public void setContent(String new_content)
    { this.content = new String(new_content); }
    
    /* (non-Javadoc)
     * @see jadacz.lib.Sendable#loadFromPacket(jadacz.lib.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());
	
	try {
	    this.setTime(istream.readLong());
	    this.setContent(istream.readUTF());
	} catch (IOException e) {
//	    it should be ok :)
	}

    }

    /* (non-Javadoc)
     * @see jadacz.lib.Sendable#toPacket()
     */
    public Packet toPacket() {
	return toPacket(Packet.TYPE_MESSAGE);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket(byte)
     */
    public Packet toPacket(byte type) {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeLong(this.time);
	    ostream.writeUTF(this.content);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(this.jid, type , buffer.toByteArray());
    }

    public boolean equals(Message message) {
	if (this.getJID() == message.getJID()
		&& this.getContent().equals(message.getContent())
	)
	{
	    return true;
	}
	else return false;
    }
}
