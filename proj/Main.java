
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Load all user data
        HashMap<String, List<String>> applicant_data = UserDatabase.excelToHashmap("proj/ApplicantList.xlsx");
        HashMap<String, List<String>> officer_data = UserDatabase.excelToHashmap("proj/OfficerList.xlsx");
        HashMap<String, List<String>> manager_data = UserDatabase.excelToHashmap("proj/ManagerList.xlsx");
        HashMap<String, HashMap<String, List<String>>> data_base = UserDatabase.combinedHashmap(applicant_data, officer_data, manager_data);
        UserManager.create_object_lists();
        //jump to line 51 for login

        // Load project data
        HashMap<String, List<String>> project_data = ProjectDatabase.excelToHashmap("proj/ProjectList.xlsx");

        // Convert project data into Project objects and store in ProjectManager
        ArrayList<Project> loadedProjects = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

        for (Map.Entry<String, List<String>> set : project_data.entrySet()) {
            String name = set.getKey();
            List<String> values = set.getValue();

            String neighbourhood = values.get(0);
            int numOf2Room = (int) Double.parseDouble(values.get(2));
            int price2Room = (int) Double.parseDouble(values.get(3));
            int numOf3Room = (int) Double.parseDouble(values.get(5));
            int price3Room = (int) Double.parseDouble(values.get(6));
            LocalDate openingDate = LocalDate.parse(values.get(7), formatter);
            LocalDate closingDate = LocalDate.parse(values.get(8), formatter);
            int numOfOfficerSlots = (int) Double.parseDouble(values.get(10));

            Project proj = new Project(name, neighbourhood, numOf2Room, price2Room,
                    numOf3Room, price3Room, openingDate, closingDate,
                    null, numOfOfficerSlots); // manager is null for now

            proj.set_visibility(true); // mark visible so it shows up
            loadedProjects.add(proj);
        }

        ProjectManager.setProjectList(loadedProjects); // key step to allow system access

        // add projects to lists correspond to their status i.e active, inactive....
        ArrayList<Project> expiredProjects = new ArrayList<>();
        ArrayList<Project> inactiveProjects = new ArrayList<>();
        ArrayList<Project> activeProjects = new ArrayList<>();
        for (Project p : ProjectManager.getProjectList()) {
            if (p.get_opening_date().isAfter(LocalDate.now())) {
                inactiveProjects.add(p);
            }
            else if (p.get_closing_date().isBefore(LocalDate.now())) {
                expiredProjects.add(p);
            }
            else {
                activeProjects.add(p);
            }
        }
        ProjectManager.setInactiveList(inactiveProjects);
        ProjectManager.setActiveList(activeProjects);
        ProjectManager.setExpiredList(expiredProjects);

        // DEBUG: Print to confirm they loaded
        System.out.println("Loaded Projects:");
        for (Project p : ProjectManager.getExpiredList()) {
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
                    switch (user_role) {
                        case "applicant":
                            Applicant user_app = new Applicant(user_nric, user_pwd, user_role, applicant_data);
                            ApplicantDisplay.start(user_app, applicant_data); // goes to respective dashboards
                            break;
                        case "hdbofficer":
                            HDBOfficer user_off = new HDBOfficer(user_nric, user_pwd, user_role, officer_data);
                            HDBOfficerDisplay.start(user_off, officer_data);
                            break;
                        case "hdbmanager":
                            HDBManager user_man = new HDBManager(user_nric, user_pwd, user_role, manager_data);
                            HDBManagerDisplay.start(user_man, manager_data);
                            break;
                    }
                    break;
                }
            }
        }
        System.out.println("Exiting program...");
    }
}


