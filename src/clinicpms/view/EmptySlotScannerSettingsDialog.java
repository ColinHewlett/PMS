/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.view;

import clinicpms.controller.EntityDescriptor;
import clinicpms.controller.ViewController;
import clinicpms.view.interfaces.IView;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.time.Duration;
import java.time.LocalDate;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author colin
 */
public class EmptySlotScannerSettingsDialog extends AppointmentViewDialog{
    private EntityDescriptor entityDescriptor = null;
    private ActionListener myController = null;

    /**
     * 
     * @param controller ActionListener and constructor of the dialog
     * @param ed EntityDescriptor enables communication of dialog settings back to the controller
     * @param owner Frame which references the dialog's parent
     */
    public EmptySlotScannerSettingsDialog(
            ActionListener myController, 
            EntityDescriptor ed, 
            Frame owner) {
        super(owner, true);
        setTitle("Empty slot criteria editor" );
        initComponents();
        this.cmbSelectSlotDuration.setRenderer(new SelectSlotDurationRenderer());
        this.buttonGroup1.add(this.rdbSelectMonths);
        this.buttonGroup1.add(this.rdbSelectWeeks);
        this.rdbSelectWeeks.setSelected(true);
        
        cmbSelectSlotDurationActionPerformed(null);

        initialiseDialogClosing();
        setEntityDescriptor(ed);
        setMyController(myController);
    }
    
    public void initialiseView(){
        
    }
    
    public EntityDescriptor getEntityDescriptor(){
        return entityDescriptor;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e){
        
        setEntityDescriptor((EntityDescriptor)e.getNewValue());
        /*
        if (e.getPropertyName().equals(
                ViewController.AppointmentViewDialogPropertyEvent.APPOINTMENT_RECEIVED.toString())){
            initialiseViewFromED();
        }
        */
    }
    private void setEntityDescriptor(EntityDescriptor value){
        entityDescriptor = value;
    }

    private ActionListener getMyController(){
         return this.myController;
     }
    private void setMyController(ActionListener value){
        this.myController = value;
    }
   /**
     * The configured window listener is entered when [1] the cancel button is clicked,
     * which despatches a WINDOW_CLOSING event, and [2] when the dialog window "X" is clicked 
     * On entry to listener 
     * -> if dialog default closing behaviour is DO_NOTHING_ON_CLOSE, which is configured during dialog construction, user is prompted to confirm the closing of dialog
     * ->-> on receipt of user confirmation an APPOINTMENT_VIEW_CLOSE_REQUEST action event is sent to the view controller
     * Its the responsibility of the view controller to re-configure the dialog to DISPOSE_ON_CLOSE, and then despatch a WINDOW_CLOSING  event 
     * 
     */
    private void initialiseDialogClosing(){
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (EmptySlotScannerSettingsDialog.this.getDefaultCloseOperation()==JDialog.DO_NOTHING_ON_CLOSE){
                    if (checkOKToCloseDialog()==JOptionPane.YES_OPTION){
                        EmptySlotScannerSettingsDialog.this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        ActionEvent actionEvent = new ActionEvent(EmptySlotScannerSettingsDialog.this,
                                ActionEvent.ACTION_PERFORMED,
                                ViewController.EmptySlotSearchCriteriaDialogActionEvent.
                                        EMPTY_SLOT_SCANNER_CLOSE_REQUEST.toString());
                        EmptySlotScannerSettingsDialog.this.getMyController().actionPerformed(actionEvent);
                    }
                } 
            }
        });
    }
    
    private int checkOKToCloseDialog(){
        String[] options = {"Yes", "No"};
        int close = JOptionPane.showOptionDialog(this,
                        "Changed settings will be lost. Cancel anyway?",null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        null);
        return close;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbSelectSlotDuration = new javax.swing.JComboBox<Duration>();
        jPanel4 = new javax.swing.JPanel();
        spnSlotSearchOffset = new javax.swing.JSpinner();
        rdbSelectMonths = new javax.swing.JRadioButton();
        rdbSelectWeeks = new javax.swing.JRadioButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(266, 144));

        jLabel2.setText("Slot minimum duration");

        cmbSelectSlotDuration.setModel(new javax.swing.DefaultComboBoxModel<>(new Duration[] {
            Duration.ofMinutes(0),
            Duration.ofMinutes(5),
            Duration.ofMinutes(10),
            Duration.ofMinutes(15),
            Duration.ofMinutes(20),
            Duration.ofMinutes(25),
            Duration.ofMinutes(30),
            Duration.ofMinutes(35),
            Duration.ofMinutes(40),
            Duration.ofMinutes(45),
            Duration.ofMinutes(50),
            Duration.ofMinutes(55),
            Duration.ofMinutes(60),
            Duration.ofMinutes(120),
            Duration.ofMinutes(180),
            Duration.ofMinutes(240),
            Duration.ofMinutes(300),
            Duration.ofMinutes(360),
            Duration.ofMinutes(420),
            Duration.ofMinutes(480)}));
cmbSelectSlotDuration.setBorder(javax.swing.BorderFactory.createEtchedBorder());
cmbSelectSlotDuration.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmbSelectSlotDurationActionPerformed(evt);
    }
    });

    jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Search start date offset by ..."));

    rdbSelectMonths.setText("month(s)");

    rdbSelectWeeks.setText("week(s)");

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(51, 51, 51)
            .addComponent(spnSlotSearchOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rdbSelectWeeks)
            .addGap(12, 12, 12)
            .addComponent(rdbSelectMonths)
            .addGap(7, 7, 7))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(spnSlotSearchOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rdbSelectMonths)
                .addComponent(rdbSelectWeeks))
            .addGap(10, 10, 10))
    );

    spnSlotSearchOffset.setModel(new SpinnerNumberModel(0,0,12,1));

    btnSave.setText("Start scan for empty slots");
    btnSave.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSaveActionPerformed(evt);
        }
    });

    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnSave)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(25, 25, 25))
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(12, 12, 12)
                    .addComponent(cmbSelectSlotDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(9, 9, 9)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(cmbSelectSlotDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSave)
                .addComponent(btnCancel))
            .addGap(18, 18, 18))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(15, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSelectSlotDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSelectSlotDurationActionPerformed
        if ((this.cmbSelectSlotDuration.getSelectedIndex()==0) ||
                (this.cmbSelectSlotDuration.getSelectedIndex()==-1)){
            this.rdbSelectMonths.setEnabled(false);
            this.rdbSelectWeeks.setEnabled(false);
            this.spnSlotSearchOffset.setEnabled(false);
            this.cmbSelectSlotDuration.setForeground(Color.red);
        }
        else{
            
            this.rdbSelectMonths.setEnabled(true);
            this.rdbSelectWeeks.setEnabled(true);
            this.spnSlotSearchOffset.setEnabled(true); 
            this.cmbSelectSlotDuration.setForeground(Color.black);
        }
    }//GEN-LAST:event_cmbSelectSlotDurationActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        Duration duration = (Duration)this.cmbSelectSlotDuration.getSelectedItem();
        LocalDate startScanDate = getEntityDescriptor().getRequest().getDay();
        if (!duration.isZero()){
            if(this.rdbSelectWeeks.isSelected()){
                startScanDate = startScanDate.plusWeeks((Integer)this.spnSlotSearchOffset.getValue());
            }
            else startScanDate = startScanDate.plusMonths((Integer)this.spnSlotSearchOffset.getValue());
            
            getEntityDescriptor().getRequest().setDay(startScanDate);
            getEntityDescriptor().getRequest().setDuration(duration);
            ActionEvent actionEvent = new ActionEvent(this,
                    ActionEvent.ACTION_PERFORMED,
                    ViewController.AppointmentViewControllerActionEvent.
                            APPOINTMENT_SLOTS_FROM_DATE_REQUEST.toString());
            this.getMyController().actionPerformed(actionEvent);
        }
        EmptySlotScannerSettingsDialog.this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        EmptySlotScannerSettingsDialog.this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Duration> cmbSelectSlotDuration;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton rdbSelectMonths;
    private javax.swing.JRadioButton rdbSelectWeeks;
    private javax.swing.JSpinner spnSlotSearchOffset;
    // End of variables declaration//GEN-END:variables
}
