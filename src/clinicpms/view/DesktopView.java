/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.view;

import clinicpms.controller.ViewController.DesktopViewControllerActionEvent;
import clinicpms.controller.DesktopViewController;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author colin
 */
public class DesktopView extends javax.swing.JFrame{
    
    private DesktopViewController controller = null;
    private JMenuItem mniMigrationKeyIntegrityCheck = null;
    private JMenuItem mniMigratePatientDBFToCSV = null;
    private JMenuItem mniMigrateAppointmentDBFToCSV = null;
    private JMenuItem mniPatientView = null;
    private JMenuItem mniAppointmentView = null;
    private JMenuItem mniExitView = null;
    private WindowAdapter windowAdapter = null;  
    private InternalFrameAdapter internalFrameAdapter = null;

    private void initFrameClosure() {
        this.windowAdapter = new WindowAdapter() {
            // WINDOW_CLOSING event handler
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                /**
                 * When an attempt to close the view (user clicking "X")
                 * the view's controller is notified and will decide whether
                 * to call the view's dispose() method
                 */
                ActionEvent actionEvent = new ActionEvent(DesktopView.this, 
                        ActionEvent.ACTION_PERFORMED,
                        DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString());
                DesktopView.this.getController().actionPerformed(actionEvent);
            }
            // WINDOW_CLOSED event handler
            /**
             * This event occurs when the view's dispose() method is called by
             * the controller and causes the application to close
             */
            /*
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                // Close application if you want to with System.exit(0)
                // but don't forget to dispose of all resources 
                // like child frames, threads, ...
                System.exit(0);
            }
            */
        };

        // when you press "X" the WINDOW_CLOSING event is called but that is it
        // nothing else happens
        this.setDefaultCloseOperation(DesktopView.this.DO_NOTHING_ON_CLOSE);
        // don't forget this
        this.addWindowListener(this.windowAdapter);
    }
    /**
     * 
     * @param controller 
     */
    public DesktopView(DesktopViewController controller) { 
        this.controller = controller;
        initComponents();
        /**
         * initialise frame closure actions
         */
        initFrameClosure();
        /**
         * MENU initialisation
         */
        mniMigrateAppointmentDBFToCSV = new JMenuItem("DBF appointments to CSV");
        mniMigratePatientDBFToCSV = new JMenuItem("DBF patients to CSV");
        mniMigrationKeyIntegrityCheck = new JMenuItem("Key integrity check");
        mniPatientView = new JMenuItem("Patient");
        mniAppointmentView = new JMenuItem("Appointments");
        mniExitView = new JMenuItem("Exit The Clinic PMS");
        this.mnuView.add(mniPatientView);
        this.mnuView.add(mniAppointmentView);
        this.mnuView.add(new JSeparator());
        this.mnuView.add(mniMigrateAppointmentDBFToCSV);
        this.mnuView.add(mniMigratePatientDBFToCSV);
        this.mnuView.add(mniMigrationKeyIntegrityCheck);
        this.mnuView.add(new JSeparator());
        this.mnuView.add(mniExitView);

        mniMigrationKeyIntegrityCheck.addActionListener((ActionEvent e) -> mniMigrationKeyIntegrityCheckActionPerformed());
        mniMigrateAppointmentDBFToCSV.addActionListener((ActionEvent e) -> mniMigrateAppointmentDBFToCSVActionPerformed());
        mniMigratePatientDBFToCSV.addActionListener((ActionEvent e) -> mniMigratePatientDBFToCSVActionPerformed());
        mniPatientView.addActionListener((ActionEvent e) -> mniPatientViewActionPerformed());
        mniAppointmentView.addActionListener((ActionEvent e) -> mniAppointmentViewActionPerformed());
        mniExitView.addActionListener((ActionEvent e) -> mniExitViewActionPerformed());
        /*
        mniPatientView.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mniPatientViewActionPerformed(e);
            }
        });
        */
        /*
        mniExitView.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mniExitActionPerformed(e);
            }
        });
        */
        /*
        mniAppointmentView.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mniAppointmentViewActionPerformed();
            }
        });
        */
        setContentPaneForInternalFrame();
    }
    /*
    @Override
    public javax.swing.JDesktopPane getContentPane(){
        return deskTop;
    }
    */
    public javax.swing.JDesktopPane getDeskTop(){
        return deskTop;
    } 
    private void setContentPaneForInternalFrame(){
        setContentPane(deskTop);
    }
    
    public DesktopViewController getController(){
        return controller;
    }
    public void setController(DesktopViewController value){
        controller = value;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        deskTop = new javax.swing.JDesktopPane();
        mnbDesktop = new javax.swing.JMenuBar();
        mnuView = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout deskTopLayout = new javax.swing.GroupLayout(deskTop);
        deskTop.setLayout(deskTopLayout);
        deskTopLayout.setHorizontalGroup(
            deskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        deskTopLayout.setVerticalGroup(
            deskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        mnuView.setText("View");
        mnbDesktop.add(mnuView);

        setJMenuBar(mnbDesktop);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deskTop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deskTop)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane deskTop;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar mnbDesktop;
    private javax.swing.JMenu mnuView;
    // End of variables declaration//GEN-END:variables
    
    private void mniMigrateAppointmentDBFToCSVActionPerformed(){
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.MIGRATE_APPOINTMENT_DBF_TO_CSV.toString());
        this.getController().actionPerformed(actionEvent);
    }
    private void mniMigratePatientDBFToCSVActionPerformed(){
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.MIGRATE_PATIENT_DBF_TO_CSV.toString());
        this.getController().actionPerformed(actionEvent);
    }   
    private void mniMigrationKeyIntegrityCheckActionPerformed(){
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.MIGRATE_INTEGRITY_CHECK.toString());
        this.getController().actionPerformed(actionEvent);
    }
    private void mniAppointmentViewActionPerformed() {                                        
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.DESKTOP_VIEW_APPOINTMENTS_REQUEST.toString());
        String s;
        s = actionEvent.getSource().getClass().getSimpleName();
        this.getController().actionPerformed(actionEvent);
    }
    
    private void mniPatientViewActionPerformed() {                                                      
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.DESKTOP_VIEW_PATIENTS_REQUEST.toString());
        this.getController().actionPerformed(actionEvent);
    }

    private void mniExitViewActionPerformed() {  
        /**
         * Menu request to close view is routed to the view controller
         */
        ActionEvent actionEvent = new ActionEvent(this, 
                ActionEvent.ACTION_PERFORMED,
                DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString());
        DesktopView.this.getController().actionPerformed(actionEvent);
    }

}
