/*
 * statusForm.java
 *
 * Created on 1 luty 2007, 01:17
 */

package jadacz;

import jadacz.lib.Status;

/**
 * Set up status and description
 * @author  Cyber
 */
public class statusForm extends javax.swing.JFrame {
    private static JXMLConfig conf = null;
    /** Creates new form statusForm */
    public statusForm() {
        initComponents();
        conf = (JXMLConfig) Registry.registry("conf");
        System.out.println(conf);
        java.awt.Point p = new java.awt.Point();
        try{
            p = (java.awt.Point) conf.getFrameAttrib("statusFormLocation");
            this.setLocation(p.x,p.y);
        }catch(java.lang.NullPointerException npe){
            //System.out.println(npe);
            conf.setProperty("statusFormLocation",this.getX()+","+this.getY());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        scroolPane = new javax.swing.JScrollPane();
        descArray = new javax.swing.JTextArea();
        myStatus = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setTitle("Ustaw status");
        setIconImage(setIconImage());
        scroolPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descArray.setColumns(20);
        descArray.setRows(5);
        descArray.setBorder(null);
        scroolPane.setViewportView(descArray);

        myStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Niedost\u0119pny", "Dost\u0119pny", "Zaraz wracam", "Ukryty", "Do pogadania" }));
        myStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        okButton.setText("OK");
        okButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Anuluj");
        cancelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(myStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(scroolPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 271, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(myStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scroolPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 17, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Set up status and send it to serwer
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        boolean istat = false;
        byte statype = 1;
        if(this.descArray.getText().length()>0){
            istat = true;
        }
        System.out.println(this.myStatus.getSelectedIndex());
        switch(this.myStatus.getSelectedIndex()){
            case 0: 
                if(istat){ statype = Status.TYPE_NOT_AVAILABLE_DESCR; }else statype = Status.TYPE_NOT_AVAILABLE; break;
            case 1: 
                if(istat){ statype = Status.TYPE_AVAILABLE_DESCR; }else statype = Status.TYPE_AVAILABLE; break;
            case 2:
                if(istat){ statype = Status.TYPE_BUSY_DESCR; }else statype = Status.TYPE_BUSY; break;
            case 3:
                if(istat){ statype = Status.TYPE_HIDDEN_DESCR; }else statype = Status.TYPE_HIDDEN; break;
            case 4:
                statype = Status.TYPE_EAT_ME; break;
        }
        System.out.println("Status" + statype);
        UserConnection uconn = (UserConnection) Registry.registry("uconn");
        Status status = new Status();
        status.setJID(Integer.valueOf(conf.getProperty("userjid","0")));
        status.setType(statype);
        if(istat){
            status.setDescription(this.descArray.getText());
        }else{
            status.setDescription(" ");
        }
        System.out.println("Status: \n "+ status.toString());
        uconn.sendStatus(status);
        System.out.println(conf);
        
        this.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed
    /**
     * Hide window
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new statusForm().setVisible(true);
            }
        });
    }
    /** Set up window icon
     */
    private java.awt.Image setIconImage(){
        
        java.io.File ico = new java.io.File("./icon.gif");
        return java.awt.Toolkit.getDefaultToolkit().getImage(ico.getAbsolutePath());
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea descArray;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox myStatus;
    private javax.swing.JButton okButton;
    private javax.swing.JScrollPane scroolPane;
    // End of variables declaration//GEN-END:variables
    
}
