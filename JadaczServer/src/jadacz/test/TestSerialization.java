package jadacz.test;

import jadacz.lib.Contact;
import jadacz.lib.FileData;
import jadacz.lib.FileInfo;
import jadacz.lib.LoginRequest;
import jadacz.lib.Message;
import jadacz.lib.Packet;
import jadacz.lib.Status;
import jadacz.lib.UserInfo;
import jadacz.server.Loger;


/**
 * Test class.
 * 
 * @author Pawel 'top' Luczak
 * @author Szymon 'tecku' Kordyaczny
 * 
 * @version Pre alpha
 *
 */
public class TestSerialization {
    
    private static void testContact(){
	Contact c1 = new Contact(0,"user","name","city",1900,true);
	Packet packet= c1.toPacket();
	Contact c2 = new Contact(packet);

	if (c1.equals(c2))
	{
	    System.out.println("Contact1 == Contact2: ok!");
	}
	else
	{
	    System.out.println("Contact1 != Contact2: failed!");
	    System.out.println(c1);
	    System.out.println(c2);
	}
    }

    private static void testFileDataSet(){
	int test_size=5;
	byte[] test = new byte[test_size];
	for(int i=0 ; i<test_size ; i++) test[i]=(byte)i;

	FileData fd1 = new FileData(0,0,test_size,test);
	FileData fd2 = new FileData(0,0,0,new byte[0]);

	fd2.setData(fd1.getData());

	if (fd1.equals(fd2))
	{
	    System.out.println("FileData1 == FileData2: ok! testing set metods");
	}
	else
	{
	    System.out.println("FileData1 != FileData2: failed! SET");
	    System.out.println(fd1);
	    System.out.println(fd2);
	}	
    }

    private static void testFileDataGet(){
	int test_size=5;
	byte[] test = new byte[test_size];
	for(int i=0 ; i<test_size ; i++) test[i]=(byte)i;

	FileData fd1 = new FileData(0,0,test_size,test);
	FileData fd2 = new FileData(0,0,fd1.getDataLength(),fd1.getData());

	if (fd1.equals(fd2))
	{
	    System.out.println("FileData1 == FileData2: ok! testing get metods");
	}
	else
	{
	    System.out.println("FileData1 != FileData2: failed! GET");
	    System.out.println(fd1);
	    System.out.println(fd2);
	}	
    }

    private static void testUserInfo(){
	UserInfo ui1 = new UserInfo("user","name","city",1900,true,"email","password");
	Packet packet= ui1.toPacket();
	UserInfo ui2 = new UserInfo(packet);

	if (ui1.equals(ui2))
	{
	    System.out.println("UserInfo1 == UserInfo2: ok!");
	}
	else
	{
	    System.out.println("UserInfo1 != UserInfo2: failed!");
	    System.out.println(ui1);
	    System.out.println(ui2);
	}
    }

    private static void testFileData(){
	int test_size=5;
	byte[] test = new byte[test_size];
	for(int i=0 ; i<test_size ; i++) test[i]=(byte)i;

	FileData fd1 = new FileData(0,0,test_size,test);
	Packet packet = fd1.toPacket();
	FileData fd2 = new FileData(packet);

	if (fd1.equals(fd2))
	{
	    System.out.println("FileData1 == FileData2: ok!");
	}
	else
	{
	    System.out.println("FileData1 != FileData2: failed!");
	    System.out.println(fd1);
	    System.out.println(fd2); 
	}	

    }

    private static void testFileInfo(){
	FileInfo fi1 = new FileInfo(10,1024,"plik.wykonywalny");
	Packet packet= fi1.toPacket();
	FileInfo fi2 = new FileInfo(packet);

	if (fi1.equals(fi2))
	{
	    System.out.println("FileInfo1 == FileInfo2: ok!");
	}
	else
	{
	    System.out.println("FileInfo1 != FileInfo2: failed!");
	    System.out.println(fi1);
	    System.out.println(fi2);
	}
    }

    private static void testMessage(){
	Message m1 = new Message(19,"czeœæ");
	Packet packet= m1.toPacket();
	Message m2 = new Message(packet);

	if(m1.equals(m2))
	{
	    System.out.println("Message1 == Message2: ok!");
	    //System.out.println(m1.getTime() + " " + m2.getTime());
	}
	else 
	{ 
	    System.out.println("Message1 != Message2: failed!");
	    System.out.println(m1);
	    System.out.println(m2);
	}
    }

    private static void testLoginRequest() {
	LoginRequest login1 = new LoginRequest(1, (float)0.1 , "has³o");
	Packet packet= login1.toPacket();
	LoginRequest login2 = new LoginRequest(packet);

	if(login1.equals(login2))
	{
	    System.out.println("LoginRequest == LoginRequest2: ok!");
	}
	else 
	{ 
	    System.out.println("LoginRequest1 != LoginRequest2: failed!");
	    System.out.println(login1);
	    System.out.println(login2);
	}
    }

    private static void testStatus() {
	Status status1 = new Status(13,Status.TYPE_EAT_ME,"Czeœæ");
	Packet packet= status1.toPacket(Packet.TYPE_STATUS_INFO);
	Status status2 = new Status(packet);


	if (status1.equals(status2)) {
	    System.out.println("Status1 == Status2: ok!");
	}
	else {
	    System.out.println("Status1 != Status2: failed!");
	    System.out.println(status1);
	    System.out.println(status2);
	}


    }

    private static Loger log = Loger.getInstance();

    /**
     * @param args
     */
    public static void main(String[] args) {

	log.println("Written in test.",Loger.NORMAL);
	testStatus();
	testLoginRequest();
	testMessage();
	testFileInfo();
	testFileData();
	testFileDataGet();
	testFileDataSet();
	testUserInfo();
	testContact();


	/*    
	public FileData(int jid, byte[] filedata, long offset, int dataLength ) {
		this.jid = jid;
		this.offset = offset;
		this.dataLength = dataLength;
		this.data = new byte[dataLength];
		System.arraycopy(filedata, offset, this.data, 0, dataLength);
    	}

	    albo jeszcze lepiej zrobic 2 wersje setContent:
	    setContent(byte[])
	    setContent(byte[], long, int)
	    i wykorzystac ta 2 w konstruktorze

	    teraz to nawet przypomina to jeden z konstruktorow Stringa
	 */
    }

    @SuppressWarnings("unused")
    private static void teckuTestRef(){
	String s= "lol";
	String s2= "rotfl";
	int i= 12345;
	byte[] b= {'w','o','w'};

	System.out.println("Test referencji przed changeIt:");
	System.out.println(s);
	System.out.println(s2);
	System.out.println(i);
	System.out.println(new String(b));

	changeIt(s,s2,i,b);

	System.out.println("Test referencji po changeIt:");
	System.out.println(s);
	System.out.println(s2);
	System.out.println(i);
	System.out.println(new String(b));
    }

    private static void changeIt(String s, String s2, int i, byte[] b)
    {
	s = "zonk";
	s2.concat("+++++");
	i = 666;
	b[1] = 'x';

	System.out.println("Test w changeIt:");
	System.out.println(s);
	System.out.println(s2);
	System.out.println(i);
	System.out.println(new String(b));
    }
}
