
package philoplus.philoPlusClasses;

public class FatherAccount {

    private int id ; 
    private String fatherAccountName ; 

    public FatherAccount(int id, String fatherAccountName) {
        if(fatherAccountName.equals("")){
         throw new NullPointerException();
        }
        this.id = id;
        this.fatherAccountName = fatherAccountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFatherAccountName() {
        return fatherAccountName;
    }

    public void setFatherAccountName(String fatherAccountName) {
        this.fatherAccountName = fatherAccountName;
    }
    
    
}
