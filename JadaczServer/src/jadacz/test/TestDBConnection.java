/**
 * 
 */
package jadacz.test;

import jadacz.lib.Contact;
import jadacz.lib.Message;
import jadacz.server.DBConnection;

import java.io.IOException;
import java.sql.SQLException;
/**
 * Test connection with database
 * @author Pawel 'top' Luczak
 */
public class TestDBConnection {


    public static void main(String[] args){
	DBConnection dbCon = null;
	
	// change database before launch!
	String dbUrl = "jdbc:mysql://91.90.179.161:3306/jadacz";
	String dbUser = "jadacz";
	String dbPass = "jadka";
	String jdbcDrv = "com.mysql.jdbc.Driver";
	try {
	    DBConnection.init(dbUrl, dbUser, dbPass, jdbcDrv);
	    System.out.println("Initializing DataBase connector... done");
	} catch (ClassNotFoundException e1) {
	    System.out.println("Initializing DataBase connector... failed");
	    System.exit(0);
	} catch (SQLException e2) {
	    System.out.println("Initializing DataBase connector... failed");
	    System.exit(0);
	}

	Contact c1 = new Contact(1,"nick1","name1","city1",1900,true);
	Contact c2 = new Contact(2,"nick2","name2","city2",1900,false);
	Contact[] cA=null;
	Contact[] cB=null;

	Message m1 = new Message(100,"m1");
	Message m2 = new Message(100,"m2");
	Message[] mA = null;
	Message[] mB = null;


	try {
	    dbCon = new DBConnection();
	    dbCon.addContact(100, c1);
	    dbCon.addContact(100, c2);
	    cA=dbCon.getContacts(100);
	    dbCon.removeContact(100, 1);
	    dbCon.removeContact(100, 2);
	    cB=dbCon.getContacts(100);

	    dbCon.addArchiveMessage(100, m1);
	    dbCon.addArchiveMessage(100, m2);
	    mA = dbCon.getArchiveMessages(100);
	    dbCon.removeArchiveMessages(100);
	    mB = dbCon.getArchiveMessages(100);

	} catch (SQLException e) {
	    System.err.println(e.toString());
	    dbCon=null;
	}

	finally {
	    try {
		if(dbCon!=null) dbCon.close();
	    } catch (IOException e) {
		System.out.println("Can`t close dbCon");
	    }
	}

	if(cA.length == 2) {
	    if(cA[1].equals(c2) && cA[0].equals(c1)) 
		System.out.println("Contacts add: OK!");
	    else {
		System.out.println("Contacts add: FAIL! bad Contact up or down");
		System.out.print("ContactA1: " + cA[0].getBirthYear() + " " +cA[0].getCity() + " ");
		System.out.println(cA[0].getName() + " " + cA[0].getNick() + " " + cA[0].isFemale());
		System.out.print("ContactO1: " + c1.getBirthYear() + " " +c1.getCity() + " ");
		System.out.println(c1.getName() + " " + c1.getNick() + " " + c1.isFemale());
		System.out.print("ContactA2: " + cA[1].getBirthYear() + " " +cA[1].getCity() + " ");
		System.out.println(cA[1].getName() + " " + cA[1].getNick() + " " + cA[1].isFemale());
		System.out.print("ContactO2: " + c2.getBirthYear() + " " +c2.getCity() + " ");
		System.out.println(c2.getName() + " " + c2.getNick() + " " + c2.isFemale());
	    }
	}
	else System.out.println("Contact add: FAIL! bas Array size");

	if(cB==null) System.out.println("Contacts rem: OK!");
	else System.out.println("Contacts rem: FAIL!");

	if(mA.length == 2) {
	    if(mA[1].equals(m2) && mA[0].equals(m1)) 
		System.out.println("MsgArch add: OK!");
	    else
		System.out.println("MsgArch add: FAIL! bad Contact up or down");
	}
	else System.out.println("MsgArch add: FAIL! bas Array size");

	if(mB==null) System.out.println("MsgArch rem: OK!");
	else System.out.println("MsgArch rem: FAIL!");

    }
}