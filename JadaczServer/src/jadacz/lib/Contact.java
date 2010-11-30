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
 * Contact is a class for holding a Contact Object.
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 * 
 */
public class Contact implements Sendable {

    /**
     * Contacts jadacz id
     */
    private int jid;

    /**
     * Contacts nickname
     */
    private String nick;

    /**
     * Contacts name
     */
    private String name;

    /**
     * The city in which contact lives
     */
    private String city;

    /**
     * Contacts year of birth
     */
    private int birthYear;

    /**
     * Contacts gender
     */
    private boolean female;

    /**
     * Contacts status
     */
    private Status status;

    /**
     * Creates empty contact.
     */
    public Contact() {
        this.setNick("");
        this.setBirthYear(0);
        this.setCity("");
        this.setJID(0);
        this.setFemale(false);
        this.setName("");
        this.setStatus(new Status());
    }

    /**
     * Creates Contact from Packet.
     * 
     * @param packet
     */
    public Contact(Packet packet) {
	this.loadFromPacket(packet);
	this.setStatus(new Status());
    }

    /**
     * Creates contact from another one.
     * 
     * @param contact the source contact
     */
    public Contact(Contact contact) {
	this.setNick(contact.getNick());
	this.setBirthYear(contact.getBirthYear());
	this.setCity(contact.getCity());
	this.setJID(contact.getJID());
	this.setFemale(contact.isFemale());
	this.setName(contact.getName());
	this.setStatus(contact.getStatus());
    }

    /**
     * 
     * @param jid contact id
     * @param nick contact nick
     */
    public Contact(int jid,String nick){
        this.setNick(nick);
        this.setBirthYear(1900);
        this.setCity("");
        this.setJID(jid);
        this.setFemale(false);
        this.setName("");
        this.setStatus(new Status());
    }

    /**
     * @param jid
     * @param nick
     * @param name
     * @param city
     * @param birthYear
     * @param female
     */
    public Contact(int jid, String nick, String name, String city, int birthYear, boolean female) {
	this.setNick(nick);
	this.setBirthYear(birthYear);
	this.setCity(city);
	this.setJID(jid);
	this.setFemale(female);
	this.setName(name);
	this.setStatus(new Status());
    }

    /**
     * Gets the JID number of Contact.
     * 
     * @return the JID number
     */
    public int getJID() {
	return this.jid;
    }

    /**
     * Sets the JID number of Contact.
     * 
     * @param jid the JID number to set
     */
    public void setJID(int jid) {
	this.jid = new Integer(jid);
    }

    /**
     * Gets nick of contact.
     * @return the nick
     */
    public String getNick() {
	return new String(this.nick);
    }

    /**
     * Sets nick of contact.
     * @param nick the nick to set
     */
    public void setNick(String nick) {
	this.nick = new String(nick);
    }

    /**
     * Checks whether contact is female.
     * @return the female
     */
    public boolean isFemale() {
	return new Boolean(this.female);
    }

    /**
     * Sets gender of contact (true is female).
     * @param female the female to set
     */
    public void setFemale(boolean female) {
	this.female = new Boolean(female);
    }

    /**
     * Gets contact name.
     * @return the name
     */
    public String getName() {
	return new String(this.name);
    }

    /**
     * Sets new name for contact.
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = new String(name);
    }

    /**
     * Gets contacts birthyear
     * @return Birthyear od contact
     */
    public int getBirthYear() {
	return new Integer(this.birthYear);
    }

    /**
     * @param birthYear the birthYear to set
     */
    public void setBirthYear(int birthYear) {
	this.birthYear = new Integer(birthYear);
    }

    /**
     * Gets contact city.
     * @return the city
     */
    public String getCity() {
	return new String(this.city);
    }

    /**
     * Sets new contact city.
     * @param city the city to set
     */
    public void setCity(String city) {
	this.city = new String(city);
    }

    /**
     * Gets user status.
     * @return User status
     */
    public Status getStatus() {
        return new Status(this.status);
    }

    /**
     * Sets new status of contact.
     * @param status
     */
    public void setStatus(Status status) {
	this.status = new Status(status);
    }

    /*
     * (non-Javadoc)
     * 
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	this.setJID(packet.getJID());

	try {
	    this.setNick(istream.readUTF());
	    this.setName(istream.readUTF());
	    this.setCity(istream.readUTF());
	    this.setBirthYear(istream.readInt());
	    this.setFemale(istream.readBoolean());
	} catch (IOException e) {
//	    it should be ok :)
	}
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket()
     */
    public Packet toPacket() {
	return toPacket(Packet.TYPE_CONTACT_INFO);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket(byte)
     */
    public Packet toPacket(byte type) {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeUTF(this.nick);
	    ostream.writeUTF(this.name);
	    ostream.writeUTF(this.city);
	    ostream.writeInt(this.birthYear);
	    ostream.writeBoolean(this.female);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(this.jid, type , buffer.toByteArray());
    }

    /**
     * Allows to write Contact object to stream as String.
     */
    public String toString() {
        return new String("Contact( jid:"+this.getJID()+
        	" | nick:"+this.getNick()+" | name:"+this.getName()+
        	" | city:"+this.getCity()+" | birthYear:"+this.getBirthYear()+
        	" | gender:"+(this.isFemale()?"female":"male")+" | status:"+this.getStatus().toString()+" )");
    }
    
    /**
     * Allows to write Contact object to stream as String.
     */
    public String toShortString() {
        return new String("Contact("+this.getJID()+
        	"|"+this.getNick()+"|"+this.getName()+
        	"|"+this.getCity()+"|"+this.getBirthYear()+
        	"|"+(this.isFemale()?"female":"male")+")");
    }
    
    /**
     * Checks if current contact is equal to (has the same values) as the given in param.
     * @param contact
     * @return true if equals, false if not
     */
    public boolean equals(Contact contact) {
	if (this.getBirthYear() == contact.getBirthYear()
		&& this.isFemale() == contact.isFemale()
		&& this.getNick().equals(contact.getNick())
		&& this.getName().equals(contact.getName())
		&& this.getCity().equals(contact.getCity())
		&& this.getJID() == contact.getJID()
	)
	{
	    return true;
	}
	else return false;
    }

}
