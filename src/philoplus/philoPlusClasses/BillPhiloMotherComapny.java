/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.philoPlusClasses;

import java.sql.Date;

/**
 *
 * @author Seha
 */
public class BillPhiloMotherComapny extends Bill{
    
    private int priced_floor;
    private float addationValue ;
    private int    isSent;
    private int    isReceived;
    
       // i added this properties to this class to use it in table view
    private String liftNumber ; 
    private String liftType ;
    private int technciansNumber ; 
    private String projectName ;
    private String companyName ; 
    private float totalWithoutAddationValue;
    private float totalWithAddationValue;

    public BillPhiloMotherComapny(int priced_floor, float addationValue, int isSent, int isReceived, int billId, int LiftId, int billNumber, Date billDate, int numberOfFloor, int numberOfWell, float priceOfFloor) {
        super(billId, LiftId, billNumber, billDate, numberOfFloor, numberOfWell, priceOfFloor);
        this.priced_floor = priced_floor;
        this.addationValue = addationValue;
        this.isSent = isSent;
        this.isReceived = isReceived;
    }

    public BillPhiloMotherComapny(int priced_floor, float addationValue, int isSent, int isReceived, String liftNumber, String liftType, int technciansNumber, String projectName, String companyName, int billId, int LiftId, int billNumber, Date billDate, int numberOfFloor, int numberOfWell, float priceOfFloor) {
        super(billId, LiftId, billNumber, billDate, numberOfFloor, numberOfWell, priceOfFloor);
        this.priced_floor = priced_floor;
        this.addationValue = addationValue;
        this.isSent = isSent;
        this.isReceived = isReceived;
        this.liftNumber = liftNumber;
        this.liftType = liftType;
        this.technciansNumber = technciansNumber;
        this.projectName = projectName;
        this.companyName = companyName;
        this.totalWithoutAddationValue = numberOfWell * priced_floor * priceOfFloor;
        this.totalWithAddationValue = totalWithoutAddationValue + (totalWithoutAddationValue * addationValue) / 100;
    }
    
    public int getPriced_floor() {
        return priced_floor;
    }

    public void setPriced_floor(int priced_floor) {
        this.priced_floor = priced_floor;
    }

    public float getAddationValue() {
        return addationValue;
    }

    public void setAddationValue(float addationValue) {
        this.addationValue = addationValue;
    }

    public int getIsSent() {
        return isSent;
    }

    public void setIsSent(int isSent) {
        this.isSent = isSent;
    }

    public int getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(int isReceived) {
        this.isReceived = isReceived;
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

    public int getTechnciansNumber() {
        return technciansNumber;
    }

    public void setTechnciansNumber(int technciansNumber) {
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

    public float getTotalWithoutAddationValue() {
        return totalWithoutAddationValue;
    }

    public void setTotalWithoutAddationValue(float totalWithoutAddationValue) {
        this.totalWithoutAddationValue = totalWithoutAddationValue;
    }

    public float getTotalWithAddationValue() {
        return totalWithAddationValue;
    }

    public void setTotalWithAddationValue(float totalWithAddationValue) {
        this.totalWithAddationValue = totalWithAddationValue;
    }
    
    
    

    
}
