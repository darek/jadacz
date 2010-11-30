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
 * Object of this type allows us to log in onto server. It contains all needed info.
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 *
 */
public class LoginRequest implements Sendable{

    /**
     * Users JID.
     */
    private int jid;

    /**
     * Jadacz version.
     */
    private float version;

    /**
     * Users password.
     */
    private String password ;

    /**
     * Creates empty LoginRequest.
     */
    public LoginRequest()
    {
        this.setJID(0);
        this.setPassword("");
        this.setVersion(0);
    }

    /**
     * Creates LoginRequest based on packet.
     * 
     * @param packet source packet
     */
    public LoginRequest(Packet packet)
    {
        this.loadFromPacket(packet);
    }

    /**
     * Creates LoginRequest based on other LoginRequest
     * @param lr source LoginRequest
     */
    public LoginRequest(LoginRequest lr)
    {
	this.setJID(lr.getJID());
	this.setPassword(lr.getPassword());
	this.setVersion(lr.getVersion());
    }

    /**
     * Creates LoginRequest based on params.
     * 
     * @param id users JID
     * @param version version of Jadacz
     * @param password user password
     */
    public LoginRequest(int id, float version, String password)
    {
	this.setJID(id);
	this.setPassword(password);
	this.setVersion(version);
    }

    /**
     * Gets JID.
     * 
     * @return jid user jid
     */
    public int getJID()
    { return this.jid; }

    /**
     * Gets version.
     * 
     * @return version version of Jadacz
     */
    public float getVersion() {
	return this.version;
    }

    /**
     * Gets user password.
     * 
     * @return password user password
     */
    public String getPassword() {
	return new String(this.password);
    }

    /**
     * Sets JID.
     * 
     * @param id new JID
     */
    public void setJID(int id)
    { this.jid = id; }

    /**
     * Sets version.
     * 
     * @param version new version
     */
    public void setVersion(float version)
    { this.version = version; }

    /**
     * Sets password.
     * 
     * @param pswd new password
     */
    public void setPassword(String pswd)
    {
	if (pswd != null) {
	    this.password = new String(pswd);
	} else {
	    this.password = new String("");
	}
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public Packet toPacket(byte type)
    {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeFloat(this.version);
	    ostream.writeUTF(this.password);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(this.jid, type, buffer.toByteArray());
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket()
     */
    public Packet toPacket()
    {
	return toPacket(Packet.TYPE_LOGIN_REQUEST);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());

	try {
	    this.setVersion(istream.readFloat());
	    this.setPassword(istream.readUTF());
	} catch (IOException e) {
//	    it should be ok :)
	}
    }

    /**
     * Allows to write LoginRequest object to stream as String.
     */
    public String toString() {
        return new String("LoginRequest( jid:"+this.getJID()+
        	" | version:"+this.getVersion()+" | pass:"+this.getPassword()+" )");
    }
    
    /**
     * Allows to write LoginRequest object to stream as String.
     */
    public String toShortString() {
        return new String("LoginRequest("+this.getJID()+
        	"|"+this.getVersion()+"|"+this.getPassword()+")");
    }

    /**
     * Checks if current LoginRequest object is equal to (has the same values) as the given in param.
     * @param loginInfo object to equal with
     * @return true if equal, false if not
     */
    public boolean equals(LoginRequest loginInfo) {
	if (this.getJID() == loginInfo.getJID()
		&& this.getVersion() == loginInfo.getVersion()
		&& this.getPassword().equals(loginInfo.getPassword())
	)
	{
	    return true;
	}
	else return false;
    }
}
