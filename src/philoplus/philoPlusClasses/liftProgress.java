
package philoplus.philoPlusClasses;

public class liftProgress {
    private int id ;
    private int liftTypeId;
    private String liftType;
    private String liftProgressDetails ; 
    private float ratio;

    public liftProgress(int id, int liftTypeId, String liftProgressDetails, float ratio) {
        this.id = id;
        this.liftTypeId = liftTypeId;
        this.liftProgressDetails = liftProgressDetails;
        this.ratio = ratio;
    }

    public liftProgress(int id, String liftType, String liftProgressDetails, float ratio) {
        this.id = id;
        this.liftType = liftType;
        this.liftProgressDetails = liftProgressDetails;
        this.ratio = ratio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLiftTypeId() {
        return liftTypeId;
    }

    public void setLiftTypeId(int liftTypeId) {
        this.liftTypeId = liftTypeId;
    }

    public String getLiftType() {
        return liftType;
    }

    public void setLiftType(String liftType) {
        this.liftType = liftType;
    }

    public String getLiftProgressDetails() {
        return liftProgressDetails;
    }

    public void setLiftProgressDetails(String liftProgressDetails) {
        this.liftProgressDetails = liftProgressDetails;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
    
        
}
