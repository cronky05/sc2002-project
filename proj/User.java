import java.util.List;
import java.util.HashMap;

public class User {
    //attributes: role, NRIC, name, marital status, age
    //methods: login, change password --> move login function to outside user class, only implement change password //inherited by all subclass
    private String name; 
    private String nric;
    private int age;
    private boolean marital_stat; //true if married, else false
    private String password;
    private String role;
    
    //constructor with 3 parameters
    public User(String nric, String pwd, String role) {
        this.name = null;
        this.nric = nric;
        this.age = 0;   
        this.marital_stat = false; //default as false
        this.password = pwd;
        this.role = role;
    }

    //specific role database eg. nric -> [name, age, marital_status, pwd]
    //superclass constructor that will be called when creating Applicant/HDBOfficer/HDBManager class objects
    public User(String nric, String pwd, String role, HashMap<String, List<String>> correct_map) {
        this.name = correct_map.get(nric).get(0);
        this.nric = nric;
        this.age = (int) Double.parseDouble(correct_map.get(nric).get(1));
        this.marital_stat = correct_map.get(nric).get(2).equalsIgnoreCase("married");
        this.password = pwd;
        this.role = role;
    }

    //getter methods
    public String get_name(){return name;}
    public String get_nric() {return nric;};
    public int get_age() {return age;}
    public boolean get_marital_stat() {return marital_stat;} //true if married, else false
    public String get_password() {return password;}
    public String get_role() {return role;}

    public boolean login(HashMap<String, HashMap<String, List<String>>> all_data) {
        //check if NRIC is present in specified role's database (3 separate datebases for Applicant, HDBOfficer, HDBManager)
        //compare role attribute and data_base keys --> access correct userdatabase hashtable in data_base 
        HashMap<String, List<String>> specific_map = UserDatabase.chooseHashmap(all_data, this.role);
        if (specific_map != null) {
            //for (int i=1; i <= specific_map.size(); i++) { //for loop according to size of hashmap
                //if (specific_map.get(i).get(1).equalsIgnoreCase(this.nric)) //compare 2nd element of hashmap value array with nric; not case-sensitive
            if (specific_map.containsKey(this.nric)) {        
                if (specific_map.get(this.nric).get(3).equals(this.password)) //compare 4th element of hashmap value array with password; case-sensitive
                        return true; 
            }
        }
        System.out.println("NRIC Username/Password is incorrect."); //not found
        return false;
    }


    public void change_pwd(HashMap<String, List<String>> exact_map, String new_pwd) { //setter method for password + update database
        this.password = new_pwd;
        //update database - check nric and pwd again to go to specifc database hashtable key and access value
        exact_map.get(this.nric).set(3, new_pwd); //make sure in Main class, can only change password if user login is successful
        System.out.println("Password changed successfully!");
    }
}
