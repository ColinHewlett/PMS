/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpms.store;

import clinicpms.store.exceptions.StoreException;
import clinicpms.store.interfaces.IStore;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;


/**
 *
 * @author colin
 */
public abstract class Store implements IStore {
    public enum MigrationMethod   { APPOINTMENTS_COUNT,
                                    APPOINTMENT_TABLE_CREATE,
                                    APPOINTMENT_TABLE_DROP,
                                    APPOINTMENT_TABLE_POPULATE,
                                    APPOINTMENT_TABLE_INTEGRITY_CHECK,
                                    APPOINTMENT_START_TIMES_NORMALISED,
                                    PATIENTS_COUNT,
                                    PATIENT_TABLE_CREATE,
                                    PATIENT_TABLE_DROP,
                                    PATIENT_TABLE_POPULATE,
                                    PATIENT_TABLE_TIDY
                                  }
    public enum CSVMigrationMethod  {   CSV_APPOINTMENT_FILE_CONVERTER,
                                        CSV_MIGRATION_INTEGRITY_PROCESS,
                                        CSV_PATIENT_FILE_CONVERTER,
                                        ACCESS_PATIENT_PREPROCESS
                                    }
    
    public enum MigrationAppointmentSQL {
                            APPOINTMENT_TABLE_CREATE,
                            APPOINTMENT_TABLE_DROP,
                            APPOINTMENT_START_TIME_NORMALISED}
    
    public enum MigrationPatientSQL {
                            PATIENT_TABLE_CREATE,
                            PATIENT_TABLE_DROP}
     
    public enum Storage{ACCESS, 
                        CSV,
                        POSTGRES,
                        SQL_EXPRESS,
                        UNDEFINED_DATABASE}
    
    public enum ExceptionType {  APPOINTEE_NOT_FOUND_EXCEPTION,
                                 IO_EXCEPTION,
                                 CSV_EXCEPTION,
                                 NULL_KEY_EXPECTED_EXCEPTION,
                                 NULL_KEY_EXCEPTION,
                                 INVALID_KEY_VALUE_EXCEPTION,
                                 KEY_FOUND_EXCEPTION,
                                 KEY_NOT_FOUND_EXCEPTION,
                                 SQL_EXCEPTION,
                                 STORE_EXCEPTION,
                                 UNDEFINED_DATABASE}
    
    /*
    public enum MigrationMethod{    CSV_APPOINTMENT_FILE_CONVERTER,
                                    CSV_MIGRATION_INTEGRITY_PROCESS,
                                    CSV_PATIENT_FILE_CONVERTER,
                                    ACCESS_PATIENT_PREPROCESS
                                }
    public enum Storage{CSV,
                        ACCESS, 
                        POSTGRES,
                        SQL_EXPRESS}
    
    public static enum ExceptionType {  IO_EXCEPTION,
                                 CSV_EXCEPTION,
                                 NULL_KEY_EXPECTED_EXCEPTION,
                                 NULL_KEY_EXCEPTION,
                                 INVALID_KEY_VALUE_EXCEPTION,
                                 KEY_FOUND_EXCEPTION,
                                 KEY_NOT_FOUND_EXCEPTION,
                                 SQL_EXCEPTION,
                                 UNDEFINED_DATABASE}
    */
    private static Storage storage = null;
    private static String databaseLocatorPath = null;
    
    public static IStore factory()throws StoreException{
        IStore result = null;
        switch (getStorageType()){
            case ACCESS: 
                result = AccessStore.getInstance();
                break;
            case CSV:
                result = CSVStore.getInstance();
                break;
            case POSTGRES:
                result = PostgreSQLStore.getInstance();
                break;
            case SQL_EXPRESS:
                result = SQLExpressStore.getInstance();
                break;
        }
        return result;
    }
    
    public static String getDatabaseLocatorPath(){
        return databaseLocatorPath;
    }
    
    public static void setDatabaseLocatorPath(String path){
        databaseLocatorPath = path;
    } 
    
    public static Storage getStorageType(){
        return storage;
    } 
    
    public static void setStorageType(Storage type){
        storage = type;
    }

    public static DbLocationStore getDbLocationStore()throws StoreException{
        return DbLocationStore.getInstance();
    }   
}

