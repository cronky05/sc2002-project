import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HDBOfficerDisplay {
	
	public static void start(HDBOfficer officer, HashMap<String, List<String>> off_database, HashMap<String, HashMap<String, List<String>>> data_base) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Choose what do you want to do:");
			System.out.println("1.Register to join a project.");
			System.out.println("2.Check the status of the registration.");
			System.out.println("3.View the details of the project you are handling.");
			System.out.println("4.View the enquiries of the project you are handling.");
			System.out.println("5.Retrieve applicant’s BTO application with applicant’s NRIC.");
			System.out.println("6.Update the information after successful BTO application.");
			System.out.println("7.Generate receipt of the applicants.");
			System.out.println("-----------------------------------------------");
			System.out.println("8.Apply for BTO (not in charge of)."); //abiliites as an Applicant (Officer inherits from Applicant, have applicant capabilities)
			System.out.println("9.Withdraw current application.");
			System.out.println("10.Submit enquiry.");
			System.out.println("11.Edit enquiry.");
			System.out.println("12.Delete enquiry.");
			System.out.println("13.Delete messages.");
			System.out.println("14.View enquiry (for BTO applied for).");
			System.out.println("15.Change password");
			System.out.println("16.Log out");
			System.out.print("Choice:");
			
			int choice = sc.nextInt();
			sc.nextLine();
			EnquiryInterface enquiryInterface = new EnquiryManager();
			switch(choice) {
			case 1 :
				ProjectManager.registerAsOfficer(officer);
				break;
			case 2 :
				if(officer.getStatus()=="Successful") {
					System.out.println("Your registration has been approved!");
				}
				else if (officer.getStatus()=="Failed") {
					System.out.println("Your registration has been rejected.");
				}
				else {
					System.out.println("Your registration is pending...");
				}
				break;
			case 3:
				ProjectManager.viewProjectDetails(officer);
				break;
			case 4:
				enquiryInterface.viewEnquiries(officer);
				break;
			case 5:
				String nric=sc.nextLine();
				Project pro=new Project(null, null, 0, 0, 0, 0, null, null, null, 0);
				for (Application app : pro.get_successful()) {
				    if (app.getApplicant().get_nric().equals(nric)) {
				        System.out.println("Found matching application: " + app);
				        break;
				    }
				}
				System.out.println("Didn't find matching application.");
				break;
			case 6:
				//update remaining flat是不是已经在applicationmanager写完了
				System.out.println("Input the flat type:");
				String flat=sc.nextLine();
				ApplicationManager.bookingFlat(officer,flat);
				//需要更新applicant profile???
				break;
			case 7:
				ApplicationManager.printReceipt(officer);
				break;
			case 8: //functions ensures that Officer can only see projects he is not handling as well as available to his age group/marital status
				ApplicationManager.newApplication(officer);
				break;
			case 9:
				ApplicationManager.requestWithdrawApplication(officer.get_application());
				break;
			case 10:
				enquiryInterface.submitEnquiry(officer, officer.get_application().getProject());
				break;
			case 11:
				enquiryInterface.editEnquiry(officer);
				break;
			case 12:
				enquiryInterface.deleteEnquiry(officer);
				break;
			case 13:
				enquiryInterface.deleteMessage(officer);
				break;
			case 14:
				Applicant applicant=new Applicant(null, null, null, null);
				applicant=officer;
				enquiryInterface.viewEnquiries(applicant);
				break;
			case 15: 
				System.out.println("Enter new password: ");
				String new_pwd = sc.nextLine();
				officer.change_pwd(off_database, new_pwd); 
				UserManager.create_object_lists(data_base); //update objects to have new password
				return; //prompt relogin after change password
			case 16:
				System.out.println("Logging out...");
				return;
			default: System.out.println("Invalid choice!");
			}
			
		}
		
		
	}

}
