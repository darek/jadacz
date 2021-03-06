/*
 * talkForm.java
 *
 * Created on 1 luty 2007, 10:27
 */

package jadacz;

import jadacz.lib.Contact;
import jadacz.lib.Message;
import jadacz.lib.Status;
import java.awt.Color;

/**
 *
 * @author  Cyber
 */
public class talkForm extends javax.swing.JFrame {
    private static JXMLConfig conf = null;
    private Contact user = null;
    private boolean isRegistered = false;
    private String talk = " ";
    private static String jid;
    private boolean isbold = false;
    private boolean isunderline = false;
    private boolean isitalic = false;
    private jadacz.lib.Connection connect = null;
    private static UserConnection uconn = (UserConnection) Registry.registry("uconn");
    
    /** Creates new form talkForm */
    public talkForm() {
        initComponents();
        setWindowAttrib();
        talkSplitPane.setDividerLocation(200);
    }
    public talkForm(Contact _user) {
        initComponents();
        setWindowAttrib();
        setUser(_user);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        talkSplitPane = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        talkScrollPane = new javax.swing.JScrollPane();
        talkTextPane = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        sendMsg = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        setBold = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        setItalic = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        setUnderline = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        cleanTalk = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        addUser = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        writeTextPane = new javax.swing.JEditorPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        charCount = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        userName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        statusDesc = new javax.swing.JTextPane();

        setIconImage(setIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindow(evt);
            }
        });

        talkSplitPane.setDividerLocation(this.getHeight()-40);
        talkSplitPane.setDividerLocation(140);
        talkSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPanel1.setLayout(new java.awt.BorderLayout());

        talkScrollPane.setAutoscrolls(true);
        talkTextPane.setCaretColor(new java.awt.Color(102, 102, 102));
        talkTextPane.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        talkTextPane.setEnabled(false);
        talkTextPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                talkTextPaneHyperlinkUpdate(evt);
            }
        });

        talkScrollPane.setViewportView(talkTextPane);

        jPanel1.add(talkScrollPane, java.awt.BorderLayout.CENTER);

        talkSplitPane.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        sendMsg.setText("Wy\u015blij");
        sendMsg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgActionPerformed(evt);
            }
        });

        jPanel2.add(sendMsg, java.awt.BorderLayout.EAST);

        jToolBar1.setBorderPainted(false);
        jToolBar1.setFocusable(false);
        setBold.setFont(new java.awt.Font("Tahoma", 1, 18));
        setBold.setForeground(java.awt.Color.lightGray);
        setBold.setText(" B ");
        setBold.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setBoldMouseClicked(evt);
            }
        });

        jToolBar1.add(setBold);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setEnabled(false);
        jSeparator1.setMaximumSize(new java.awt.Dimension(5, 214123123));
        jSeparator1.setMinimumSize(new java.awt.Dimension(15, 0));
        jSeparator1.setPreferredSize(new java.awt.Dimension(1, 0));
        jToolBar1.add(jSeparator1);

        setItalic.setFont(new java.awt.Font("Tahoma", 2, 18));
        setItalic.setForeground(java.awt.Color.lightGray);
        setItalic.setText(" I ");
        setItalic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setItalicMouseClicked(evt);
            }
        });

        jToolBar1.add(setItalic);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(5, 32767));
        jToolBar1.add(jSeparator2);

        setUnderline.setFont(new java.awt.Font("Tahoma", 0, 18));
        setUnderline.setForeground(java.awt.Color.lightGray);
        setUnderline.setText(" U ");
        setUnderline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setUnderlineMouseClicked(evt);
            }
        });

        jToolBar1.add(setUnderline);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setMaximumSize(new java.awt.Dimension(5, 32767));
        jToolBar1.add(jSeparator3);

        cleanTalk.setText("Wyczy\u015b\u0107");
        cleanTalk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cleanTalkMouseClicked(evt);
            }
        });
        cleanTalk.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cleanTalkAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jToolBar1.add(cleanTalk);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setMaximumSize(new java.awt.Dimension(5, 32767));
        jToolBar1.add(jSeparator5);

        addUser.setFont(new java.awt.Font("Tahoma", 1, 18));
        addUser.setForeground(new java.awt.Color(0, 153, 0));
        addUser.setText("+");
        addUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUserMouseClicked(evt);
            }
        });

        jToolBar1.add(addUser);

        jPanel2.add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        writeTextPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                writeTextPaneKeyPressed(evt);
            }
        });

        jScrollPane2.setViewportView(writeTextPane);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Liter:");
        jPanel3.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel3.add(charCount, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jSeparator4, java.awt.BorderLayout.WEST);

        talkSplitPane.setRightComponent(jPanel2);

        getContentPane().add(talkSplitPane, java.awt.BorderLayout.CENTER);
        talkSplitPane.getAccessibleContext().setAccessibleName("talkForm");

        jLabel3.setText("Status:");

        status.setText("Offline/Online");

        jLabel5.setText("Rozm\u00f3wca: ");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusDesc.setBackground(new java.awt.Color(236, 234, 234));
        statusDesc.setBorder(null);
        statusDesc.setEditable(false);
        jScrollPane3.setViewportView(statusDesc);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel3)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                        .add(userName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE)
                        .add(status, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(status)
                    .add(userName))
                .add(12, 12, 12)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void addUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserMouseClicked
        if(!(this.isRegistered)){
            new addContactForm(user.getJID()).setVisible(true);
        }
    }//GEN-LAST:event_addUserMouseClicked
    
    private void talkTextPaneHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_talkTextPaneHyperlinkUpdate
        
    }//GEN-LAST:event_talkTextPaneHyperlinkUpdate
    
    private void cleanTalkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cleanTalkMouseClicked
        this.talk = "";
        this.talkTextPane.setText(talk);
    }//GEN-LAST:event_cleanTalkMouseClicked
    
    private void cleanTalkAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cleanTalkAncestorAdded
// TODO add your handling code here:
    }//GEN-LAST:event_cleanTalkAncestorAdded
    
    private void setUnderlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setUnderlineMouseClicked
        if(!isunderline){
            isunderline = true;
            setUnderline.setForeground(java.awt.Color.BLACK);
        }else{
            isunderline = false;
            setUnderline.setForeground(java.awt.Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_setUnderlineMouseClicked
    
    private void setItalicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setItalicMouseClicked
        if(!isitalic){
            isitalic = true;
            setItalic.setForeground(java.awt.Color.BLACK);
        }else{
            isitalic = false;
            setItalic.setForeground(java.awt.Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_setItalicMouseClicked
    
    private void sendMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgActionPerformed
        if(writeTextPane.getText().length()>0){
            Message msg = new Message();
            msg.setContent(getFormatedWriteTextPane());
            System.out.println(user.getJID());
            msg.setJID(user.getJID());
            if(uconn.sendMessage(msg) == 0){
                talk += "<div style=\"color:#c22323; text-weight:bold;\">Nie uda�o si� wys�a� wiadomo�ci :(. </div><div style=\"color: #cccccc;\">"+ getFormatedWriteTextPane() + "</div><hr>";
            }else{
                talk +=  getFormatedWriteTextPane() + "<hr>";
            }
            talkTextPane.setText(talk);
            writeTextPane.setText("");
        }
    }//GEN-LAST:event_sendMsgActionPerformed
    
    private void writeTextPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_writeTextPaneKeyPressed
        
        if(evt.getKeyCode() == evt.VK_ENTER && writeTextPane.getText().length()>0){
            Message msg = new Message();
            msg.setContent(getFormatedWriteTextPane());
            msg.setJID(user.getJID());
            if(uconn.sendMessage(msg) == 0){
                talk += "<span style=\"color:#c22323; text-weight:bold;\"> Nie uda�o si� wys�a� wiadomo�ci :(. </span><span style=\"color: #cccccc;\">"+ getFormatedWriteTextPane() + "</span><hr>";
            }else{
                talk +=  getFormatedWriteTextPane() + "<hr>";
            }
            talkTextPane.setText(talk);
            writeTextPane.setText("");
            evt.consume();
        }else if(evt.getKeyCode() == evt.VK_ENTER && writeTextPane.getText().length()==0){
            writeTextPane.setText("");
            evt.consume();
        }
        String num = String.valueOf(writeTextPane.getText().length());
        charCount.setText(num);
    }//GEN-LAST:event_writeTextPaneKeyPressed
    
    private void closeWindow(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeWindow
        SaveWindowAttibutes();
        this.setVisible(false);
    }//GEN-LAST:event_closeWindow
    
    private void setBoldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setBoldMouseClicked
        if(!isbold){
            isbold = true;
            setBold.setForeground(java.awt.Color.BLACK);
        }else{
            isbold = false;
            setBold.setForeground(java.awt.Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_setBoldMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new talkForm().setVisible(true);
            }
        });
    }
    private void SaveWindowAttibutes() {
        conf.setProperty("talkFormPosition",this.getX()+","+this.getY());
        conf.setProperty("talkFormSize",this.getWidth()+","+this.getHeight());
        
    }
    private java.awt.Image setIconImage(){
        java.io.File ico = new java.io.File("./icon_talk.gif");
        return java.awt.Toolkit.getDefaultToolkit().getImage(ico.getAbsolutePath());
    }
    private String getFormatedWriteTextPane() {
        String text = writeTextPane.getText();
      /*  if(text.("(http:\/\/([\w.]+\/?)\S*)")){
            text = "<a href=\""+text+"\">"+text+"</a>";
       
        }*/
        if(isunderline){
            text = "<u>"+text+"</u>";
        }
        if(isitalic){
            text = "<i>"+text+"</i>";
        }
        if(isbold){
            text = "<b>"+text+"</b>";
        }
        return text;
    }
    public void setJid(String _jid){
        this.jid = _jid;
    }
    public void setUser(int i){
        
    }
    public void setUser(Contact _user){
        user = _user;
        
        if(user.getName().equals("->UNKNOW<-")){
            this.isRegistered = false;
            addUser.setEnabled(true);
            user.setName("Nieznany");
        }else{
            this.isRegistered = true;
            addUser.setEnabled(false);
           try{
             this.statusDesc.setText(user.getStatus().getDescription());
             this.status.setText(StatToString(_user.getStatus().getType()));
           }catch(java.lang.NullPointerException npe){ }
        }
        this.setTitle(user.getName() + "("+user.getJID()+")");
        this.userName.setText(user.getName());
    }
    public void putMessage(String _message){
        talk += "<b>"+user.getName()+":</b> "+ _message + "<hr>";
        this.talkTextPane.setText(talk);
        this.talkTextPane.setCaretPosition( this.talkTextPane.getDocument().getLength() );
    }
    public talkForm getInstance(){
        return this;
    }
    private void setWindowAttrib(){
        conf = JXMLConfig.getInstance();
        java.awt.Point p = new java.awt.Point();
        try{
            p = (java.awt.Point) conf.getFrameAttrib("talkFormPosition");
            this.setLocation(p.x,p.y);
        }catch(java.lang.NullPointerException npe){
            System.out.println(npe);
            conf.setProperty("talkFormPosition",this.getX()+","+this.getY());
        }
        try{
            p = (java.awt.Point) conf.getFrameAttrib("talkFormSize");
            this.setSize(p.x,p.y);
        }catch(java.lang.NullPointerException npe){
            System.out.println(npe);
            conf.setProperty("talkFormSize",this.getWidth()+","+this.getHeight());
        }
        talkSplitPane.setDividerLocation(this.getHeight()-140);
        talkTextPane.setContentType("text/html");
        
    }
    /**
     * changing status
     * @param _status
     */
    public void changeStatus(Status _status){
        user.setStatus(_status);
        this.statusDesc.setText(_status.getDescription());
        
        
        this.status.setText(StatToString(_status.getType()));
    }
    private String StatToString(byte _statype){
        String status = " ";
        switch(_statype){
            case 1: status = "Niedost�pny";  this.status.setForeground(Color.RED); break;
            case 2: status = "Niedost�pny";  this.status.setForeground(Color.RED); break;
            case 3: status = "Dost�pny";   this.status.setForeground(Color.GREEN); break;
            case 4: status = "Dost�pny";  this.status.setForeground(Color.GREEN); break;
            case 5: status = "Zaraz wracam "; this.status.setForeground(Color.BLUE); break;
            case 6: status = "Zaraz wracam";  this.status.setForeground(Color.BLUE); break;
        }
        return status;
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addUser;
    private javax.swing.JLabel charCount;
    private javax.swing.JLabel cleanTalk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton sendMsg;
    private javax.swing.JLabel setBold;
    private javax.swing.JLabel setItalic;
    private javax.swing.JLabel setUnderline;
    private javax.swing.JLabel status;
    private javax.swing.JTextPane statusDesc;
    private javax.swing.JScrollPane talkScrollPane;
    private javax.swing.JSplitPane talkSplitPane;
    private javax.swing.JTextPane talkTextPane;
    private javax.swing.JLabel userName;
    private javax.swing.JEditorPane writeTextPane;
    // End of variables declaration//GEN-END:variables
    
}
/*
  talkTextPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {

	public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent hle) {
		if (hle.getEventType()!=javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
			return;   
        }
 		java.net.URL url = hle.getURL();
			System.out.println("Hyperlink event "+hle+" url="+url);
                 
        try {
			//Let's try to open a browser
            BrowserLauncher.openURL(url.toString());
            return;
        } catch (java.io.IOException e) {
			System.out.println("Unable to open browser - using internal, inferior browsing");
        }
                 

 	}
             
});

 */
