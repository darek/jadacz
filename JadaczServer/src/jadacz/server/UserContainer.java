/**
 * 
 */
package jadacz.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Singletone class for handling users which are logged in on the server.
 * 
 * @author Szymon 'tecku' Kordyaczny
 * @version 1.0
 */
public class UserContainer {
    
    /**
     * Reference to the only existing object of UserContainer.
     */
    private final static UserContainer instance = new UserContainer();

    /**
     * Map which contains users.
     */
    private ConcurrentHashMap<Integer, User> map = null;

    /**
     * Creates empty UserContainer.
     */
    private UserContainer() {
	map = new ConcurrentHashMap<Integer, User>();
    }

    /**
     * Gets reference to object from instance field.
     * 
     * @return object reference
     */
    public static UserContainer getInstance() {
	return instance;
    }

    /**
     * Fetches user in UserContainer by his Jadacz ID.
     * 
     * @param userJid key for search
     * @return User reference
     */
    public User get(int userJid) {
	return (User) map.get(new Integer(userJid));
    }
    
    /**
     * Returns jids table.
     * 
     * @return jids
     */
    public int[] getJids() {
	Object[] ojids = map.keySet().toArray();
	int[] jids = new int[ojids.length];
	
	for (int i=0 ; i < jids.length ; ++i){
	    jids[i] = ((Integer)ojids[i]).intValue();
	}
	
	return jids;
    }

    /**
     * Checks whether the user is inside UserContainer.
     * 
     * @param userJid key to search
     * @return bool whether the user is in Container
     */
    public boolean contains(int userJid) {
	return map.containsKey(new Integer(userJid));
    }

    /**
     * Inserts new user to UserContainer.
     * 
     * @param user user object to insert
     * @return inserted User object
     */
    public User insert(User user) {
	map.put(new Integer(user.getJID()), user);
	return user;
    }

    /**
     * Remove user from container.
     * 
     * @param userJid key to remval
     * @return removed User object
     */
    public User remove(int userJid) {
	return (User) map.remove(new Integer(userJid));
    }

    /**
     * Gets size of Container.
     * 
     * @return size of Container
     */
    public int size() {
	return map.size();
    }

}
