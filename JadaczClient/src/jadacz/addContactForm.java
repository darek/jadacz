/*
 * addContactForm.java
 *
 * Created on 17 stycze� 2007, 01:38
 */

package jadacz;

import jadacz.lib.Contact;
import java.awt.Graphics;
import java.io.IOException;

/**
 *
 * @author  Cyber
 */
public class addContactForm extends javax.swing.JFrame {
    private static JXMLConfig conf = null;
    private static String fixed_jid = null;
    /** Creates new form addContactForm */
    public addContactForm() {
        initComponents();
        setWindowAttrib();
    }
    public addContactForm(int _jid) {
        initComponents();
        JIDNumber.setText(String.valueOf(_jid));
        setWindowAttrib();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        shownName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JIDNumber = new javax.swing.JTextField();
        cancelButton = new javax.swing.JToggleButton();
        okButton = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Dodaj kontakt");
        setIconImage(setIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                savePosition(evt);
            }
        });

        shownName.setFont(new java.awt.Font("Tahoma", 0, 12));
        shownName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nazwa wy\u015bwietlana:");

        jLabel2.setText("Numer JID:");

        JIDNumber.setFont(new java.awt.Font("Tahoma", 0, 12));
        JIDNumber.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cancelButton.setText("Anuluj");
        cancelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Wpisz nazw\u0119 oraz numer identyfikacyjny u\u017cytkownika");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel2)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 22, Short.MAX_VALUE)
                                .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(shownName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(JIDNumber, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)))
                    .add(jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 34, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(shownName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(JIDNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(okButton))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Save window position, called when window is closing
     */
    private void savePosition(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_savePosition
        conf.setProperty("addContactLocation",this.getX()+","+this.getY());
    }//GEN-LAST:event_savePosition
    /*
     * Close window
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed
    /**
     * Save new contact to serwer database and user list
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if(JIDNumber.getText().length()<=0 || this.shownName.getText().length() <= 0){
            //
        }else{
            JContact cnt = JContact.getInstance();
            UserConnection uconn = (UserConnection) Registry.registry("uconn");
            Contact contact = new Contact();
            contact.setJID(Integer.valueOf(this.JIDNumber.getText()));
            contact.setName(this.shownName.getText());
            uconn.sendContactChange(contact,UserConnection.ccADD);
            cnt.addContact(contact);
            //cnt.saveToCCF();
            mainGUI mg = mainGUI.getInstance();
            mg.fillUserList();
        }
        this.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addContactForm().setVisible(true);
            }
        });
    }
    /**
     * Set up window attrib, if not set use actual position and size
     */
    public void setWindowAttrib(){
        conf = JXMLConfig.getInstance();
        System.out.println(conf);
        java.awt.Point p = new java.awt.Point();
        try{
            p = (java.awt.Point) conf.getFrameAttrib("addContactLocation");
            this.setLocation(p.x,p.y);
        }catch(java.lang.NullPointerException npe){
            System.out.println(npe);
            conf.setProperty("addContactLocation",this.getX()+","+this.getY());
        }
    }
    /**
     * Set up window icon
     */
    private java.awt.Image setIconImage(){
        
        java.io.File ico = new java.io.File("./icon.gif");
        return java.awt.Toolkit.getDefaultToolkit().getImage(ico.getAbsolutePath());
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JIDNumber;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToggleButton okButton;
    private javax.swing.JTextField shownName;
    // End of variables declaration//GEN-END:variables
    
}