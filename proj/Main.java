import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Load all user data
        HashMap<String, List<String>> applicant_data = UserDatabase.excelToHashmap("proj/ApplicantList.xlsx");
        HashMap<String, List<String>> officer_data = UserDatabase.excelToHashmap("proj/OfficerList.xlsx");
        HashMap<String, List<String>> manager_data = UserDatabase.excelToHashmap("proj/ManagerList.xlsx");
        HashMap<String, HashMap<String, List<String>>> data_base = UserDatabase.combinedHashmap(applicant_data, officer_data, manager_data);
        UserManager.create_object_lists(data_base);
        //jump to line 95 for login

        // Load project data
        ProjectDatabase.excelToHashmap("proj/ProjectList.xlsx");

        // DEBUG: Print to confirm they loaded
        System.out.println("Loaded Projects:");
        for (Project p : ProjectManager.getActiveList()) {
            System.out.println(" - " + p.get_title());
        }

        // === L O G I N ===
        // system will keep running to allow for different logins until "endprogram" --> this ends the entire system
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter role (Applicant, HDBOfficer, HDBManager)"); //enter "ENDPROGRAM"
            String user_role = sc.nextLine().toLowerCase();

            while (!(user_role.equals("applicant") || user_role.equals("hdbofficer") || user_role.equals("hdbmanager")||user_role.equals("endprogram"))) {
                System.out.println("Role does not exist");
                user_role = sc.nextLine().toLowerCase();
            }
            if (user_role.equals("endprogram")) {
                System.out.println("Exiting program");
                break;
            }

            while (true) {
                System.out.println("Enter NRIC: ");
                String user_nric = sc.nextLine().toUpperCase();

                System.out.println("Enter password (case-sensitive): ");
                String user_pwd = sc.nextLine();

                User temp = new User(user_nric, user_pwd, user_role);
                boolean success = temp.login(data_base);

                if (success) {
                    switch (user_role) { //get object from existing arraylists of objects of UserManager class
                        case "applicant":
                            Applicant user_app = null;
                            for (Applicant appl : UserManager.all_applicants) {
                                if (appl.get_nric().equals(user_nric)) {
                                    user_app = appl;
                                }
                            }
                            ApplicantDisplay.start(user_app, applicant_data, data_base); // goes to respective dashboards
                            break;
                        case "hdbofficer":
                            HDBOfficer user_off = null;
                            for (HDBOfficer offi : UserManager.all_officers) {
                                if (offi.get_nric().equals(user_nric)) {
                                    user_off = offi;
                                }
                            }
                            HDBOfficerDisplay.start(user_off, officer_data, data_base);
                            break;
                        case "hdbmanager":
                            HDBManager user_man = null;
                            for (HDBManager mana : UserManager.all_managers) {
                                if (mana.get_nric().equals(user_nric)) {
                                    user_man = mana;
                                }
                            }
                            HDBManagerDisplay.start(user_man, manager_data, data_base);
                            break;
                    }
                    break;
                }
            }
        }
        System.out.println("Exiting program...");
    }
}


