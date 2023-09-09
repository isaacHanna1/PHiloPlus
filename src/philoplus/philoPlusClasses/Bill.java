package philoplus.philoPlusClasses;

import java.sql.Date;

public class Bill {
    
    private int billId;
    private int LiftId;
    private String liftNumber;
    private int billNumber;
    private Date billDate;
    private int numberOfFloor ;
    private int numberOfWell ; 
    private float priceOfFloor ; 

    public Bill(int billID , Date billDate) {
        this.billId= billID;
        this.billDate = billDate;
    }

    public Bill(int billId, int LiftId, int billNumber, Date billDate, int numberOfFloor, int numberOfWell, float priceOfFloor) {
        this.billId = billId;
        this.LiftId = LiftId;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.numberOfFloor = numberOfFloor;
        this.numberOfWell = numberOfWell;
        this.priceOfFloor = priceOfFloor;
    }

    public Bill(int billId, int LiftId, Date billDate) {
        this.billId = billId;
        this.LiftId = LiftId;
        this.billDate = billDate;
    }
    

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int LiftId) {
        this.LiftId = LiftId;
    }

    public String getLiftNumber() {
        return liftNumber;
    }

    public void setLiftNumber(String liftNumber) {
        this.liftNumber = liftNumber;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public int getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(int numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }

    public int getNumberOfWell() {
        return numberOfWell;
    }

    public void setNumberOfWell(int numberOfWell) {
        this.numberOfWell = numberOfWell;
    }

    public float getPriceOfFloor() {
        return priceOfFloor;
    }

    public void setPriceOfFloor(float priceOfFloor) {
        this.priceOfFloor = priceOfFloor;
    }
    
    
}
