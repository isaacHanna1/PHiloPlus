
package philoplus.FXMLFILES;

public class TypeOfLifts {

private int id ; 
private String tyoeOfLift ;
private String note ;

    public TypeOfLifts(int id, String tyoeOfLift, String note) {
        this.id = id;
        this.tyoeOfLift = tyoeOfLift;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTyoeOfLift() {
        return tyoeOfLift;
    }

    public void setTyoeOfLift(String tyoeOfLift) {
        this.tyoeOfLift = tyoeOfLift;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
