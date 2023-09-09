/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.philoPlusClasses;

import java.sql.Date;


public class BillPHilo_techncian extends Bill{
    private int priced_floor_num ; // ساعات بنحسب للفني عدد ادوار اكبر من العدد الفعلي للمصعد

    // i added this properties to this class to use it in table view
    private String liftNumber ; 
    private String liftType ;
    private int technciansNumber ; 
    private String projectName ;
    private String companyName ; 
    private float total;
 

    
    public BillPHilo_techncian(int billId, int LiftId, int billNumber, Date billDate, int numberOfFloor, int numberOfWell, float priceOfFloor , int priced_floor_num) {
        super(billId, LiftId, billNumber, billDate, numberOfFloor, numberOfWell, priceOfFloor);
        this.priced_floor_num = priced_floor_num;
    }

    public BillPHilo_techncian(int priced_floor_num, String liftNumber, String liftType, int technciansNumber , String projectName,String companyName, float total, int billId, int LiftId, int billNumber, Date billDate, int numberOfFloor, int numberOfWell, float priceOfFloor) {
        super(billId, LiftId, billNumber, billDate, numberOfFloor, numberOfWell, priceOfFloor);
        this.priced_floor_num = priced_floor_num;
        this.liftNumber = liftNumber;
        this.liftType = liftType;
        this.technciansNumber = technciansNumber;
        this.projectName = projectName;
        this.companyName = companyName;
        this.total = priced_floor_num * numberOfWell * priceOfFloor;
    }


    public int getPriced_floor_num() {
        return priced_floor_num;
    }

    public void setPriced_floor_num(int priced_floor_num) {
        this.priced_floor_num = priced_floor_num;
    }

    public String getLiftNumber() {
        return liftNumber;
    }

    public void setLiftNumber(String liftNumber) {
        this.liftNumber = liftNumber;
    }

    public String getLiftType() {
        return liftType;
    }

    public void setLiftType(String liftType) {
        this.liftType = liftType;
    }

    public int getTechnciansName() {
        return technciansNumber;
    }

    public void setTechnciansName(int technciansNumber) {
        this.technciansNumber = technciansNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
}
