package philoplus.philoPlusClasses;

public class Son {
    
    
    private int id ;
    private int fatherAccountId ; 
    private String sonAccount ;
    
    private String fatherAccountName ;

    public Son(int id, int fatherAccountId, String sonAccount) {
        this.id = id;
        this.fatherAccountId = fatherAccountId;
        this.sonAccount = sonAccount;
    }

    public Son(int id, String sonAccount, String fatherAccountName) {
        this.id = id;
        this.sonAccount = sonAccount;
        this.fatherAccountName = fatherAccountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFatherAccountId() {
        return fatherAccountId;
    }

    public void setFatherAccountId(int fatherAccountId) {
        this.fatherAccountId = fatherAccountId;
    }

    public String getSonAccount() {
        return sonAccount;
    }

    public void setSonAccount(String sonAccount) {
        this.sonAccount = sonAccount;
    }

    public String getFatherAccountName() {
        return fatherAccountName;
    }

    public void setFatherAccountName(String fatherAccountName) {
        this.fatherAccountName = fatherAccountName;
    }
    
    
    
    
}
