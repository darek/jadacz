/*
 * Jconfig.java
 *
 * Created on 27 styczeñ 2007, 12:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jadacz;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Configuration class, read from configuration option from XML and store it in data object
 * @author Cyber 
 */
public class JXMLConfig {
    static String file = "./conf/config.xml";
    java.util.Properties prop = null;
    private static JXMLConfig instance = null;
    java.io.FileInputStream is = null;
    
    /**
     * Creates a new instance of JXMLConfig
     */
    public JXMLConfig() {
        prop = new java.util.Properties();
        init();
    }
    /**
     * Initialize configuration, parse xml file
     *
     */
    public void init(){
        try{
          java.io.File f = new java.io.File(file);
          is = new java.io.FileInputStream(f.getAbsolutePath());
          System.out.println(f.getAbsolutePath());
          prop.loadFromXML(is);          
        }catch(java.io.FileNotFoundException fnfe){
            System.out.println(fnfe);
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JOptionPane.showMessageDialog(frame,
                                                      "Program nie mo¿e odnaleœæ pliku konfiguracyjnego. \n Zostanie stworzony domyslny plik. \n Prosze zrestartowaæ program",
                                                      "Brak pliku konfiguracyjnego",
                                                      javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        catch(java.io.IOException ioe){
            System.out.println(ioe);
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JOptionPane.showMessageDialog(frame,
                                                      "Plik konfiguracyjny posiada z³¹ strukturê.\n Prosze skasowaæ aktualny plik konfiguracyjny \n i ponownie uruchomiæ program",
                                                      "B³êdny plik konfiguracyjny",
                                                      javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        catch(java.lang.NullPointerException npe){
            System.out.println(npe);
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JOptionPane.showMessageDialog(frame,
                                                      "Nieoczekiwany b³¹d konfigurayjny",
                                                      "B³¹d konfiguracyjny",
                                                      javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    /**
     * return property, if property can't be found return default value
     * @param key - property key
     * @param defaultValue - default value if property don't exist
     * @return String
     */
    public String getProperty(String key, String defaultValue){ 
            return  prop.getProperty(key,defaultValue);
    }   
    /**
     * return property, if property can't be found return null 
     * @param key - property key
     * @return String
     */
    public String getProperty(String key){
            return prop.getProperty(key);  
    }
    /**
     * Set property defined by ky and value
     * @param key
     * @param value
     * @return boolean - true if succesfull otherwise false
     */
    public boolean setProperty(String key, String value){
    
           if(key!=null & value!=null){
               prop.setProperty(key,value);
               return true;
           }else{
               return false;
           }
    }
    /**
     * Save Config to xml file defined by static _file variable in 
     */
    public void storeToXML(){
        java.io.FileOutputStream os = null;
        try{
            os = new java.io.FileOutputStream(file);
            prop.storeToXML(os,"Jadacz config");
        }catch(java.io.FileNotFoundException fnfe){
            System.out.println(fnfe);
        }catch(java.lang.SecurityException se){
            System.out.println(se);
        }catch(java.io.IOException ioe){
            System.out.println(ioe);
        }
    }
    /**
     * Return all values as Enumeration
     * @return java.util.Enumeration
     */
    public java.util.Enumeration propertyNames(){
        return prop.propertyNames();
    }
    /**
     * Return frame attribiutes such as position and size;
     * @param key - config key
     * @return Object - array of attribiutes
     */
    public Object getFrameAttrib(String key){
        try{
            String[] data = this.getProperty(key).split(",");
            int[] loc = new int[2];
            loc[0] = Integer.valueOf(data[0]);
            loc[1] = Integer.valueOf(data[1]);
            if(loc[0]>=0 && loc[1]>=0){
                java.awt.Point point = new java.awt.Point(loc[0],loc[1]);
                return point;
            }else{
                return null;
            }
        }catch(java.lang.NullPointerException npe){
            System.out.println("Brak danych");
            return null;
        }
    }
    /**
     * Singleton function returning instance of JXMLConfig class
     * @return JXMLConfig
     */
    public static JXMLConfig getInstance(){
        if(instance == null){
            instance =  new JXMLConfig();
            return instance;
        }else{
            return instance;
        }
    }
}