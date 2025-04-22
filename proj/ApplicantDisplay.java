import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ApplicantDisplay {
    public static void start(Applicant applicant, HashMap<String, List<String>> app_database) {
        Scanner sc = new Scanner(System.in);
        //check applicant's age
        if ((applicant.get_age() < 21) || (applicant.get_age() < 35 && applicant.get_marital_stat()!=true) ) { //wont be able to see at all if below 21
                System.out.println("Not of age to book BTO");
                System.out.println("Change password? ");
                char ch_pwd = Character.toUpperCase(sc.next().charAt(0));
                if (ch_pwd == 'Y') {
                        System.out.println("Enter new password: ");
                        String new_pwd = sc.nextLine();
                        applicant.change_pwd(app_database, new_pwd); 
                        UserManager.create_object_lists(); //update objects to have new password    
                }//only allow these users to change password, else not able to perform any operations in this system
        }
        while (true) {
                EnquiryInterface enquiryInterface = new EnquiryManager();
                System.out.println("Welcome applicant, what would you like to do:");
                String options = 
                        "1. View available projects" +
                        "2. Apply for new application" +
                        "3. Withdraw current application" +
                        "4. Submit enquiries" + //all these are from EnquiryManager
                        "5. Edit enquiries" + //editEnquiry
                        "6. Delete enquiries" +
                        "7. Delete message" +
                        "8. View enquiries" +
                        "9. Change password" +
                        "10. Exit";

                System.out.println(options);
                int choice = sc.nextInt();
                if (choice > 10) {
                        System.out.println("Invalid choice try again!");
                        System.out.println(options);
                        choice = sc.nextInt();
                }
                else {
                switch (choice) {
                case 1 :
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
                                while (filter_choice < 5) {
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
                                        case 4: print_Filter.location = null;
                                                print_Filter.minPrice = null;
                                                print_Filter.maxPrice = null;
                                                break;

                                        default: System.out.println("Invalid option!");
                                        }
                                        System.out.println("Choose filtering option: ");
                                        filter_choice = sc.nextInt();
                                }
                        } 
                        // additional filter such that applicant can only view visibility "ON" projects available to their user group (according to marital status)
                        print_Filter.checkvisibility = true;
                        //check if applicant is single or married 
                        if (applicant.get_marital_stat() != true) {
                                print_Filter.check2room = true; //turn on filter to check for num of 2 rooms, if no 2 rooms, singles cannot apply for project thus not displayed to them
                        }
                        ProjectManager.viewAllProject(print_Filter);
                case 2 : if (applicant.get_typeOf_flat() != null) {
                         System.out.println("You can only book 1 flat! Prior application is successful :)");
                         break;
                         }
                         ApplicationManager.newApplication(applicant);
                         break;
                case 3 : ApplicationManager.requestWithdrawApplication(applicant.get_application());
                        break;  
                case 4 : enquiryInterface.submitEnquiry(applicant, applicant.get_application().getProject());
                         break;
                case 5 : enquiryInterface.editEnquiry(applicant);
                        break;
                case 6 : enquiryInterface.deleteEnquiry(applicant);
                         break;
                case 7 : enquiryInterface.deleteMessage(applicant);
                        break;
                case 8 : enquiryInterface.viewEnquiries(applicant);
                        break;
                case 9 : System.out.println("Enter new password: ");
                         String new_pwd = sc.nextLine();
                         applicant.change_pwd(app_database, new_pwd); 
                         UserManager.create_object_lists(); //update objects to have new password
                         break;
                case 10 : sc.close();
                         return;
                default: System.out.println("Invalid choice!");
                }
                }
        
        }
    }
}