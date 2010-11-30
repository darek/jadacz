/**
 * 
 */
package jadacz.server;


/**
 * xml config parser
 * @author Dariusz "Cyber" Zon
 */

public class JXMLConfig {
    /**
     * Loger for loging in error.log
     */
    private static Loger log = Loger.getInstance();
    
    /**
     * Config file name.
     */
    static String file = "config.xml";
    
    /**
     * Java Properties.
     */
    java.util.Properties prop = null;
    
    /**
     * Reference to Config Loader.
     */
    private static JXMLConfig instance = null;
    
    /**
     * Stream to read file.
     */
    java.io.FileInputStream is = null;

    /**
     * Creates a new instance of JXMLConfig
     */
    public JXMLConfig() {
	prop = new java.util.Properties();
	init();
    }

    /**
     * Init of ConfigLoader (opens stream etc.)
     *
     */
    public void init(){
	try{
	    java.io.File f = new java.io.File(file);
	    is = new java.io.FileInputStream(f.getAbsolutePath());
	    log.println(f.getAbsolutePath(),Loger.NORMAL);
	    prop.loadFromXML(is);
	}catch(java.io.FileNotFoundException fnfe){
	    System.out.println(fnfe);

	}
	catch(java.io.IOException ioe){
	    System.out.println(ioe);
	}
	catch(java.lang.NullPointerException npe){
	    System.out.println(npe);
	}
    }

    /**
     * gets property with default value set
     * 
     * @param key key for search
     * @param defaultValue default value if not found
     * @return result of search in String format
     */
    public String getProperty(String key, String defaultValue){
	return  prop.getProperty(key,defaultValue);
    }
    
    /**
     * gets property without default value
     * @param key key for search
     * @return result of search in String format
     */
    public String getProperty(String key){
	return prop.getProperty(key);
    }
    
    /**
     * Gets reference to object.
     * @return reference to Loger
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