package everything;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HDBManagerDisplay {
	static Scanner sc = new Scanner(System.in);
	public static void start(HDBManager manager, HashMap<String, List<String>> man_database) {
        while (true) {
            EnquiryInterface enquiryInterface = new EnquiryManager();
            System.out.println("Enter a number corresponding to the function");
            System.out.println("1. Create a project");
            System.out.println("2. Edit project");
            System.out.println("3. Delete project");
            System.out.println("4. View all projects");
            System.out.println("5. View my projects");
            System.out.println("6. View pending HDB officer registration");
            System.out.println("7. Process HDB officer registration");
            System.out.println("8. Process withdrawal request");
            System.out.println("9. View enquiries");
            System.out.println("10. Process enquiries");
            System.out.println("11. Generate a list of applicants with their respective flat booking");
            System.out.println("12. Change password");
            System.out.println("13. Edit projects' visibilities");
            System.out.println("14. Exit");
            int choice = sc.nextInt();
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
                        System.out.println("1. everything2.Filter location");
                        System.out.println("2. everything2.Filter minimum housing price");
                        System.out.println("3. everything2.Filter maximum housing price");
                        System.out.println("4. Finish selecting filters");
            
                        System.out.println("Choose filtering option: ");
                        int filter_choice = sc.nextInt();
                        while (filter_choice < 4) {
                            switch(filter_choice) {
                                case 1: 
                                    System.out.println("Enter preferred location: ");
                                    print_Filter.location = sc.nextLine();
                                    break;
                                case 2:
                                    System.out.println("Enter preferred minimum housing price: ");
                                    print_Filter.minPrice = sc.nextInt(); //autoboxing from primitive type to wrapper class type
                                    break;
                                case 3:
                                    System.out.println("Enter preferred maximum housing price: ");
                                    print_Filter.maxPrice = sc.nextInt();
                                    break;
                                default: System.out.println("Invalid option!");
                            }
                            System.out.println("Choose filtering option: ");
                            filter_choice = sc.nextInt();
                        }
                    } 
                    ProjectManager.viewAllProject(print_Filter); //filter elements default to null ie. wont have any filters if none is set so should be able to print all
                    break;
                case 5: 
                    System.out.println("Set filters? (Y/N)");
                    char set_filter2 = Character.toUpperCase(sc.next().charAt(0));
                    Filter print_Filter2 = new Filter();
                    if (set_filter2 == 'Y') {
                        System.out.println("1. everything2.Filter location");
                        System.out.println("2. Old and upcoming projects only");
                        System.out.println("3. Current active project only");
                        System.out.println("4. Hide location");
                        System.out.println("5. Hide visibility");
                        System.out.println("6. Finish selecting filters");

                        System.out.println("Choose filtering option: ");
                        int filter_choice1 = sc.nextInt();
                        while (filter_choice1 < 6) {
                            switch(filter_choice1) {
                                case 1:
                                    System.out.println("Enter specific location: ");
                                    print_Filter2.location = sc.nextLine();
                                    break;
                                case 2:
                                    print_Filter2.check_old_upcoming = true;
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
                                default: System.out.println("Invalid choice");
                            }
                            System.out.println("Choose filtering option: ");
                            filter_choice1 = sc.nextInt();
                        }
                    } 
                    ProjectManager.viewOwnProject(manager, print_Filter2);
                    break;
                case 6:
                    ProjectManager.viewOfficerRegistration(manager);
                    break;
                case 7:
                    ProjectManager.processOfficerRegistration(manager);
                    break;
                case 8:
                    ApplicationManager.processWithdrawApplication(manager);
                    break;
                case 9:
                    enquiryInterface.viewEnquiries(manager);
                    break;
                case 10:
                    enquiryInterface.replyEnquiry(manager);
                    break;
                case 11:
                    System.out.println("Set filters? (Y/N)");
                    char set_filter3 = Character.toUpperCase(sc.next().charAt(0));
                    Filter report_Filter = new Filter();
                    if (set_filter3 == 'Y') {
                        System.out.println("1. everything2.Filter type of flat (2/3-room)");
                        System.out.println("2. everything2.Filter marital status");
                        System.out.println("3. everything2.Filter minimum age");
                        System.out.println("4. everything2.Filter maximum age");
                        System.out.println("5. Show type of flat");
                        System.out.println("6. Show marital status");
                        System.out.println("7. Show age");
                        System.out.println("8. Show everything2.Project title");
                        System.out.println("9. Finish selecting filters");

                        System.out.println("Choose filtering option: ");
                        int filter_option = sc.nextInt();
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
                        }
                    } 
                    manager.generate_report(report_Filter);
                    break;
                case 12:
                    System.out.println("Enter new password: ");
                    String new_pwd = sc.nextLine();
                    manager.change_pwd(man_database, new_pwd); 
                    UserManager.create_object_lists(); //update objects to have new password
                    break;   
                case 13:
                    ProjectManager.toggle_visibility(manager);            
                case 14 : return;
                default: System.out.println("Invalid choice!");
                    
            }
        }
    }   
}