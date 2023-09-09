/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.philoPlusClasses;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import philoplus.philoPlusClasses.Database;

/**
 *
 * @author Seha
 */
public class adie extends Technician{
   private  int technical_id ;
   private String technicalName ;
    public adie(int id, String isTechnician, String name,int technician_id ,String nationalId, String telephones, Date startWork, int experienceYear, String notes) {
        super(id, isTechnician, name, nationalId, telephones, startWork, experienceYear, notes);
        this.technical_id = technician_id;
    }
    public adie(int id, String isTechnician, String name,String techncianName ,String nationalId, String telephones, Date startWork, int experienceYear, String notes) {
        super(id, isTechnician, name, nationalId, telephones, startWork, experienceYear, notes);
        this.technicalName = techncianName;
    }

    public int getTechnical_id() {
        return technical_id;
    }

    public void setTechnical_id(int technical_id) {
        this.technical_id = technical_id;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

  
   
    
    
}
