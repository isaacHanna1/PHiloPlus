
package philoplus.philoPlusClasses;

import java.sql.Date;


public class Payed {
    
    private int id ;
    private int sonAccountid ; 
    private float payedValue ;
    private Date payedDate ;
    private String payedDetails;
    
    private String sonAccountName;

    public Payed(int id, int sonAccountid, float payedValue, Date payedDate, String payedDetails) {
        this.id = id;
        this.sonAccountid = sonAccountid;
        this.payedValue = payedValue;
        this.payedDate = payedDate;
        this.payedDetails = payedDetails;
    }

    public Payed(int id, float payedValue, Date payedDate, String payedDetails, String sonAccountName) {
        this.id = id;
        this.payedValue = payedValue;
        this.payedDate = payedDate;
        this.payedDetails = payedDetails;
        this.sonAccountName = sonAccountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSonAccountId() {
        return sonAccountid;
    }

    public void setSonAccount(int sonAccount) {
        this.sonAccountid = sonAccount;
    }

    public float getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(float payedValue) {
        this.payedValue = payedValue;
    }

    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

    public String getPayedDetails() {
        return payedDetails;
    }

    public void setPayedDetails(String payedDetails) {
        this.payedDetails = payedDetails;
    }

    public String getSonAccountName() {
        return sonAccountName;
    }

    public void setSonAccountName(String sonAccountName) {
        this.sonAccountName = sonAccountName;
    }

    
    
}