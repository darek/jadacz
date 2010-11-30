/**
 * 
 */
package jadacz.lib;

/**
 * Interface of classes which can be serialized to packet.
 * 
 * @author 	Szymon 'tecku' Kordyaczny
 * @version     1.0
 * @see Packet
 */
public interface Sendable {
      
    /**
     * Creates a packet without specyfing a type (uses default).
     * 
     * @return packet with serialized data
     */
    public Packet toPacket();
    
    /**
     * Creates a Packet of type we decide.
     * 
     * @param type the type of returned packet
     * @return packet with serialized data
     */
    public Packet toPacket(byte type);

    /**
     * Loads data from packet.
     * 
     * @param packet the packet with serialized data
     */
    public void loadFromPacket(Packet packet);
}
