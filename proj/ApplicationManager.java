import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationManager{

    public static void newApplication(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Project> projectList = ProjectManager.getProjectList(); // getting the price of projects
        for (Project project : projectList) {
            if (project.get_visibility()) { //if project not visible, do not print out, if not list
                System.out.println(project.get_title());
            }
        }
        System.out.println("Enter project title that you wish to apply for:");
        String projTitle = sc.nextLine().toLowerCase();
        Project project = projectList.stream().filter(p -> p.get_title()
                .equalsIgnoreCase(projTitle)).findFirst().orElse(null);; //filtering and retrieving the project
        if (applicant.get_age() > 34 && applicant.get_marital_stat() == false) {
            System.out.println("You can only apply for 2-room flat! Applying now..");
            Application application = new Application(applicant, project, "2");
            applicant.set_application(application);
            ArrayList<Application> applicationList = project.get_submissions();
            applicationList.add(application);
            project.set_submissions(applicationList);
            System.out.println("Successfully applied for 2 room!");
        } else if (applicant.get_age() > 21 && applicant.get_marital_stat() == true) {
            System.out.println("You can apply for a 1) 2-room or 2) 3-room flat! Enter 1 or 2:");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                Application application = new Application(applicant, project, "2");
                ArrayList<Application> applicationList = project.get_submissions();
                applicationList.add(application);
                project.set_submissions(applicationList);
                System.out.println("Successfully applied for 2 room!");
            } else if (choice.equals("2")) {
                Application application = new Application(applicant, project, "3");
                System.out.println("Successfully applied for 3 room!");
                ArrayList<Application> applicationList = project.get_submissions();
                applicationList.add(application);
                project.set_submissions(applicationList);
            }
        }
        else {
                System.out.println("You are not eligible to apply for any projects!");
            }
    }
    public static void listApplicants(HDBManager manager){
        Project project = manager.getProject();
        ArrayList<Application> applicationList = project.get_submissions();
        for(Application application: applicationList){
            int count = 1;
            System.out.println("Applicant number " + count + ": " + application.getApplicant().get_name());
        }
    }
    public static void processApplication(HDBManager manager) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Project> projectList = manager.getProjList();
        System.out.println("Project list:");
        for (Project project : projectList) { //print out the name of every project and let the manager choose
            System.out.println(project.get_title());
        }
        System.out.println("Enter project title you wish to access: ");
        String projTitle = sc.nextLine();
        Project project = projectList.stream().filter(p -> p.get_title().equalsIgnoreCase(projTitle)).findFirst().orElse(null);
        int numof2room_success = project.get_numof2room_success();
        int numof3room_success = project.get_numof3room_success();
        if (project.get_visibility()) {//if project still visible, don't let manager touch
            System.out.println("Project still ongoing! Unable to process applications yet!");
            return;
        }
        ArrayList<Application> applicationList = project.get_submissions();
        System.out.println("Applicant list:");
        for (Application application : applicationList) { //print out the name of every applicant and let the manager choose
            System.out.println(application.getApplicant().get_name());
        }
        System.out.println("Enter name of the applicant: ");
        String name = sc.nextLine();
        Application application = applicationList.stream().filter(a -> a.getApplicant().get_name()
                .equalsIgnoreCase(name)).findFirst().orElse(null);
        System.out.println("Do you want to 1.approve or 2.reject them? Enter your choice: ");
        int choice = sc.nextInt();
        if (choice == 1) { //need to check which room they are allocated.
            if (application.getRoomType().equals("2")) {
                if (numof2room_success < project.get_numof2room()) { //Check if still have 2 room
                    application.setStatus("Successful");
                    applicationList.remove(application); //removes application from submission list
                    project.set_submissions(applicationList); //updates the submission list
                    ArrayList<Application> successList = project.get_successful(); //retrieve old successful list
                    successList.add(application); //adding application to successful
                    project.set_successful(successList); //overwriting old success list with the newly added application
                    project.set_numof2room_success(numof2room_success + 1);
                    System.out.println("Approval successful!");
                } else {
                    System.out.println("Number of 2 rooms fully applied for! Unable to approve.");
                    return;
                }
            }
            if (application.getRoomType().equals("3")) {
                if (numof3room_success < project.get_numof3room()) {
                    application.setStatus("Successful");
                    applicationList.remove(application); //removes application from submission list
                    project.set_submissions(applicationList); //updates the submission list
                    ArrayList<Application> successList = project.get_successful(); //retrieve old successful list
                    successList.add(application); //adding application to successful
                    project.set_successful(successList); //overwriting old success list with the newly added application
                    project.set_numof3room_success(numof3room_success + 1);
                    System.out.println("Approval successful!");
                } else {
                    System.out.println("Number of 3 rooms fully applied for! Unable to approve.");
                    return;
                }
            }
        }
        else if (choice == 2){
            applicationList.remove(application); //removes application from submission list
            project.set_submissions(applicationList); //updates the submission list
            application.setStatus("Failed");
            System.out.println("Rejection successful!");
        }
    }

    public static void requestWithdrawApplication(Application application){
        Project project = application.getProject(); //get project associated with the application
        project.addWithdrawApplication(application); //adds application to withdrawal list in project
        System.out.println("Request for withdrawal successful!");
    }

    public static void processWithdrawApplication(HDBManager manager){
        Scanner sc = new Scanner(System.in);
        ArrayList<Project> projList = manager.getProjList();
        if (projList.isEmpty()) {
            System.out.println("No project created by manager");
            return;
        }
        for(Project project: projList){
            System.out.println(project.get_title());
        }
        System.out.println("Enter title of project you wish to process withdrawals for:");
        String projName = sc.nextLine();
        Project project = projList.stream().filter(p-> p.get_title()
                .equalsIgnoreCase(projName)).findFirst().orElse(null); //Gets the project from the proj list
        ArrayList<Application> withdrawalList = project.get_withdrawals(); //retrieve project's withdrawalList
        ArrayList<Application> submissionList = project.get_submissions(); //retrieve project's submissionList
        for (Application applicantObj: withdrawalList){
            System.out.println(applicantObj.getApplicant().get_name()); //print out every applicant by calling applicant's getName() method
        }
        System.out.println("Enter name of applicant you wish to process:");
        String name = sc.nextLine();
        Application app = withdrawalList.stream().filter(application->application.getApplicant().get_name()
                .equalsIgnoreCase(name)).findFirst().orElse(null);
        Applicant applicant = app.getApplicant();
        //getting the applicant that we want to process
        System.out.println("Do you wish to 1.approve or 2.reject " + name + "'s withdrawal?");
        int choice = sc.nextInt();
        withdrawalList.removeIf(application -> application.getApplicant().get_name().equalsIgnoreCase(name)); //removes application matching name from withdrawalList
        if (choice == 1) {
            submissionList.removeIf(application -> application.getApplicant().get_name().equalsIgnoreCase(name));
             //removes application matching name from submissionList
            project.set_submissions(submissionList); //updates project's submissionList
            project.set_withdrawals(withdrawalList); //updates project's withdrawalList
            applicant.set_application(null); //deletes the application from applicant
            System.out.println("Successfully approved!");
            }

        else{
            project.set_withdrawals(withdrawalList);
            System.out.println("Successfully rejected!");
        }
    }

//    public static void retrieveApplicantApplication(HDBOfficer officer){
//        Project project = officer.getProject();
//        System.out.println("Enter NRIC of applicant: ");
//        int applicantNRIC = sc.nextInt();
//        applicationList = project.get_successful();//edit this to be application.
//        application = applicationList.stream().filter(a->a.getApplicant().get_nric().equals(applicantNRIC)).findFirst();//does application have getMric or need to .getapplicant.getnric
//        }


//    //need to redo this
//    public static void updateApplicantStatus(Officer officer){
//        applicant.getSuccessfulApplication();
//                //need to .setStatus("Booked"); // accept boolean value in applicant class
//    }
//
//    public static void updateApplicantFlat(Officer officer, String applicantname, int flat){
//        Project project = officer.getProject();
//        Applicant applicant = project.listApplicants().stream().filter(a->a.get_name.contains(applicantname)).findFirst();
//        application.setFlat(flat);
//    }

    public static void bookingFlat(HDBOfficer officer, String flat){
        Scanner sc = new Scanner(System.in);
        Project project = officer.getProjectInCharge();
        System.out.println("Enter NRIC of applicant: ");
        String applicantNRIC = sc.nextLine();
        Application application = project.get_successful().stream().filter(a->a.getApplicant().get_nric().equals(applicantNRIC)).findFirst().orElse(null);
        application.getApplicant().set_typeOf_flat(flat); //update the applicant's profile with flat
        application.setStatus("Booked"); 
        if (flat.equals("two")){
            int num = project.get_numof2room();
            project.set_numof2room(num - 1);
            System.out.println("Successfully booked 2 room!");
        }
        else{
            int num = project.get_numof3room();
            project.set_numof3room(num - 1);
            System.out.println("Successfully booked 3 room!");
        }
    }
    public static void printReceipt(HDBOfficer officer){
        Project project = officer.getProjectInCharge();
        if (project == null){
            System.out.println("Officer not in charge of any project!");
            return;
        }
        ArrayList<Application> applicantList = project.get_successful(); //only print for after booking ie. successful application
        for (Application app: applicantList){
            app.getApplicant().generate_receipt();
            System.out.println("Project title: " + project.get_title() + ", Location: " + project.get_neighbourhood());
        }
    }

    //enable officers to apply for BTO they are not in charge of
    public static void newApplication(HDBOfficer officer) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Project> projectList = ProjectManager.getActiveList(); // getting the list of projects
        for (Project project : projectList) {
            if (project.get_visibility()&& !officer.getProjectInCharge().equals(project)) { //if project not visible, do not print out, if not list
                System.out.println(project.get_title());
            }
        }
        System.out.println("Enter project title that you wish to apply for:");
        String projTitle = sc.nextLine();
        Project project = projectList.stream().filter(p -> p.get_title()
                .equals(projTitle)).findFirst().orElse(null);; //filtering and retrieving the project
        if (officer.get_age() > 34 && officer.get_marital_stat() == false) {
            System.out.println("You can only apply for 2-room flat! Applying now..");
            Application application = new Application(officer, project, "2");
            ArrayList<Application> applicationList = project.get_submissions();
            applicationList.add(application);
            project.set_submissions(applicationList);
            System.out.println("Successfully applied for 3 room!");
        } else if (officer.get_age() > 21 && officer.get_marital_stat() == true) {
            System.out.println("You can apply for a 1) 2-room or 2) 3-room flat! Enter 1 or 2:");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                Application application = new Application(officer, project, "2");
                ArrayList<Application> applicationList = project.get_submissions();
                applicationList.add(application);
                project.set_submissions(applicationList);
                System.out.println("Successfully applied for 2 room!");
            } else if (choice.equals("2")) {
                Application application = new Application(officer, project, "3");
                System.out.println("Successfully applied for 3 room!");
                ArrayList<Application> applicationList = project.get_submissions();
                applicationList.add(application);
                project.set_submissions(applicationList);
            }
        }
        else {
                System.out.println("You are not eligible to apply for any projects!");
            }

    }

}