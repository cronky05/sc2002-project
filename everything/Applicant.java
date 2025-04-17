package everything;

import java.util.HashMap;
import java.util.List;

public class Applicant extends User{
    //attributes: everything2.Application, everything2.Enquiry
    private Application applyBTO; 
    private Enquiry qns;
    private String typeOf_flat; //initially null, set as two/three AFTER successfully booked

    public Applicant(String nric, String pwd, String role, HashMap<String, List<String>> correct_map) {
        super(nric, pwd, role, correct_map);
        this.applyBTO = null;
        this.qns = null;
        this.typeOf_flat = null;
    }

    //setter methods
    public void set_application(Application app) {this.applyBTO = app;}
    public void set_enquiry(Enquiry enq) {this.qns = enq;}
    public void set_typeOf_flat(String typeFlat) {this.typeOf_flat = typeFlat;}

    //getter methods
    public Application get_application() {return applyBTO;}
    public Enquiry get_enquiry() {return qns;}
    public String get_typeOf_flat() {return typeOf_flat;}

    public void generate_receipt() { //will be used in printReceipt() in everything2.ApplicationManager
        System.out.println("Name: " + get_name() +", NRIC: " + get_nric() + ", Age: " + get_age() + ", Marital Status: " + get_marital_stat() + ", Flat Type: " + applyBTO.getRoomType() + "-room");
    }
}
