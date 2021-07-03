/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.view;

import clinicpms.view.base.DesktopView;
import clinicpms.view.factory_methods.*;
import clinicpms.controller.EntityDescriptor;
import clinicpms.view.interfaces.IView;
import clinicpms.view.interfaces.IViewInternalFrameListener;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;

/**
 *
 * @author colin
 */
public abstract class View extends JInternalFrame
                           implements PropertyChangeListener,IView, IViewInternalFrameListener{
    private static Viewer viewer = null;
    
    public View(){
        super("Appointments view",true,true,true,true);
        
    }
    
    public static enum Viewer { APPOINTMENT_SCHEDULE_VIEW,
                                APPOINTMENT_CREATOR_VIEW,
                                APPOINTMENT_CREATOR_EDITOR_VIEW,
                                APPOINTMENT_EDITOR_VIEW,
                                EMPTY_SLOT_SCANNER_VIEW,
                                MIGRATION_VIEW,
                                NON_SURGERY_DAY_SCHEDULE_EDITOR_VIEW,
                                PATIENT_VIEW,
                                SCHEDULE_CONTACT_LIST_VIEW,
                                SURGERY_DAYS_EDITOR_VIEW}
    
    public static void setViewer(Viewer value){
        viewer = value;
    }
    
    public abstract Viewer getMyViewType();
    
    /*
    public static Viewer getViewer(){
        return viewer;
    }
     */
    
    public static View factory(ActionListener controller, EntityDescriptor ed, DesktopView dtView){
        View result = null;
        switch(viewer){
            case APPOINTMENT_SCHEDULE_VIEW:
                result = new AppointmentScheduleFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case APPOINTMENT_CREATOR_VIEW:
                result = null;
                break;
            case APPOINTMENT_CREATOR_EDITOR_VIEW:
                result = new AppointmentCreatorEditorFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case APPOINTMENT_EDITOR_VIEW:
                result = null;
                break;
            case EMPTY_SLOT_SCANNER_VIEW:
                result = new EmptySlotScannerFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case PATIENT_VIEW:
                result = new PatientFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case NON_SURGERY_DAY_SCHEDULE_EDITOR_VIEW:
                result = new NonSurgeryDayEditorFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case SCHEDULE_CONTACT_LIST_VIEW:
                result = new ScheduleContactListFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
            case SURGERY_DAYS_EDITOR_VIEW:
                result = new SurgeryDaysEditorFactoryMethod(controller, ed, dtView).makeView(viewer);
                break;
                
        }
        return result;
    }
}
