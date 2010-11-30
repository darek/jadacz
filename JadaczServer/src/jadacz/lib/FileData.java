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
 * Class which allows sending file data.
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 *
 */
public class FileData implements Sendable {

    /**
     * JID address other user (changed when going threw server).
     */
    private int jid;

    /**
     * Offset inside created file.
     */
    private long offset;

    /**
     * The size of data kept in this object.
     */
    private int dataLength;

    /**
     * The data.
     */
    private byte[] data;

    /**
     * Creates FileData based on other FileData
     * @param fd source FileData
     */
    public FileData(FileData fd) {
	this.setJID(fd.getJID());
	this.setOffset(fd.getOffset());
	this.setDataLength(fd.getDataLength());
	this.setData(fd.getData());
    }

    /**
     * Creates FileData based on packet.
     * 
     * @param packet source packet
     */
    public FileData(Packet packet){
	this.loadFromPacket(packet);
    }

    /**
     * Creates FileData from specified params.
     * 
     * @param jid JID address
     * @param offset Offset inside file
     * @param dataLength length of data in this packet
     * @param data the data
     */
    public FileData(int jid, long offset, int dataLength, byte[] data) {
	this.setJID(jid);
	this.setOffset(offset);
	this.setDataLength(dataLength);
	this.setData(data);
    }

    /**
     * Gets data.
     * 
     * @return the data
     */
    public byte[] getData() {
	byte[] tmp = new byte[this.getDataLength()];
	System.arraycopy(this.data, 0, tmp, 0, this.data.length);
	return tmp;
    }

    /**
     * Sets data.
     * 
     * @param data the data to set
     */
    public void setData(byte[] data) {
	this.dataLength = new Integer (data.length);
	this.data = new byte[this.dataLength];
	System.arraycopy(data, 0, this.data, 0, data.length);
    }

    /**
     * Gets length of data.
     * 
     * @return the dataLength
     */
    public int getDataLength() {
	return new Integer(this.dataLength);
    }

    /**
     * Sets length of data.
     * 
     * @param dataLength the dataLength to set
     */
    public void setDataLength(int dataLength) {
	this.dataLength = new Integer(dataLength);
    }

    /**
     * Gets JID.
     * 
     * @return the jid
     */
    public int getJID() {
	return new Integer(this.jid);
    }

    /**
     * Sets JID.
     * 
     * @param jid the jid to set
     */
    public void setJID(int jid) {
	this.jid = new Integer(jid);
    }

    /**
     * Gets offset.
     * 
     * @return the offset
     */
    public long getOffset() {
	return  new Long(this.offset);
    }

    /**
     * Sets offset.
     * 
     * @param offset the offset to set
     */
    public void setOffset(long offset) {
	this.offset = new Long(offset);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());

	try {
	    this.setOffset(istream.readLong());
	    this.setDataLength(istream.readInt());
	    byte[] tmp = new byte[this.getDataLength()];

	    for(int i = 0 ; i < tmp.length ; ++i)
		tmp[i] = istream.readByte();

	    this.setData(tmp);
	} catch (IOException e) {
//	    it should be ok :)
	}
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket()
     */
    public Packet toPacket() {
	return toPacket(Packet.TYPE_FILE_SEND_DATA);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket(byte)
     */
    public Packet toPacket(byte type) {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeLong(this.offset);
	    ostream.writeInt(this.dataLength);
	    ostream.write(this.data);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(this.jid, type , buffer.toByteArray());
    }

    /**
     * Allows to write FileData object to stream as String.
     */
    public String toString() {
	return new String("FileData( jid:"+this.getJID()+" | offset:"+this.getOffset()+
		" | dataLength:"+this.getDataLength()+" | data:"+this.getData()+" )");
    }
    
    /**
     * Allows to write FileData object to stream as String.
     */
    public String toShortString() {
	return new String("FileData("+this.getJID()+"|"+this.getOffset()+
		"|"+this.getDataLength()+"|"+this.getData()+")");
    }
    
    /**
     * Checks if current FileData object is equal to (has the same values) as the given in param. 
     * @param fd object to equal with
     * @return true if equal, false if not
     */
    public boolean equals(FileData fd) {
	boolean value = true; 
	if (this.getJID() == fd.getJID()
		&& this.getOffset() == fd.getOffset()
		&& this.getDataLength() == fd.getDataLength()
		&& this.getData() != fd.getData()
	)
	{
	    for(int i=0 ; i<this.getDataLength(); ++i)
		if(this.getData()[i] != fd.getData()[i]) value=false;
	    return value;
	}
	else return false;
    }

}
