import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HDBManagerDisplay {
	static Scanner sc = new Scanner(System.in);
	public static void start(HDBManager manager, HashMap<String, List<String>> man_database, HashMap<String, HashMap<String, List<String>>> data_base) {
        while (true) {
            EnquiryInterface enquiryInterface = new EnquiryManager();
            System.out.println("Enter a number corresponding to the function");
            System.out.println("1. Create a project");
            System.out.println("2. Edit project");
            System.out.println("3. Delete project");
            System.out.println("4. View all projects");
            System.out.println("5. View my projects");
            System.out.println("6. View application");
            System.out.println("7. Process application");
            System.out.println("8. View pending HDB officer registration");
            System.out.println("9. Process HDB officer registration");
            System.out.println("10. Process withdrawal request");
            System.out.println("11. View enquiries");
            System.out.println("12. Process enquiries");
            System.out.println("13. Generate a list of applicants with their respective flat booking");
            System.out.println("14. Change password");
            System.out.println("15. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    ProjectManager.createProject(manager);
                    break;
                case 2:
                    ProjectManager.editProject(manager);
                    break;
                case 3:
                    ProjectManager.deleteProject(manager);
                    break;
                case 4:
                    System.out.println("Set filters? (Y/N)");
                    char set_filter = Character.toUpperCase(sc.next().charAt(0));
                    Filter print_Filter = new Filter();
                    if (set_filter == 'Y') {
                        System.out.println("1. Filter location");
                        System.out.println("2. Filter minimum housing price");
                        System.out.println("3. Filter maximum housing price");
                        System.out.println("4. Reset filters");
                        System.out.println("5. Finish selecting filters");
            
                        System.out.println("Choose filtering option: ");
                        int filter_choice = sc.nextInt();
                        sc.nextLine();
                        while (filter_choice < 4) {
                            switch(filter_choice) {
                                case 1: 
                                    System.out.println("Enter specific location: ");
                                    print_Filter.location = sc.nextLine();
                                    break;
                                case 2:
                                    System.out.println("Enter specific minimum housing price: ");
                                    print_Filter.minPrice = sc.nextInt(); //autoboxing from primitive type to wrapper class type
                                    break;
                                case 3:
                                    System.out.println("Enter specific maximum housing price: ");
                                    print_Filter.maxPrice = sc.nextInt();
                                    break;
                                case 4: print_Filter.location = null;
                                        print_Filter.minPrice = null;
                                        print_Filter.maxPrice = null;
                                        break;
                                default: System.out.println("Invalid option!");
                            }
                            System.out.println("Choose filtering option: ");
                            filter_choice = sc.nextInt();
                            sc.nextLine();
                        }
                    } 
                    ProjectManager.viewAllProject(print_Filter); //filter elements default to null ie. wont have any filters if none is set so should be able to print all
                    break;
                case 5: 
                    System.out.println("Set filters? (Y/N)");
                    char set_filter2 = Character.toUpperCase(sc.next().charAt(0));
                    Filter print_Filter2 = new Filter();
                    if (set_filter2 == 'Y') {
                        System.out.println("1. Filter location");
                        System.out.println("2. Old and upcoming projects only");
                        System.out.println("3. Current active project only");
                        System.out.println("4. Hide location");
                        System.out.println("5. Hide visibility");
                        System.out.println("6. Reset filters");
                        System.out.println("7. Finish selecting filters");

                        System.out.println("Choose filtering option: ");
                        int filter_choice1 = sc.nextInt();
                        sc.nextLine();
                        while (filter_choice1 < 6) {
                            switch(filter_choice1) {
                                case 1:
                                    System.out.println("Enter specific location: ");
                                    print_Filter2.location = sc.nextLine();
                                    break;
                                case 2:
                                    print_Filter2.check_old_upcoming = true; //filter away active projects
                                    break;
                                case 3:
                                    print_Filter2.checkvisibility = true;
                                    break;
                                case 4:
                                    print_Filter2.showLocation = false;
                                    break;
                                case 5:
                                    print_Filter2.showVisibility = false;
                                    break;
                                case 6: 
                                    print_Filter2.location = null;
                                    print_Filter2.check_old_upcoming = false;
                                    print_Filter2.checkvisibility = false;
                                    print_Filter2.showLocation = true;
                                    print_Filter2.showVisibility = true;
                                default: System.out.println("Invalid choice");
                            }
                            System.out.println("Choose filtering option: ");
                            filter_choice1 = sc.nextInt();
                            sc.nextLine();
                        }
                    } 
                    ProjectManager.viewOwnProject(manager, print_Filter2);
                    break;
                case 6:
                    ApplicationManager.listApplicants(manager);
                    break;
                case 7:
                    ApplicationManager.processApplication(manager);
                    break;
                case 8:
                    ProjectManager.viewOfficerRegistration(manager);
                    break;
                case 9:
                    ProjectManager.processOfficerRegistration(manager);
                    break;
                case 10:
                    ApplicationManager.processWithdrawApplication(manager);
                    System.out.println("Withdrawal processing successful!");
                    break;
                case 11:
                    enquiryInterface.viewEnquiries(manager);
                    break;
                case 12:
                    enquiryInterface.replyEnquiry(manager);
                    break;
                case 13:
                    System.out.println("Set filters? (Y/N)");
                    char set_filter3 = Character.toUpperCase(sc.next().charAt(0));
                    Filter report_Filter = new Filter();
                    if (set_filter3 == 'Y') {
                        System.out.println("1. Filter type of flat (2/3-room)");
                        System.out.println("2. Filter marital status");
                        System.out.println("3. Filter minimum age");
                        System.out.println("4. Filter maximum age");
                        System.out.println("5. Show type of flat");
                        System.out.println("6. Show marital status");
                        System.out.println("7. Show age");
                        System.out.println("8. Show Project title");
                        System.out.println("9. Finish selecting filters");

                        System.out.println("Choose filtering option: ");
                        int filter_option = sc.nextInt();
                        sc.nextLine();
                        while (filter_option < 9) {
                            switch(filter_option) {
                                case 1: 
                                    System.out.println("Choose type of flat (two / three):");
                                    report_Filter.flatType = sc.nextLine().toLowerCase();
                                    break;
                                case 2:
                                    System.out.println("Choose marital status: ");
                                    report_Filter.filter_marital = sc.nextLine().toLowerCase();
                                    break;
                                case 3:
                                    System.out.println("Enter minimum age: ");
                                    report_Filter.minAge = sc.nextInt();
                                    break;
                                case 4:
                                    System.out.println("Enter maximum age: ");
                                    report_Filter.maxAge = sc.nextInt();
                                    break;
                                case 5:
                                    System.out.println("Show type of flat? (Y/N)");
                                    char input_flat = Character.toUpperCase(sc.next().charAt(0));
                                    report_Filter.showFlatType = (input_flat == 'Y');
                                    break;
                                case 6:
                                    System.out.println("Show marital status? (Y/N)");
                                    char input_martial = Character.toUpperCase(sc.next().charAt(0));
                                    report_Filter.showMarital = (input_martial == 'Y');
                                    break;
                                case 7:
                                    System.out.println("Show age? (Y/N)");
                                    char input_age = Character.toUpperCase(sc.next().charAt(0));
                                    report_Filter.showAge = (input_age == 'Y');
                                    break;
                                case 8:
                                    System.out.println("Show project title? (Y/N)");
                                    char input_title = Character.toUpperCase(sc.next().charAt(0));
                                    report_Filter.showProjectName = (input_title == 'Y');
                                    break;
                                default: System.out.println("Invalid option");
                                
                            }
                            System.out.println("Choose filtering option: ");
                            filter_option = sc.nextInt();
                            sc.nextLine();
                        }
                    } 
                    manager.generate_report(report_Filter);
                    break;
                case 14:
                    System.out.println("Enter new password: ");
                    String new_pwd = sc.nextLine();
                    manager.change_pwd(man_database, new_pwd); 
                    UserManager.create_object_lists(data_base); //update objects to have new password
                    return; //relogin after change password
                case 15 : return;
                default: System.out.println("Invalid choice!");
                    
            }
        }
    }   
}