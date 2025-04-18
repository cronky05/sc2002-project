import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {
    //stores list of objects from excel sheets as login creates particular object/user using the BTO booking system; this database allows our system functions to be used
    public static ArrayList<Applicant> all_applicants = new ArrayList<>();
    public static ArrayList<HDBOfficer> all_officers = new ArrayList<>();
    public static ArrayList<HDBManager> all_managers = new ArrayList<>();

    

    public static void create_object_lists() {
        HashMap<String, List<String>> applicant_data =  UserDatabase.excelToHashmap("proj/ApplicantList.xlsx");
        HashMap<String, List<String>> officer_data = UserDatabase.excelToHashmap("proj/OfficerList.xlsx");
        HashMap<String, List<String>> manager_data = UserDatabase.excelToHashmap("proj/ManagerList.xlsx");

        for (String a_key: applicant_data.keySet()) {
            String a_pwd = applicant_data.get(a_key).get(3);
            Applicant a_obj = new Applicant(a_key, a_pwd, "applicant", applicant_data);
            all_applicants.add(a_obj);
        }

        for (String o_key: officer_data.keySet()) {
            String o_pwd = officer_data.get(o_key).get(3);
            HDBOfficer o_obj = new HDBOfficer(o_key, o_pwd, "hdbofficer", officer_data);
            all_officers.add(o_obj);
        }

        for (String m_key: manager_data.keySet()) {
            String m_pwd = manager_data.get(m_key).get(3);
            HDBManager m_obj = new HDBManager(m_key, m_pwd, "hdbmanager", manager_data);
            all_managers.add(m_obj);
        }
    }
}
