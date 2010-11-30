/*
 * mainGUI.java
 * Created on 8 stycze� 2007, 11:04
 * Main GUI form for Jadacz IM
 * @autor Dariusz "Cyber" Zon  darek.zon@gmail.com
 * @version 0.2 beta
 * 
 */

package jadacz;

import jadacz.AboutForm;
import jadacz.lib.Packet;
import java.awt.Graphics;
import java.awt.MenuComponent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.Frame;
import java.io.IOException;
import jadacz.lib.FileInfo;
import jadacz.lib.Message;
import jadacz.lib.Contact;
import jadacz.lib.Status;
import java.net.UnknownHostException;
/**
 *
 * @author  Cyber
 */
public class mainGUI extends javax.swing.JFrame {
    
    private static JXMLConfig conf = null;
    private Registry registry = new Registry();
    private static JContact clist = null;
    private static mainGUI instance = null;
    private static javax.swing.DefaultListModel listModel = new javax.swing.DefaultListModel();
    private java.util.HashMap<Integer,Object> talklist = new java.util.HashMap();
    /**
     * Creates new form mainGUI, set size and location
     */
    public mainGUI(){
        conf = JXMLConfig.getInstance();
        Registry.register("conf",conf);
        clist = JContact.getInstance();
        instance = this;
        Registry.register("maingui",this);
        initComponents();
        setWindowAttribs();  
        fillUserList();
     //   System.out.println("-->"+conf.getProperty("userjid","noJID")+"<--");
    //    System.out.println(conf.getProperty("userjid","noJID"));
            if(conf.getProperty("userjid","noJID").equals("noJID")){
                System.out.println("pokaz");
                new LoginForm().setVisible(true);
            }else{
                System.out.println("start servera");
                this.serverConnect();
                System.out.println("logowanie");
                UserConnection uconn = (UserConnection) Registry.registry("uconn");
                LoginRequest lr = new LoginRequest();
                System.out.println("JID:"+conf.getProperty("userjid")+"  PASS:"+conf.getProperty("userpass"));
                lr.setJID(Integer.valueOf(conf.getProperty("userjid")));
                lr.setPassword(conf.getProperty("userpass"));
                lr.setVersion((float)0.002);
                uconn.sendLoginRequest(lr);
            } 
        //serverConnect(); 

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        userMenu = new javax.swing.JPopupMenu();
        sendMsg = new javax.swing.JMenuItem();
        sendFile = new javax.swing.JMenuItem();
        delUser = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        statusBar = new javax.swing.JToolBar();
        status = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        jadaczMenuBar = new javax.swing.JMenuBar();
        menuJadacz = new javax.swing.JMenu();
        addContactMenu = new javax.swing.JMenuItem();
        myDataMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exitMenu = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        optionsMenu = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenuItem();

        sendMsg.setText("Wy\u015blij wiadomo\u015b\u0107");
        sendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgActionPerformed(evt);
            }
        });

        userMenu.add(sendMsg);

        sendFile.setText("Wy\u015blij plik");
        sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileActionPerformed(evt);
            }
        });

        userMenu.add(sendFile);

        delUser.setText("Kasuj");
        delUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delUserActionPerformed(evt);
            }
        });

        userMenu.add(delUser);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jadacz 0.0.1 alfa");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(setIconImage());
        setName("mainFrame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        getAccessibleContext().setAccessibleName("JadaczGUI");
        jPanel1.setLayout(new java.awt.BorderLayout());

        statusPanel.setLayout(new java.awt.BorderLayout());

        statusBar.setFloatable(false);
        statusBar.setMinimumSize(new java.awt.Dimension(60, 20));
        status.setText("Status");
        status.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        status.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statusMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                statusMouseExited(evt);
            }
        });

        statusBar.add(status);

        statusPanel.add(statusBar, java.awt.BorderLayout.CENTER);

        jPanel1.add(statusPanel, java.awt.BorderLayout.SOUTH);

        userList.setComponentPopupMenu(userMenu);
        userList.setModel(this.listModel);
        userList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userListMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(userList);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jadaczMenuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        menuJadacz.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        menuJadacz.setText("Jadacz");
        addContactMenu.setText("Dodaj kontakt");
        addContactMenu.setToolTipText("Dodaje nowy kontakt");
        addContactMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContactMenuActionPerformed(evt);
            }
        });

        menuJadacz.add(addContactMenu);

        myDataMenu.setText("Moje Dane");
        myDataMenu.setToolTipText("Pokazuje dane u\u017cytkownika");
        myDataMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myDataMenuActionPerformed(evt);
            }
        });

        menuJadacz.add(myDataMenu);

        menuJadacz.add(jSeparator1);

        exitMenu.setText("Koniec");
        exitMenu.setToolTipText("Zako\u0144czenie programu");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });

        menuJadacz.add(exitMenu);

        jadaczMenuBar.add(menuJadacz);

        menuTools.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        menuTools.setText("Narz\u0119dzia");
        optionsMenu.setText("Opcje");
        optionsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsMenuActionPerformed(evt);
            }
        });

        menuTools.add(optionsMenu);

        jadaczMenuBar.add(menuTools);

        menuHelp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        menuHelp.setText("Pomoc");
        menuHelp.setAlignmentX(100.0F);
        aboutMenu.setText("O programie");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });

        menuHelp.add(aboutMenu);

        jadaczMenuBar.add(menuHelp);

        setJMenuBar(jadaczMenuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-341)/2, (screenSize.height-504)/2, 341, 504);
    }// </editor-fold>//GEN-END:initComponents
    public void serverConnect(){
                JXMLConfig cfg = JXMLConfig.getInstance();
                String[] server = new String[2];
                server = cfg.getProperty("server").split(":");
                System.out.println(server[0]+":"+Integer.valueOf(server[1]));
                try {

                    UserConnection uconn = new UserConnection(server[0],Integer.valueOf(server[1]),this.instance);
                    Registry.register("uconn",uconn);
                } catch (java.net.UnknownHostException uhe) {
                    uhe.printStackTrace();
                    javax.swing.JFrame frame = new javax.swing.JFrame();
                    javax.swing.JOptionPane.showMessageDialog(frame,
                                                              "B��dny adres serwera.\n Prosze poprawi� dane",
                                                              "B��d serwera",
                                                              javax.swing.JOptionPane.WARNING_MESSAGE);
                }catch(java.lang.NullPointerException npe){
                    npe.printStackTrace();
                    javax.swing.JFrame frame = new javax.swing.JFrame();
                    javax.swing.JOptionPane.showMessageDialog(frame,
                                                              "Nie mo�na po��czy� si� z serwerem.\n Prosze spr�bowa� p�niej",
                                                              "B��d po��czenia",
                                                              javax.swing.JOptionPane.WARNING_MESSAGE);
                }
    }
    private void setWindowAttribs(){
    
        java.awt.Point p = new java.awt.Point();
        // try to get form size, if fails set actual size
        try{
            p = (java.awt.Point) conf.getFrameAttrib("mainWindowSize");
            this.setSize(p.x,p.y);
        }catch(java.lang.NullPointerException npe){
            conf.setProperty("mainWindowSize",this.getWidth()+","+this.getHeight());
        }
        // try to get form location, if fails set actual location
        try{
            p = (java.awt.Point) conf.getFrameAttrib("mainWindowLocation");
            this.setLocation(p.x,p.y);
        }catch(java.lang.NullPointerException npe2){
            conf.setProperty("mainWindowLocation",this.getX()+","+this.getY());
        }
    
}
    private void sendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileActionPerformed
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser("");
            fc.showDialog(frame,null);
            java.io.File file = fc.getSelectedFile();
            System.out.println(file.getName());
    }//GEN-LAST:event_sendFileActionPerformed
    
    private java.io.File saveFileActionPerformed(java.awt.event.ActionEvent evt) {                                         
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser("");
            fc.showDialog(frame,null);
            java.io.File file = fc.getSelectedFile();
            return file;
    }  
    /*
     * Ation performed when double click on user window
     * @see openTalkWindow()
     * @param java.awt.event.MouseEvent
     * @return void;
     */
    private void userListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userListMouseClicked
        
        if(evt.getClickCount() >= 2){
            
            openTalkWindow();

        }
    }//GEN-LAST:event_userListMouseClicked
    private void openTalkWindow(){
        
            try{
                talkForm tf = (talkForm) talklist.get(clist.getContact(userList.getSelectedIndex()).getJID());
                tf.setVisible(true);
                
            }catch(java.lang.NullPointerException npe){
                System.out.println(clist.getContact(userList.getSelectedIndex()).getJID());
                talkForm tf = new talkForm();
                Contact user = clist.getByJID(clist.getContact(userList.getSelectedIndex()).getJID());
                tf.setUser(user);
                tf.setVisible(true); 
                talklist.put(user.getJID(),tf);
            }
        
    }
    private java.awt.Image setIconImage(){
        
        java.io.File ico = new java.io.File("./icon.gif");
        return java.awt.Toolkit.getDefaultToolkit().getImage(ico.getAbsolutePath());
        
    }
    private void saveWindowsAttribs(){
        conf.setProperty("mainWindowSize",this.getWidth()+","+this.getHeight());
        conf.setProperty("mainWindowLocation",this.getX()+","+this.getY());
        conf.storeToXML();  
    }
    /*
     * Action taken on window closing
     * @autor Dariusz "Cyber" Zon
     * @param WindowsEvent
     * @return void
     */
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // saving window position and location
        saveWindowsAttribs();
    }//GEN-LAST:event_formWindowClosed

    private void delUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delUserActionPerformed
       UserConnection uconn = (UserConnection) Registry.registry("uconn");
       Contact cnt = new Contact(userList.getSelectedIndex()," ");
       uconn.sendContactChange(cnt,UserConnection.ccDELETE);
       clist.remove(userList.getSelectedIndex());
       listModel.remove(userList.getSelectedIndex());
        try {
            clist.saveToCCF();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_delUserActionPerformed
    /*
     * Opening new talk window
     * @autor Dariusz "Cyber" Zon
     * @param WindowsEvent
     * @return void
     */
    private void sendMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgActionPerformed
       // Object user = userList.getSelectedValue();
       // Object userjid = userList.getSelectedIndex();
        openTalkWindow();
      //  System.out.println(user);
    }//GEN-LAST:event_sendMsgActionPerformed

    private void statusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusMouseExited
            
    }//GEN-LAST:event_statusMouseExited

    private void statusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusMouseClicked

        new statusForm().setVisible(true);
       
    }//GEN-LAST:event_statusMouseClicked
    /*
     * Action taken on window closing by exit menu
     * @autor Dariusz "Cyber" Zon
     * @param WindowsEvent
     * @return void
     */
    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        // try to close window if fails print error to standard output
        try{
            saveWindowsAttribs();
            System.exit(0);
        }catch(SecurityException se){
            System.out.println("Application error: " + se);
        }
    }//GEN-LAST:event_exitMenuActionPerformed
    /*
     * 
     * @autor Dariusz "Cyber" Zon
     * @param WindowsEvent
     * @return void
     */
    private void addContactMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContactMenuActionPerformed
        new addContactForm().setVisible(true);
    }//GEN-LAST:event_addContactMenuActionPerformed

    private void myDataMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myDataMenuActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_myDataMenuActionPerformed
    
    private void optionsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuActionPerformed
        new configForm().setVisible(true);
    }//GEN-LAST:event_optionsMenuActionPerformed

    private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuActionPerformed
     new AboutForm().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_aboutMenuActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainGUI().setVisible(true);
            }

        });
    }
    public void fillUserList(){
        clist.loadFromCCF("contact.list");
        listModel.removeAllElements();
        for(int i=0; i< clist.size(); ++i){
            listModel.addElement(clist.getContact(i).getName());
        }
    }
    /*
      *
    private static Object[] fillUserList(){
        clist = JContact.getInstance();
        clist.loadFromCCF("contact.list");
        return clist.getNames();
    }*/
    /*
     * od baktiego
     *
     */
    public void setFileRequest(FileInfo _file){
            javax.swing.JFrame frame = new javax.swing.JFrame();
            Object[] options = {"Tak",
                                "Nie"};
            int n = javax.swing.JOptionPane.showOptionDialog(frame,
                "U�ytkownik przesy�a plik: "+_file.getFilename()+"\n o rozmiarze: "+_file.getFilesize(),
                "Odbierz plik",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            System.out.println(n);
            if(n == 0){
                new FileSending(_file,0).setVisible(true);
            }else{
                UserConnection ucon = (UserConnection) Registry.registry("uconn");
                ucon.sendFileInfoResponse(Packet.TYPE_FILE_SEND_REJECT,_file);
            }
    }
    
    public void  setSystemInfo(){
        
    }

    public void setMessage(Message _msg){
        Contact user = null;
        try{
            user = clist.getByJID(_msg.getJID());
            talkForm tf = new talkForm();
            tf.setUser(user);
            tf.setVisible(true);
            tf.putMessage(_msg.getContent());
            talklist.put(user.getJID(),tf);
        }catch(java.lang.NullPointerException npe){
            user = new Contact(_msg.getJID(),"->UNKNOW<-","","",0,false);
            talkForm tf = new talkForm();
            tf.setUser(user);
            tf.setVisible(true);
            tf.putMessage(_msg.getContent());    
            talklist.put(user.getJID(),tf);
        }
        
    }
    public void setStatusChange(Status status){
        Contact cnt = clist.getByJID(status.getJID());
        cnt.setStatus(status);
    }
    
    public void setLoginStatus(boolean status) {
        if(status == true){
            
        }else{
            new LoginForm().setVisible(true);
        }
    }
  
    
    public void setContact(Contact contact) {
        if(clist.getByJID(contact.getJID()) != null){
            
            
        }else{
           // addContact
        }
    }
    public void setRegisteredJID(int _jid){
        if(_jid == 0){
            javax.swing.JFrame frame = new javax.swing.JFrame();
            javax.swing.JOptionPane.showMessageDialog(frame,
                                                      "Wyst�pi� b��d podczas rejestrowania,\n prosze spr�bowa� p�niej.",
                                                      "B��d rejestracji",
                                                       javax.swing.JOptionPane.WARNING_MESSAGE);
            new RegistrationForm().setVisible(true);
            conf.setProperty("userpass","");
        }else{
            conf.setProperty("userjid",String.valueOf(_jid));
            serverConnect();
        }
        
        
    }
    public static mainGUI getInstance(){
        return instance;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenu;

    public void setVisible(boolean b) {
    }
    private javax.swing.JMenuItem addContactMenu;
    private javax.swing.JMenuItem delUser;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar jadaczMenuBar;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuJadacz;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem myDataMenu;
    private javax.swing.JMenuItem optionsMenu;
    private javax.swing.JMenuItem sendFile;
    private javax.swing.JMenuItem sendMsg;
    private javax.swing.JLabel status;
    private javax.swing.JToolBar statusBar;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JList userList;
    private javax.swing.JPopupMenu userMenu;
    // End of variables declaration//GEN-END:variables
    
}
