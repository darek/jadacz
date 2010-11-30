/*
 * JContact.java
 *
 * Created on 1 luty 2007, 18:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jadacz;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import java.io.IOException;
import jadacz.lib.Contact;

/** 
 * Jadacz user list class
 */
public class JContact {
    java.util.Vector contact = new java.util.Vector();
    private String ccffile = null;
    private static JContact instance = null;
    /**
     * Creates a new instance of JContact
     */
    public JContact() {
            
    }
    /**
    * Loading contact list from CyberContactlistFile
    * @author Cyber
    * @param _file filename to read
    * @deprecated
    */
    public void loadFromCCF(String _file){
        ccffile = _file;
        contact.removeAllElements();
        java.io.BufferedReader input = null;
        java.io.File file = null;
        try{
            file = new java.io.File(ccffile);
        }catch(java.lang.NullPointerException npe){
            
        }
         try {
            input = new java.io.BufferedReader( new java.io.FileReader(ccffile) );
            String line = null;
            String[] cnt = new String[2];
            
            while (( line = input.readLine()) != null){
                Contact cont = new Contact();
                cnt = line.split("::");
                cont.setJID(Integer.valueOf(cnt[0]));
                cont.setName(cnt[1]);
                System.out.println(cnt[0]+"==="+cnt[1]);
                this.addContact(cont);
              }
            }
            catch (java.io.FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (java.io.IOException ex){
                ex.printStackTrace();
            }
            finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                }
             catch (java.io.IOException ex) {
                ex.printStackTrace();
             }
        }
    }
    /**
    * Saving contact list to CyberContactlistFile
    * @author Cyber
    * @deprecated
    */
    public void saveToCCF() throws IOException{
        java.io.BufferedWriter bw = null;
        try {
            bw = new java.io.BufferedWriter(new java.io.FileWriter(ccffile,false));
            for ( int i = 0; i < contact.size(); ++i ){
                Contact value = (Contact) contact.get( i );
                bw.write(value.getJID() + "::" + value.getName());
                bw.newLine();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
           
        }
        
        
    }
    /**
     * Return contact specified by key
     * @param _key - position in list
     * @return Contact
     * @see Contact
     */
    public Contact getContact(int _key){
        try {
            return (Contact) contact.get(_key);
        }catch(java.lang.ArrayIndexOutOfBoundsException aioobe) {
        }
        return new Contact(0,"->UNKNOW<-");
    }
    /**
     * Return size of contact container
     * @return int
     */
    public int size(){
        return contact.size();
    }
    /**
     * Add contact to container
     * @see java.util.Vector
     */
    public void addContact(Contact _c){
        System.out.println(_c);
        contact.add(_c);
        
    }
    /**
     * Return all User names
     * @return Object[]
     */
    public Object[] getNames(){
        Object[] names = new Object[contact.size()];
        for ( int i = 0; i < contact.size(); ++i ){
            Contact value = (Contact) contact.get( i );
            names[i] = value.getName();
        }
        return names;
    }
    /**
     * Convert user list container to Object[]
     * @return Object[]
     */
    public Object[] toArray(){
        return contact.toArray();
    }
    /**
     * Get user specified by JID, if can't find user return null
     * @param _jid - user JID
     * @return Contact
     * @see Contact
     */
    public Contact getByJID(int _jid){
        for ( int i = 0; i < contact.size(); ++i ){
            Contact value = (Contact) contact.get( i );
            if(value.getJID() == _jid){
                return value;
            }
        }
        return null;
    }
    /*
     * Check if specified JID exist in list, if so return position in list otherwise return -1
     * @param _jid - user JID
     * @return int - position in list
     */
    public int JIDexists(int _jid){
        for ( int i = 0; i < contact.size(); ++i ){
            Contact value = (Contact) contact.get( i );
            if(value.getJID() == _jid){
                return i;
            }
        }
        return -1;
    }
    /**
     * Update contact
     * @param _contact - contact info
     * @see Contact
     */
    public void updateContact(Contact _contact){
        for ( int i = 0; i < contact.size(); ++i ){
            Contact value = (Contact) contact.get( i );
            if(value.getJID() == _contact.getJID()){
               // contact.removeElementAt(i);
                contact.set(i,_contact);
            }
        }
    }
    /**
     * Remove contact specified by key
     * @param _key - position in list;
     */
    public void remove(int _key){
        contact.remove(_key);
    }
    /**
     * Singleton pattern
     * @return JContact
     */
    public static JContact getInstance(){
        if(instance == null){
            instance = new JContact();
            return instance;
        }else{
            return instance;
        }
        
    }

}