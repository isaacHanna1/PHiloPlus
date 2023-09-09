
package philoplus.philoPlusClasses;

import java.sql.Date;

public class AddationValueToLift extends Bill{
    
    private float addedValue ;
    private String reasonOfAdding  ;
    
    private String liftType;
    private String site;
    private String  company ; 
    private String liftNumber;
    
    public AddationValueToLift(float addedValue, String reasonOfAdding, int billId, int LiftId, Date billDate) {
        super(billId, LiftId, billDate);
        this.addedValue = addedValue;
        this.reasonOfAdding = reasonOfAdding;
    }

    public AddationValueToLift(float addedValue, String reasonOfAdding, String liftNumber, int billId, int LiftId, Date billDate) {
        super(billId, LiftId, billDate);
        this.addedValue = addedValue;
        this.reasonOfAdding = reasonOfAdding;
        this.liftNumber = liftNumber;
    }

    public AddationValueToLift(float addedValue, String reasonOfAdding, int billID , Date billDate) {
        super(billID, billDate);
        this.addedValue = addedValue;
        this.reasonOfAdding = reasonOfAdding;
    }
     
    public float getAddedValue() {
        return addedValue;
    }

    public void setAddedValue(float addedValue) {
        this.addedValue = addedValue;
    }

    public String getReasonOfAdding() {
        return reasonOfAdding;
    }

    public void setReasonOfAdding(String reasonOfAdding) {
        this.reasonOfAdding = reasonOfAdding;
    }

    public String getLiftType() {
        return liftType;
    }

    public void setLiftType(String liftType) {
        this.liftType = liftType;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLiftNumber() {
        return liftNumber;
    }

    public void setLiftNumber(String liftNumber) {
        this.liftNumber = liftNumber;
    }
    
            
}
