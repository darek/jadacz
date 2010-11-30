/*
 * Registry.java
 *
 * Created on 10 luty 2007, 19:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jadacz;

/**
 * registry class contains references to objects
 * @author Cyber
 */
public final class Registry {
    private static java.util.HashMap<String,Object> _registry = new java.util.HashMap();
    /** Creates a new instance of Registry */
    public Registry() {
        
    }
    public static void register(String _name, Object _obj){
        _registry.put(_name,_obj);
    }
    
    public static Object registry(String _name){
        if(_registry.containsKey(_name)){
            return _registry.get(_name);
        }else{
            return null;
        }
    }
    public static void unregister(String _name){
        if(_registry.containsKey(_name)){
            _registry.remove(_name);
        }
    }
}
