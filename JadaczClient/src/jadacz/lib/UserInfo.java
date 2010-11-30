package jadacz.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Pawel 'top' Luczak
 * @version 0.1 Beta
 *
 */
public class UserInfo implements Sendable {


    /**
     * User Nickname
     */
    private String nick;

    /**
     * User Name
     */
    private String name;

    /**
     * City in which the user lives
     */
    private String city;

    /**
     * The year in which the user was born
     */
    private int birthYear;

    /**
     * Users gender
     */
    private boolean female;

    /**
     * Users e-mail
     */
    private String email;

    /**
     * Users Password
     */
    private String password;
    
    /**
     * Creates new UserInfo based on another UserInfo.
     * @param ui source UserInfo
     */
    public UserInfo(UserInfo ui) {
	this.setNick(ui.getNick());
	this.setBirthYear(ui.getBirthYear());
	this.setCity(ui.getCity());
	this.setEmail(ui.getEmail());
	this.setFemale(ui.isFemale());
	this.setName(ui.getName());
	this.setNick(ui.getNick());
	this.setPassword(ui.getPassword());
    }

    /**
     * Creates UserInfo with all specified parameters.
     * 
     * @param nick Users nick
     * @param name Users name
     * @param city Users city
     * @param birthYear Year in which the user was born
     * @param female Users Gender (true is female)
     * @param email Users e-mail
     * @param password Users password
     */
    public UserInfo(String nick, String name, String city, int birthYear, boolean female, String email, String password) {
	this.setNick(nick);
	this.setBirthYear(birthYear);
	this.setCity(city);
	this.setEmail(email);
	this.setFemale(female);
	this.setName(name);
	this.setNick(nick);
	this.setPassword(password);
    }

    /**
     * Creates empty UserInfo.
     */
    public UserInfo() {
	this.setNick("");
	this.setBirthYear(0);
	this.setCity("");
	this.setEmail("");
	this.setFemale(false);
	this.setName("");
	this.setNick("");
	this.setPassword("");
    }

    /**
     * Creates UserInfo based on a received packet.
     * 
     * @param packet source for data
     */
    public UserInfo(Packet packet) {
	this.loadFromPacket(packet);
    }



    /**
     * Gets users year of birth.
     * 
     * @return the birth year
     */
    public int getBirthYear() {
	return new Integer(this.birthYear);
    }

    /**
     * Sets users year of birth.
     * 
     * @param birthYear new year of birth
     */
    public void setBirthYear(int birthYear) {
	this.birthYear = new Integer(birthYear);
    }

    /**
     * Gets the city in which the user lives.
     * 
     * @return users city
     */
    public String getCity() {
	return new String(this.city);
    }

    /**
     * Sets new city for user.
     * 
     * @param city the city to set
     */
    public void setCity(String city) {
	this.city = new String(city);
    }

    /**
     * Gets users e-mail address.
     * 
     * @return users e-mail
     */
    public String getEmail() {
	return new String(this.email);
    }

    /**
     * Sets new e-mail for user.
     * 
     * @param email new e-mail address
     */
    public void setEmail(String email) {
	this.email = new String(email);
    }

    /**
     * Checks what is the gender of user.
     * 
     * @return is female?
     */
    public boolean isFemale() {
	return new Boolean(this.female);
    }

    /**
     * Sets new gender for user.
     * 
     * @param female new gender to set (true is female)
     */
    public void setFemale(boolean female) {
	this.female = new Boolean(female);
    }

    /**
     * Gets user name.
     * 
     * @return user name
     */
    public String getName() {
	return new String(this.name);
    }

    /**
     * Sets new user name
     * 
     * @param name new user name
     */
    public void setName(String name) {
	this.name = new String(name);
    }

    /**
     * Gets user nick.
     * 
     * @return user nick
     */
    public String getNick() {
	return new String(this.nick);
    }

    /**
     * Sets new user nickname
     * 
     * @param nick new nickname
     */
    public void setNick(String nick) {
	this.nick = new String(nick);
    }

    /**
     * Gets user password.
     * 
     * @return the password
     */
    public String getPassword() {
	return new String(this.password);
    }

    /**
     * Sets new user password.
     * 
     * @param password new password
     */
    public void setPassword(String password) {
	this.password = new String(password);
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#loadFromPacket(jadaczserver.Packet)
     */
    public void loadFromPacket(Packet packet) {
	ByteArrayInputStream buffer = new ByteArrayInputStream(packet.getContent());
	DataInputStream istream = new DataInputStream(buffer);

	try {
	    this.setNick(istream.readUTF());
	    this.setName(istream.readUTF());
	    this.setCity(istream.readUTF());
	    this.setBirthYear(istream.readInt());
	    this.setEmail(istream.readUTF());
	    this.setPassword(istream.readUTF());
	    this.setFemale(istream.readBoolean());
	} catch (IOException e) {
//	    it should be ok :)
	}
    }

    /* (non-Javadoc)
     * @see jadaczserver.Sendable#toPacket()
     */
    public Packet toPacket() {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DataOutputStream ostream = new DataOutputStream(buffer);

	try {
	    ostream.writeUTF(this.nick);
	    ostream.writeUTF(this.name);
	    ostream.writeUTF(this.city);
	    ostream.writeInt(this.birthYear);
	    ostream.writeUTF(this.email);
	    ostream.writeUTF(this.password);
	    ostream.writeBoolean(this.female);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(0, Packet.TYPE_REGISTER_REQUEST , buffer.toByteArray());
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
	    ostream.writeUTF(this.email);
	    ostream.writeUTF(this.password);
	    ostream.writeBoolean(this.female);
	} catch (IOException e) {
//	    it should be ok :)
	}
	return new Packet(0, type , buffer.toByteArray());
    }

    public boolean equals(UserInfo userInfo) {
	if (this.getBirthYear() == userInfo.getBirthYear()
		&& this.isFemale() == userInfo.isFemale()
		&& this.getPassword().equals(userInfo.getPassword())
		&& this.getNick().equals(userInfo.getNick())
		&& this.getName().equals(userInfo.getName())
		&& this.getCity().equals(userInfo.getCity())
		&& this.getEmail().equals(userInfo.getEmail())
	)
	{
	    return true;
	}
	else return false;
    }
}
