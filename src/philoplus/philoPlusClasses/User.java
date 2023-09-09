
package philoplus.philoPlusClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Seha
 */
public class User {
    private int id ;
    private String userName ;
    private  String passsword;
    private String perivilage;
    
    public static User activeUser ;
    
    public User() {
    }
    public User(int id, String userName, String passsword, String perivilage) {
        this.id = id;
        this.userName = userName;
        this.passsword = passsword;
        this.perivilage = perivilage;
    }

    public User(String userName, String passsword) {
        this.userName = userName;
        this.passsword = passsword;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getPerivilage() {
        return perivilage;
    }
    
    public void setPerivilage(String perivilage) {
        this.perivilage = perivilage;
    }
        
    public boolean chechLoginAvability() throws SQLException{
        
        ResultSet rs =  Database.checkValidalityLogin(userName, passsword);
        if(rs.next()){
        return true;
        }
        else{
        return false;
        }
    }
    
    public static void activeUser(User u){
        activeUser = u ;
    }
}
