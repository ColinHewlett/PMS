/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.controller;

import clinicpms.controller.ViewController.PatientViewControllerActionEvent;
import clinicpms.controller.ViewController.PatientViewControllerPropertyEvent;
import clinicpms.controller.ViewController.DesktopViewControllerActionEvent;
import clinicpms.controller.EntityDescriptor;
import clinicpms.model.Appointment;
import clinicpms.model.Patient;
import clinicpms.model.Patients;
import clinicpms.view.PatientView;
import clinicpms.view.interfaces.IView;
import clinicpms.store.exceptions.StoreException;
import java.beans.PropertyChangeSupport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author colin
 */
public class PatientViewController extends ViewController {
    
    private ActionListener myController = null;
    private PropertyChangeSupport pcSupportForView = null;
    //private PropertyChangeSupport pcSupportForPatientSelector = null;
    private PropertyChangeEvent pcEvent = null;
    private PatientView view = null;
    private EntityDescriptor oldEntityDescriptor = new EntityDescriptor();
    private EntityDescriptor newEntityDescriptor = new EntityDescriptor();
    private EntityDescriptor entityDescriptorFromView = null;
    private JFrame owningFrame = null;
    private String message = null;

    
    private void cancelView(ActionEvent e){
        try{
            getView().setClosed(true);
            myController.actionPerformed(e);
        }
        catch (PropertyVetoException e1) {
            
        }
    }
    private RenderedAppointment renderAppointment(Appointment a){
        RenderedAppointment ra = new RenderedAppointment();
        for (AppointmentField af: AppointmentField.values()){
            switch(af){
                case KEY -> ra.setKey(a.getKey());
                case DURATION -> ra.setDuration(a.getDuration());
                case NOTES -> ra.setNotes(a.getNotes());
                case START -> ra.setStart(a.getStart());   
            }  
        }
        return ra;
    }
    private RenderedPatient renderPatient(Patient p){
        RenderedPatient result = null;
        if (p!=null){
            RenderedPatient vp = new RenderedPatient();
            for (PatientField pf: PatientField.values()){
                switch(pf){
                    case KEY -> vp.setKey(p.getKey());
                    case TITLE -> vp.setTitle((p.getName().getTitle()));
                    case FORENAMES -> vp.setForenames((p.getName().getForenames()));
                    case SURNAME -> vp.setSurname((p.getName().getSurname()));
                    case LINE1 -> vp.setLine1((p.getAddress().getLine1()));
                    case LINE2 -> vp.setLine2((p.getAddress().getLine2()));
                    case TOWN -> vp.setTown((p.getAddress().getTown()));
                    case COUNTY -> vp.setCounty((p.getAddress().getCounty()));
                    case POSTCODE -> vp.setPostcode((p.getAddress().getPostcode()));
                    case DENTAL_RECALL_DATE -> vp.setDentalRecallDate((p.getRecall().getDentalDate()));
                    case HYGIENE_RECALL_DATE -> vp.setHygieneRecallDate((p.getRecall().getHygieneDate()));
                    case DENTAL_RECALL_FREQUENCY -> vp.setHygieneRecallFrequency((p.getRecall().getDentalFrequency()));
                    case HYGIENE_RECALL_FREQUENCY -> vp.setDentalRecallFrequency((p.getRecall().getDentalFrequency()));
                    case DOB -> vp.setDOB((p.getDOB()));
                    case GENDER -> vp.setGender((p.getGender()));
                    case PHONE1 -> vp.setPhone1((p.getPhone1()));
                    case PHONE2 -> vp.setPhone2((p.getPhone2()));
                    case IS_GUARDIAN_A_PATIENT -> vp.setIsGuardianAPatient((p.getIsGuardianAPatient()));
                    case NOTES -> vp.setNotes((p.getNotes()));

                }
            }
            result = vp;
        }
        return result;
    }
    /**
     * A request for a collection of all patient records is processed
     * -- collection of all patients fetched from model
     * -- EntityDescriptor.Collection patients cleared
     * -- each model patient is serialised into EntityDescriptor.Patient and then
     * the EntityDescriptor.Patient added to the EntityDescriptor.Collection of
     * patients
     * -- note, the objects the model Patient encapsulates are not included in 
     * the serialisation
     * @throws StoreException passed up the line to caller to process
     */
    private void serialisePatientsToEDCollection(ArrayList<Patient> patients) throws StoreException{
        //fetch all patients on the system from the model
        
        getNewEntityDescriptor().getPatients().getData().clear();
        Iterator<Patient> patientsIterator = patients.iterator();
        while(patientsIterator.hasNext()){       
            getNewEntityDescriptor().setPatient(new EntityDescriptor().getPatient());
            Patient patient = patientsIterator.next();
            RenderedPatient p = renderPatient(patient);
            getNewEntityDescriptor().getPatient().setData(p);
            getNewEntityDescriptor().getPatients().getData().add(getNewEntityDescriptor().getPatient());
        }
    }
    /**
     * A request for a patient object is processed
     * -- the selected patient's key is fetched from the EntityDescriptor.Selection.Patient object
     * -- the model patient with this key is fetched and serialised into the 
     * EntityDescriptor.Patient object
     * @throws StoreException 
     */
    private void serialisePatientToEDPatient(Patient patient) throws StoreException{
        RenderedPatient p = renderPatient(patient);
        getNewEntityDescriptor().getPatient().setData(p);
        if (patient.getIsGuardianAPatient()){
            if (patient.getGuardian() != null){
                RenderedPatient g = renderPatient(patient.getGuardian());
                getNewEntityDescriptor().getPatientGuardian().setData(g);  
            }
        }
        ArrayList<Appointment> appointments;
        if (patient.getAppointmentHistory().getDentalAppointments().size() > 0){
            appointments = patient.getAppointmentHistory().getDentalAppointments();
            serialisePatientAppointmentHistory(appointments);
        }
        /*
        if (patient.getAppointmentHistory().getHygieneAppointments().size() > 0){
            appointments = patient.getAppointmentHistory().getDentalAppointments();
            serialisePatientAppointmentHistory(appointments);
        }
        */
    }
    private void serialisePatientAppointmentHistory(ArrayList<Appointment> appointments){
        EntityDescriptor ed = null;
        ArrayList<EntityDescriptor.Appointment> xx = new ArrayList<>();
        Iterator<Appointment> appointmentsIterator = appointments.iterator();
        while (appointmentsIterator.hasNext()){
            Appointment appointment = appointmentsIterator.next();
            RenderedAppointment a = renderAppointment(appointment);
            getNewEntityDescriptor().setAppointment(new EntityDescriptor().getAppointment());
            getNewEntityDescriptor().getAppointment().setData(a);
            getNewEntityDescriptor().getAppointment().setAppointee(getNewEntityDescriptor().getPatient());
            getNewEntityDescriptor().getPatientAppointmentHistory().getDentalAppointments()
                    .add(getNewEntityDescriptor().getAppointment());
        }   
    }
    private Patient makePatientFrom(EntityDescriptor.Patient  eP){
        Patient p = new Patient();
        for (PatientField pf: PatientField.values()){
            switch (pf){
                case KEY -> p.setKey(eP.getData().getKey());
                case TITLE -> p.getName().setTitle(eP.getData().getTitle());
                case FORENAMES -> p.getName().setForenames(eP.getData().getForenames());
                case SURNAME -> p.getName().setSurname(eP.getData().getSurname());
                case LINE1 -> p.getAddress().setLine1(eP.getData().getLine1());
                case LINE2 -> p.getAddress().setLine2(eP.getData().getLine2());
                case TOWN -> p.getAddress().setTown(eP.getData().getTown());
                case COUNTY -> p.getAddress().setCounty(eP.getData().getCounty());
                case POSTCODE -> p.getAddress().setPostcode(eP.getData().getPostcode());
                case DENTAL_RECALL_DATE -> p.getRecall().setDentalDate(eP.getData().getDentalRecallDate());
                //case HYGIENE_RECALL_DATE -> p.getRecall().setHygieneDate(eP.getData().getHygieneRecallDate());
                //case HYGIENE_RECALL_FREQUENCY -> p.getRecall().setHygieneFrequency(eP.getData().getHygieneRecallFrequency());
                case DENTAL_RECALL_FREQUENCY -> p.getRecall().setDentalFrequency(eP.getData().getDentalRecallFrequency());
                case GENDER -> p.setGender(eP.getData().getGender());
                case PHONE1 -> p.setPhone1(eP.getData().getPhone1());
                case PHONE2 -> p.setPhone2(eP.getData().getPhone2());
                case DOB -> p.setDOB(eP.getData().getDOB());
                case NOTES -> p.setNotes(eP.getData().getNotes());
                case IS_GUARDIAN_A_PATIENT -> p.setIsGuardianAPatient(eP.getData().getIsGuardianAPatient());
            }
        }
        return p;
    }
    /**
     * On entry view controller's EntityFromView.Selection.Patient contains the 
     * view's currently selected patient. This is deserialised into a model Patient
     * object
     * @return model Patient object
     */
    private Patient deserialisePatientFromEDRequest(){
        Patient patient = makePatientFrom(getEntityDescriptorFromView().getRequest().getPatient());
        if (getEntityDescriptorFromView().getRequest().getPatient().getData().getIsGuardianAPatient()){
            if (getEntityDescriptorFromView().getRequest().getPatientGuardian()!=null){
                patient.setGuardian(makePatientFrom(
                        getEntityDescriptorFromView().getRequest().getPatientGuardian()));
            }
        }
        return patient;
    }
    private void setEntityDescriptor(EntityDescriptor value){
        this.newEntityDescriptor = value; 
    }
    private EntityDescriptor getNewEntityDescriptor(){
        return this.newEntityDescriptor;
    }
    private EntityDescriptor getOldEntityDescriptor(){
        return this.oldEntityDescriptor;
    }
    private void setOldEntityDescriptor(EntityDescriptor e){
        this.oldEntityDescriptor = e;
    }
    private void setNewEntityDescriptor(EntityDescriptor e){
        this.newEntityDescriptor = e;
    }
    private EntityDescriptor getEntityDescriptorFromView(){
        return this.entityDescriptorFromView;
    }
    private void setEntityDescriptorFromView(EntityDescriptor e){
        this.entityDescriptorFromView = e;
    }
    
    public PatientViewController(DesktopViewController controller, JFrame owner)throws StoreException{
        setMyController(controller);
        pcSupportForView = new PropertyChangeSupport(this);
        this.newEntityDescriptor = new EntityDescriptor();
        this.oldEntityDescriptor = new EntityDescriptor();
        ArrayList<Patient> patients = new Patients().getPatients();
        serialisePatientsToEDCollection(patients);

        view = new PatientView(this, getNewEntityDescriptor());
        this.view.addInternalFrameClosingListener(); 
        pcSupportForView.addPropertyChangeListener(
                PatientViewControllerPropertyEvent.
                        PATIENTS_RECEIVED.toString(),view);
        pcSupportForView.addPropertyChangeListener(
                PatientViewControllerPropertyEvent.
                        PATIENT_RECEIVED.toString(),view);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PropertyChangeListener[] pcls = null;
        Patient patient = null;
        setEntityDescriptorFromView(view.getEntityDescriptor());
        if (e.getActionCommand().equals(
                    PatientViewControllerActionEvent.
                            PATIENT_VIEW_CLOSED.toString())){
                ActionEvent actionEvent = new ActionEvent(
                    this,ActionEvent.ACTION_PERFORMED,
                    DesktopViewControllerActionEvent.VIEW_CLOSED_NOTIFICATION.toString());
                getMyController().actionPerformed(actionEvent);
        }
        else if (e.getActionCommand().equals(
            PatientViewControllerActionEvent.PATIENT_VIEW_CREATE_REQUEST.toString())){
            patient = deserialisePatientFromEDRequest();
            if (patient.getKey() == null){
                try{
                    patient = patient.create();
                    setOldEntityDescriptor(getNewEntityDescriptor());
                    serialisePatientToEDPatient(patient);
                    
                    pcEvent = new PropertyChangeEvent(this,
                            PatientViewControllerPropertyEvent.
                                PATIENT_RECEIVED.toString(),
                            getOldEntityDescriptor(),getNewEntityDescriptor());
                    pcSupportForView.firePropertyChange(pcEvent);
                }
                catch (StoreException ex){
                    
                }
            }
            else {//throw null patient key expected, non null value received
                //UnspecifiedErrorException
            }
        }
        else if (e.getActionCommand().equals(
                PatientViewControllerActionEvent.PATIENT_VIEW_UPDATE_REQUEST.toString())){
            patient = deserialisePatientFromEDRequest();
            if (patient.getKey() != null){
                try{
                    patient = patient.update();
                    setOldEntityDescriptor(getNewEntityDescriptor());
                    serialisePatientToEDPatient(patient);
                    
                    pcEvent = new PropertyChangeEvent(this,
                            PatientViewControllerPropertyEvent.
                            PATIENT_RECEIVED.toString(),getOldEntityDescriptor(),getNewEntityDescriptor());
                    pcSupportForView.firePropertyChange(pcEvent);
                }
                catch (StoreException ex){
                    //UnspecifiedError action
                }
            }
            else {//display an error message in view that non null key expected
                //UnspecifiedErrorException
            }
        }
        else if (e.getActionCommand().equals(
                PatientViewControllerActionEvent.PATIENTS_REQUEST.toString())){
            setEntityDescriptorFromView(((IView)e.getSource()).getEntityDescriptor());
            patient = deserialisePatientFromEDRequest();
            
            if (patient.getKey() != null){
                try{
                    ArrayList<Patient> patients = new Patients().getPatients();
                    setOldEntityDescriptor(getNewEntityDescriptor());
                    serialisePatientsToEDCollection(patients);
                    pcEvent = new PropertyChangeEvent(this,
                                PatientViewControllerPropertyEvent.
                                        PATIENTS_RECEIVED.toString(),getOldEntityDescriptor(),getNewEntityDescriptor());
                    pcSupportForView.firePropertyChange(pcEvent);  
                }
                catch (StoreException ex){
                    //UnspecifiedError action
                }
            }
        }
        /**
         * View has requested a patient object from view controller
         */
        else if (e.getActionCommand().equals(
                PatientViewControllerActionEvent.PATIENT_REQUEST.toString())){
            setEntityDescriptorFromView(((IView)e.getSource()).getEntityDescriptor());
            patient = deserialisePatientFromEDRequest();
            pcls = pcSupportForView.getPropertyChangeListeners();
            if (patient.getKey() != null){
                try{
                    Patient p = patient.read();
                    //setOldEntityDescriptor(getNewEntityDescriptor());
                    this.initialiseNewEntityDescriptor();
                    serialisePatientToEDPatient(p);
                    EntityDescriptor ed = getNewEntityDescriptor();
                    pcEvent = new PropertyChangeEvent(this,
                            PatientViewControllerPropertyEvent.
                            PATIENT_RECEIVED.toString(),getOldEntityDescriptor(),getNewEntityDescriptor());
                    pcSupportForView.firePropertyChange(pcEvent);
                }
                catch (StoreException ex){
                    JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                }
            }
        }
        else if (e.getActionCommand().equals(
                DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString())){
            try{
                /**
                 * view will message view controller when view is closed 
                 */
                getView().setClosed(true);
            }
            catch (PropertyVetoException ex){
                //UnspecifiedError action
            }
        }
    }
    /**
     * update old entity descriptor with previous new entity descriptor 
     * re-initialise the new entity descriptor, but copy over the old selected day
     */
    private void initialiseNewEntityDescriptor(){
        setOldEntityDescriptor(getNewEntityDescriptor());
        setNewEntityDescriptor(new EntityDescriptor());
        getNewEntityDescriptor().getRequest().setDay(getOldEntityDescriptor().getRequest().getDay());
    }
    private ActionListener getMyController(){
        return this.myController;
    }
    private void setMyController(ActionListener myController){
        this.myController = myController;
    }
    public JInternalFrame getView( ){
        return view;
    }
    private void setView(PatientView view ){
        this.view = view;
    }
    
    
}
