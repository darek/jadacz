/**
 * 
 */
package jadacz.server;

import jadacz.lib.Contact;
import jadacz.lib.Message;
import jadacz.lib.UserInfo;

import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class of database connections.
 * 
 * Note: use static init() before creating any connection object. 
 * 
 * @author Szymon 'tecku' Kordyaczny
 * @version 1.0
 */
public class DBConnection implements Closeable {

    
    
    /**
     * URL to database. 
     */
    private static String url = null;

    /**
     * Username on database server.
     */
    private static String username = null;

    /**
     * Password on database server.
     */
    private static String password = null;

    /**
     * Driver of JDBC Connector/J
     */
    private static String driver = null;

    /**
     * Initialization flag.
     */
    private static boolean initialized = false;

    /**
     * Connection to database.
     */
    private java.sql.Connection connection = null;
    
    /**
     * Loads JDBC driver, and creates table on database if they not exists.
     */
    public static void init(String url, String username, String password,
	    String driver) throws ClassNotFoundException, SQLException {
	DBConnection.url = url;
	DBConnection.username = username;
	DBConnection.password = password;
	DBConnection.driver = driver;

	// Register the JDBC driver.
	Class.forName(DBConnection.driver);

	// create tables if db is empty

	java.sql.Connection conn = null;
	java.sql.Statement stmt = null;
	try {
	    conn = java.sql.DriverManager
		    .getConnection(url, username, password);
	    stmt = conn.createStatement();

	    // create jadacz user's table if it not exists
	    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `jadacz_users` ("
		    + "`jid` INT UNSIGNED NOT NULL AUTO_INCREMENT ,"
		    + "`nick` VARCHAR( 30 ) NOT NULL ,"
		    + "`name` VARCHAR( 30 ) NOT NULL ,"
		    + "`password` VARCHAR( 30 ) NOT NULL ,"
		    + "`city` VARCHAR( 30 ) NOT NULL ,"
		    + "`email` VARCHAR( 50 ) NOT NULL ,"
		    + "`birthYear` INT( 4 ) NOT NULL ,"
		    + "`female` ENUM( 'false', 'true' ) NOT NULL ,"
		    + "PRIMARY KEY ( `jid` )" + ") AUTO_INCREMENT = 100");

	    // create jadacz user contacts` table if it not exists
	    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `jadacz_contacts` ("
		    + "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,"
		    + "`owner_jid` INT UNSIGNED NOT NULL ,"
		    + "`jid` INT UNSIGNED NOT NULL ,"
		    + "`nick` VARCHAR( 30 ) NOT NULL ,"
		    + "`name` VARCHAR( 30 ) NOT NULL ,"
		    + "`city` VARCHAR( 30 ) NOT NULL ,"
		    + "`birthYear` INT( 4 ) NOT NULL ,"
		    + "`female` ENUM( 'false', 'true' ) NOT NULL ,"
		    + "PRIMARY KEY ( `id` ) ," + "INDEX ( `owner_jid` )" + ")");

	    // create jadacz user contacts` table if it not exists
	    stmt
		    .executeUpdate("CREATE TABLE IF NOT EXISTS `jadacz_archive_msg` ("
			    + "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,"
			    + "`owner_jid` INT UNSIGNED NOT NULL ,"
			    + "`jid` INT UNSIGNED NOT NULL ,"
			    + "`time` BIGINT NOT NULL ,"
			    + "`content` TEXT NOT NULL ,"
			    + "PRIMARY KEY ( `id` ) ,"
			    + "INDEX ( `owner_jid` )" + ")");

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}

	// sets initialization flag
	DBConnection.initialized = true;

    }

    /**
     * Creates a database connection object.
     * 
     * @throws SQLException
     */
    public DBConnection() throws SQLException {
	if (DBConnection.initialized == false) {
	    throw new SQLException(
		    "Database connection wasn't initialized, use init()");
	}
	connection = java.sql.DriverManager.getConnection(url, username,
		password);

    }

    /**
     * Closes this database connection.
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
	if (connection != null) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new IOException(e.getMessage());
	    }
	}
    }

    // get:

    
    
    /**
     * Returns archive messages from database.
     * 
     * @param owner_jid archive messages owner
     * @return message array or <code>null</code> if there's no messages
     * @throws SQLException if error in database connection occurs
     */
    public Message[] getArchiveMessages(int owner_jid) throws SQLException {
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = connection.createStatement();
	    
	    rs = stmt.executeQuery("SELECT * FROM `jadacz_archive_msg` "
		    + "WHERE `owner_jid` = '" + owner_jid + "' "
		    + "ORDER BY `id` ASC");
	    
	    rs.last();
	    int count = rs.getRow();
	    rs.beforeFirst();
	    
	    if (count > 0) {
		// there are some archive messages of this user
		Message[] msgs = new Message[(int)count];

		int i = 0;
		while (rs.next()) {
		    msgs[i] = new Message();
		    msgs[i].setJID(rs.getInt("jid"));
		    msgs[i].setTime(rs.getLong("time"));
		    msgs[i].setContent(rs.getString("content"));
		    ++i;
		}

		return msgs;
	    }
	    else {
		// no contacts found
		return null;
	    }
		
	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	    // always close result set
	    if (rs != null) {
		rs.close();
		rs = null;
	    }
	}
    }

    /**
     * Returns contacts from database.
     * 
     * @param owner_jid contacts owner
     * @return contacts array or <code>null</code> if there's no messages
     * @throws SQLException if error in database connection occurs
     */
    public Contact[] getContacts(int owner_jid) throws SQLException {
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = connection.createStatement();

	    rs = stmt.executeQuery("SELECT COUNT(*) FROM `jadacz_contacts`"
		    + "WHERE owner_jid='" + owner_jid + "' ");
	    int count = 0;
	    if (rs.next()) {
		count = rs.getInt(1);
	    }
	    rs.close();
	    rs = null;
	    
	    rs = stmt.executeQuery("SELECT * FROM `jadacz_contacts`"
		    + "WHERE owner_jid='" + owner_jid + "'"
		    + "ORDER BY `id` ASC");
	    
	    if (count > 0) {
		// there are some contacts of this user
		Contact[] contacts = new Contact[count];
		int i = 0;
		while (rs.next()) {
		    contacts[i] = new Contact();
		    contacts[i].setJID(rs.getInt("jid"));
		    contacts[i].setNick(rs.getString("nick"));
		    contacts[i].setName(rs.getString("name"));
		    contacts[i].setCity(rs.getString("city"));
		    contacts[i].setBirthYear(rs.getInt("birthYear"));
		    contacts[i].setFemale(rs.getBoolean("female"));
		    ++i;
		}

		return contacts;
	    }
	    else {
		// no contacts found
		return null;
	    }
		
	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	    // always close result set
	    if (rs != null) {
		rs.close();
		rs = null;
	    }
	}
    }

    /**
     * Returns user info from database.
     * 
     * @param jid the JID number of user
     * @return user info or <code>null</code> if there's no such a user
     * @throws SQLException if error in database connection occurs
     */
    public UserInfo getUserInfo(int jid) throws SQLException {
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = connection.createStatement();

	    rs = stmt.executeQuery("SELECT * FROM `jadacz_users`"
		    + "WHERE jid='" + jid + "'");
	    if (rs.next()) {
		// record found
		return new UserInfo(
			rs.getString("nick"),
			rs.getString("name"),
			rs.getString("city"),
			rs.getInt("birthYear"),
			rs.getBoolean("female"),
			rs.getString("email"),
			rs.getString("password")
			);
	    } else {
		// no record found
		return null;
	    }

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	    // always close result set
	    if (rs != null) {
		rs.close();
		rs = null;
	    }
	}

    }

    // add:

    
    /**
     * Adds archive message to database.
     * 
     * @param owner_jid archive message owner
     * @param msg message to add
     * @throws SQLException if error in database connection occurs
     */
    public void addArchiveMessage(int owner_jid, Message msg)
	    throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("INSERT INTO `jadacz_archive_msg` ("
		    + "`owner_jid` , `jid` , `time` , `content` )"
		    + "VALUES ("
		    + "'" + owner_jid + "', "
		    + "'" + msg.getJID() + "', "
		    + "'" + msg.getTime() + "', "
		    + "'" + msg.getContent() + "');",
		    Statement.RETURN_GENERATED_KEYS);
	    
	    if (count != 1){
		throw new SQLException("Cannot add archive message to DB.");
	    }
	    
	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    /**
     * Adds new user to database.
     * 
     * @param userInfo user to add
     * @return the JID number of new added user
     * @throws SQLException if error in database connection occurs
     */
    public int addUser(UserInfo userInfo) throws SQLException {
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("INSERT INTO `jadacz_users` ("
		    + "`nick` , `name` , `password` , `city` , `email` ,"
		    + "`birthYear` , `female` ) VALUES ("
		    + "'" + userInfo.getNick() + "', "
		    + "'" + userInfo.getName() + "', "
		    + "'" + userInfo.getPassword() + "', "
		    + "'" + userInfo.getCity() + "', "
		    + "'" + userInfo.getEmail() + "', "
		    + "'" + userInfo.getBirthYear() + "', "
		    + "'" + userInfo.isFemale() + "');",
		    Statement.RETURN_GENERATED_KEYS);
	    
	    if (count != 1){
		throw new SQLException("Cannot add user.");
	    }
	    
	    rs = stmt.getGeneratedKeys();
	    if (rs.next()) {
		// returns new jid (generated by auto_increment)
		return rs.getInt(1);
	    } else {
		throw new SQLException("Cannot add user.");
	    }

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	    // always close result set
	    if (rs != null) {
		rs.close();
		rs = null;
	    }
	}
    }

    /**
     * Adds new contact to database.
     * 
     * @param owner_jid the JIB number of contact owner
     * @param contact contact info
     * @throws SQLException if error in database connection occurs
     */
    public void addContact(int owner_jid, Contact contact) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("INSERT INTO `jadacz_contacts` ("
		    + "`owner_jid` , `jid` , `nick` , `name` , `city` ,"
		    + "`birthYear` , `female` )"
		    + "VALUES ("
		    + "'" + owner_jid + "', "
		    + "'" + contact.getJID() + "', "
		    + "'" + contact.getNick() + "', "
		    + "'" + contact.getName() + "', "
		    + "'" + contact.getCity() + "', "
		    + "'" + contact.getBirthYear() + "', "
		    + "'" + contact.isFemale() + "');",
		    Statement.RETURN_GENERATED_KEYS);
	    
	    if (count != 1){
		throw new SQLException("Cannot add user.");
	    }
	    

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    // update:
    /**
     * Updates contact info on database.
     * 
     * @param owner_jid the JIB number of contact owner
     * @param contact contact info
     * @throws SQLException if error in database connection occurs
     */
    public int updateContact(int owner_jid, Contact contact) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("UPDATE `jadacz_contacts` SET"
		    + "`nick` = '" + contact.getNick() + "', "
		    + "`name` = '" + contact.getName() + "', "
		    + "`city` = '" + contact.getCity() + "', "
		    + "`birthYear` = '" + contact.getBirthYear() + "', "
		    + "`female` = '" + contact.isFemale() + "'"
		    + "WHERE `owner_jid` = '" + owner_jid + "'"
		    + "AND `jid` = '" + contact.getJID() + "'"
		    );
	    
	    return count;

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    /**
     * Updates user info on database.
     * 
     * @param jid the JID number of user
     * @param user user info
     * @return the count of changed records
     * @throws SQLException if error in database connection occurs
     */
    public int updateUserInfo(int jid, UserInfo user) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("UPDATE `jadacz_contacts` SET"
		    + "`nick` = '" + user.getNick() + "', "
		    + "`name` = '" + user.getName() + "', "
		    + "`city` = '" + user.getCity() + "', "
		    + "`password` = '" + user.getPassword() + "', "
		    + "`email` = '" + user.getEmail() + "', "
		    + "`birthYear` = '" + user.getBirthYear() + "', "
		    + "`female` = '" + user.isFemale() + "'"
		    + "WHERE `jid` = '" + jid + "'"
		    );
	    
	    return count;

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    // remove:
    
    /**
     * Removes all archive messages of user.
     * 
     * @param owner_jid the JID number of owner
     * @return the count of changed records
     * @throws SQLException if error in database connection occurs
     */
    public int removeArchiveMessages(int owner_jid) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("DELETE FROM `jadacz_archive_msg`"
		    + "WHERE `owner_jid` = '" + owner_jid + "'");
	    
	    return count;

	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    /**
     * Removes contact of user.
     * 
     * @param owner_jid the JID number of contact owner
     * @param contact_jid the JID number of contact to be removed
     * @return the count of changed records
     * @throws SQLException if error in database connection occurs
     */
    public int removeContact(int owner_jid, int contact_jid) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("DELETE FROM `jadacz_contacts`"
		    + "WHERE `owner_jid` = '" + owner_jid + "'"
		    + "AND `jid` = '" + contact_jid + "' ");
	    
	    return count;
	} finally {
	    // always close statement
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }

    /**
     * Removes user from database.
     * 
     * @param jid the JID number of user
     * @return the count of changed records
     * @throws SQLException if error in database connection occurs
     */
    public int removeUser(int jid) throws SQLException {
	Statement stmt = null;
	try {
	    stmt = connection.createStatement();

	    int count = stmt.executeUpdate("DELETE FROM `jadacz_users`"
		    + "WHERE `jid` = '" + jid + "' ");
	    
	    stmt.executeUpdate("DELETE FROM `jadacz_contacts`"
		    + "WHERE `owner_jid` = '" + jid + "' ");
	    
	    stmt.executeUpdate("DELETE FROM `jadacz_archive_msg`"
		    + "WHERE `owner_jid` = '" + jid + "' ");
	    
	    return count;
	} finally {
	    // always close statment
	    if (stmt != null) {
		stmt.close();
		stmt = null;
	    }
	}
    }



}
