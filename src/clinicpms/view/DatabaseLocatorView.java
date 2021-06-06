/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.view;

import clinicpms.controller.EntityDescriptor;
import clinicpms.controller.ViewController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author colin
 */
public class DatabaseLocatorView extends View {
    private JCheckBoxMenuItem mniLocateDatabase = null;
    private EntityDescriptor ed = null;
    private ActionListener myController = null;
    private InternalFrameAdapter internalFrameAdapter = null;
    /**
     * Creates new form DatabaseLocatorView
     */
    public DatabaseLocatorView(ActionListener myController, EntityDescriptor value) {
        this.myController = myController;
        this.ed = value;
        initComponents();
        mniLocateDatabase = new JCheckBoxMenuItem("Locate database");
        this.mnuAction.add(mniLocateDatabase);
        mniLocateDatabase.addActionListener((ActionEvent e) -> mniLocateDatabaseActionPerformed());
        String location = getEntityDescriptor().getRequest().getDatabaseLocation();
        if (location!=null){
            this.txtDatabaseLocation.setText(location);
        }
        else {
            txtDatabaseLocation.setText("<< currently undefined>>");
        }

    }
    
    public Viewer getMyViewType(){
        return null;
    }
    
    private ActionListener getMyController(){
        return this.myController;
    }
    
    @Override
    public void addInternalFrameClosingListener(){
        /**
         * Establish an InternalFrameListener for when the view is closed 
         */
        
        internalFrameAdapter = new InternalFrameAdapter(){
            @Override  
            public void internalFrameClosing(InternalFrameEvent e) {
                ActionEvent actionEvent = new ActionEvent(
                        DatabaseLocatorView.this,ActionEvent.ACTION_PERFORMED,
                        ViewController.DatabaseLocatorViewControllerActionEvent.DATABASE_LOCATOR_VIEW_CLOSED.toString());
                getMyController().actionPerformed(actionEvent);
            }
        };
        this.addInternalFrameListener(internalFrameAdapter);
    }

    @Override
    public EntityDescriptor getEntityDescriptor(){
        return ed;
    }
    
    private void setEntityDescriptor(EntityDescriptor value){
        this.ed = value;
    }
    
    @Override
    public void initialiseView(){
        /*
        int result = this.fchFileChooser.showOpenDialog(new JFrame());
        if (result == fchFileChooser.APPROVE_OPTION) {
            File selectedFile = fchFileChooser.getSelectedFile();
            this.txtDatabaseLocation.setText(selectedFile.getPath());
            this.mniLocateDatabase.setState(true);
        }
        else mniLocateDatabase.setState(false);
        */
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e){
        String propertyName = e.getPropertyName();
        if (propertyName.equals(ViewController.DatabaseLocatorViewPropertyEvent.DATABASE_LOCATION_RECEIVED.toString())){
            setEntityDescriptor((EntityDescriptor)e.getNewValue());
            String location = getEntityDescriptor().getRequest().getDatabaseLocation();
            if (location!=null)this.txtDatabaseLocation.setText(location);
            else txtDatabaseLocation.setText("<< database location currently undefined>>");
        }
    }
    
    private void mniLocateDatabaseActionPerformed(){
        int result = this.fchFileChooser.showOpenDialog(new JFrame());
        if (result == fchFileChooser.APPROVE_OPTION) {
            File selectedFile = fchFileChooser.getSelectedFile();
            this.txtDatabaseLocation.setText(selectedFile.getPath());
            this.mniLocateDatabase.setState(true);
        }
        else mniLocateDatabase.setState(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtDatabaseLocation = new javax.swing.JTextField();
        fchFileChooser = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        cmdSaveLocation = new javax.swing.JButton();
        cmdCloseDatabaseLocator = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuAction = new javax.swing.JMenu();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Database location"));

        txtDatabaseLocation.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDatabaseLocation)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDatabaseLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdSaveLocation.setText("Save location");
        cmdSaveLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveLocationActionPerformed(evt);
            }
        });

        cmdCloseDatabaseLocator.setText("Close Database Locator");
        cmdCloseDatabaseLocator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseDatabaseLocatorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdSaveLocation)
                .addGap(77, 77, 77)
                .addComponent(cmdCloseDatabaseLocator, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSaveLocation)
                    .addComponent(cmdCloseDatabaseLocator))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnuAction.setText("Action");
        jMenuBar1.add(mnuAction);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(fchFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(98, 98, 98))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fchFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSaveLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveLocationActionPerformed
        if (mniLocateDatabase.getState()){
            getEntityDescriptor().getRequest().setDatabaseLocation(this.txtDatabaseLocation.getText());
            ActionEvent actionEvent = new ActionEvent(this, 
                    ActionEvent.ACTION_PERFORMED,
                    ViewController.DatabaseLocatorViewControllerActionEvent.DATABASE_LOCATION_REQUEST.toString());
            this.getMyController().actionPerformed(actionEvent);
        }
    }//GEN-LAST:event_cmdSaveLocationActionPerformed

    private void cmdCloseDatabaseLocatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseDatabaseLocatorActionPerformed
        try{
            this.setClosed(true);
        }
        catch (PropertyVetoException e){
            
        }
    }//GEN-LAST:event_cmdCloseDatabaseLocatorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCloseDatabaseLocator;
    private javax.swing.JButton cmdSaveLocation;
    private javax.swing.JFileChooser fchFileChooser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenu mnuAction;
    private javax.swing.JTextField txtDatabaseLocation;
    // End of variables declaration//GEN-END:variables
}
