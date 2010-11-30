/**
 * jadaczserver package contains all classes of Jadacz server.
 */
package jadacz.lib;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Connection is a class used to handle two way comunication and
 * data exchange. The entity of data transfer is Packet. 
 * 
 * @author 	Szymon 'tecku' Kordyaczny
 * @version     1.0
 * @see Packet
 */
public class Connection implements Closeable {
    
    /**
     * This signature is sent before every packet.
     */
    private byte[] PACKET_SIGNATURE = { '#', 'P', '#' };
    
    /**
     * The socket of Connection on local machine.
     */
    protected Socket socket= null;

    /**
     * Data input stream of connection.
     */
    protected DataInputStream in= null;
    
    /**
     * Data output stream of connection.
     */
    protected DataOutputStream out= null;

    private Boolean syncSend = new Boolean(true);
    
    private Boolean syncRecv = new Boolean(true);
    
    /**
     * Creates connection from socket, which means it opens i/o streams.
     * 
     * @throws IOException if an I/O error occurs when
     * (data) input/output stream is opening.
     * @param socket the local socket
     */
    public Connection(Socket socket) throws IOException {
	this.socket = socket;
	in = new DataInputStream(this.socket.getInputStream());
	out = new DataOutputStream(this.socket.getOutputStream());
    }
    
    /**
     * Sends a packet to output stream of connection.
     * This method is synchronized so more that one thread can call
     * this method without risk of data mix.
     * 
     * @param packet	the packet to send
     * @throws IOException	if an I/O error occurs
     */
    public void send(Packet packet) throws IOException {
	synchronized (syncSend) {
	    // writes packet's beginning signature
	    //System.out.println("Connection.send: sending signature...");
	    out.write(PACKET_SIGNATURE);
	    //System.out.println("Connection.send: ...signature sent");

	    //System.out.println("Connection.send: writing packet to ostream...");
	    // writes properties of packet
	    out.writeInt(packet.getJID());
	    out.writeByte(packet.getType());
	    out.writeInt(packet.getContentLength());

	    // writes content of packet
	    out.write(packet.getContent(), 0, packet.getContentLength());
	    //System.out.println("Connection.send: ...packet wrote to ostream");
	    //System.out.println("Connection.send: flushing ostream...");
	    out.flush();
	    //System.out.println("Connection.send: ...ostream flushed");
	}
    }
    
    /**
     * Sends a packet to output stream of connection.
     * This method is synchronized so more that one thread can call
     * this method without risk of data mix.
     * 
     * @param packet	the packet to which write the recivied data
     * @throws IOException	if an I/O error occurs
     */
    public void receive(Packet packet) throws IOException {
	synchronized (syncRecv) {
	    //System.out.println("Connection.receive: receving started");
	    if (packet == null)
		throw new NullPointerException();

	    // checks packet's beginning signature
	    // if signature is invalid it skips 1 byte and check again
	    // as long as it matches the signature or get end of stream
	    int i = 0;
	    //System.out.println("Connection.receive: reading signature");
	    while (i != PACKET_SIGNATURE.length) {
		if (in.readByte() == PACKET_SIGNATURE[i]) {
		    ++i;
		} else {
		    i = 0;
		    System.out
			    .println("Connection.receive: skipping some data, wrong data received");
		}
	    }
	    //System.out.println("Connection.receive: signature readed");

	    // reads properties of packet
	    packet.setJID(in.readInt());
	    packet.setType(in.readByte());

	    // reads contentLength and content of packet
	    int contentLength = in.readInt();
	    if (contentLength > 0) {
		byte[] buffer = new byte[contentLength];
		in.readFully(buffer);
		packet.setContent(buffer);
	    }
	    //System.out.println("Connection.receive: receving ended");
	}
    }
    
    /**
     * Closes input, output stream and socket of the connection.
     * This method simply performs in.close(), out.close()
     * and socket.close().
     * 
     * @throws IOException if an I/O error occurs when closing socket,
     * input/output stream
     * @see java.io.FilterInputStream#close()
     * @see java.io.FilterOutputStream#close()
     * @see java.net.Socket#close()
     */
    public void close() throws IOException {
	if (in != null) this.in.close();
	if (out != null) this.out.close();
	if (socket != null) this.socket.close();
    }
    
}
