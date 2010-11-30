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
 * Class represents information about file to send.
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 *
 */
public class FileInfo implements Sendable {

    private int jid;
    private long filesize;
    private String filename;

    /**
     * Creates empty FileInfo.
     */
    public FileInfo() {
        this.setJID(0);
        this.setFilesize(0);
        this.setFilename("");
    }

    /**
     * Creates FileInfo based on packet.
     * 
     * @param packet source packet
     */
    public FileInfo(Packet packet) {
        this.loadFromPacket(packet);
    }

    /**
     * Creates FileInfo based on other FileInfo.
     * @param fi source FileInfo
     */    
    public FileInfo(FileInfo fi) {
	this.setJID(fi.getJID());
	this.setFilesize(fi.getFilesize());
	this.setFilename(fi.getFilename());
    }

    /**
     * Creates FileInfo based on parameters.
     * 
     * @param jid JID address
     * @param filesize Filesize
     * @param filename Filename
     */

    public FileInfo(int jid, long filesize, String filename) {
	this.setFilename(filename);
	this.setFilesize(filesize);
	this.setJID(jid);
    }

    /**
     * Gets filename.
     * 
     * @return filename
     */
    public String getFilename() {
	return new String(this.filename);
    }

    /**
     * Sets new filename.
     * 
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
	this.filename = new String(filename);
    }

    /**
     * Gets filesize.
     * 
     * @return the filesize
     */
    public long getFilesize() {
	return new Long(this.filesize);
    }

    /**
     * Sets new filesize.
     * 
     * @param filesize the filesize to set
     */
    public void setFilesize(long filesize) {
	this.filesize = new Long(filesize);
    }

    /**
     * Gets JID address.
     * 
     * @return the jid
     */
    public int getJID() {
	return new Integer(this.jid);
    }

    /**
     * Sets JID address.
     * 
     * @param jid the jid to set
     */
    public void setJID(int jid) {
	this.jid = new Integer(jid);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());

	try {
	    this.setFilesize(istream.readLong());
	    this.setFilename(istream.readUTF());
	} catch (IOException e) {
//	    it should be ok :)
	}

    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket()
     */
    public Packet toPacket() {
	return toPacket(Packet.TYPE_FILE_SEND_REQUEST);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket(byte)
     */
    public Packet toPacket(byte type) {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeLong(this.filesize);
	    ostream.writeUTF(this.filename);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(this.jid, type , buffer.toByteArray());
    }

    /**
     * Allows to write FileInfo object to stream as String.
     */
    public String toString() {
        return new String("FileInfo( jid:"+this.getJID()+" | filesize:"+this.getFilesize()+
        	" | filename:"+this.getFilename()+" )");
    }
    
    /**
     * Allows to write FileInfo object to stream as String.
     */
    public String toShortString() {
        return new String("FileInfo("+this.getJID()+"|"+this.getFilesize()+
        	"|"+this.getFilename()+")");
    }

    /**
     * Checks if current FileInfo object is equal to (has the same values) as the given in param.
     * @param fi object to equal with
     * @return true if equal, false if not
     */
    public boolean equals(FileInfo fi) {
	if (this.getJID() == fi.getJID()
		&& this.getFilename().equals(fi.getFilename())
		&& this.getFilesize() == fi.getFilesize()
	)
	{
	    return true;
	}
	else return false;
    }

}
