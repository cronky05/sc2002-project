import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HDBOfficerDisplay {
	
	public static void start(HDBOfficer officer, HashMap<String, List<String>> off_database, HashMap<String, HashMap<String, List<String>>> data_base) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			EnquiryInterface enquiryInterface = new EnquiryManager();
			System.out.println("==============================");
            System.out.println("Welcome " + officer.get_name() +", what would you like to do:");

			String option1 =//not handling a project and no application 
                        "1. Register to join a project \n" +
						"2. Check the status of the registration\n"+
						"-----------------------------------------------\n"+
						"3. View available projects as applicant\n"+
						"4. Apply for BTO\n" + 
						"5. Change password \n" + 
						"6. Log out";

			String option2=//handling a project and no application
			            "1. Check the status of the registration\n "+
						"2. View the details of the project you are handling\n" +
                        "3. View the enquiries of the project you are handling \n" + 
                        "4. Reply enquiries of the project you are handling \n" + 
                        "5. Retrieve applicant’s BTO application with applicant’s NRIC\n" +
						"6. Update the information after successful BTO application\n" +
						"7. Generate receipt of the applicants \n "+
						"-----------------------------------------------\n"+
						"8. View available projects as applicant\n"+
						"9. Apply for BTO\n" + 
						"10. Change password \n" + 
						"11. Log out";
					
			String option3=//not handling a project and have an application
			            "1. Register to join a project \n" +
						"2. Check the status of the registration\n"+
						"-----------------------------------------------\n"+
						"3. Withdraw current application \n"+
						"4. Submit enquiry \n" + //all these are from EnquiryManager
                        "5. Edit enquiry \n" + //editEnquiry
                        "6. Delete enquiry \n" +
                        "7. Delete specific message \n" +
                        "8. View enquiry of the application you created \n" +
                        "9. View Application Status\n"+
                        "10.Change password \n"+
						"11.Log out ";

			String option4=//handling a project and have an application
			            "1. Check the status of the registration \n" +
						"2. View the details of the project you are handling\n" +
                        "3. View the enquiries of the project you are handling \n" + 
                        "4. Reply enquiries of the project you are handling \n" + 
                        "5. Retrieve applicant’s BTO application with applicant’s NRIC\n" +
						"6. Update the information after successful BTO application\n" +
						"7. Generate receipt of the applicants \n "+
						"-----------------------------------------------\n"+
						"8. Withdraw current application \n"+
						"9. Submit enquiry \n" + //all these are from EnquiryManager
                        "10. Edit enquiry \n" + //editEnquiry
                        "11. Delete enquiry \n" +
                        "12. Delete specific message \n" +
                        "13. View enquiry of the application you created \n" +
                        "14. View Application Status\n"+
                        "15.Change password \n"+
						"16.Log out ";


			if (officer.getProjectInCharge()==null && officer.get_application()==null){
				System.out.println(option1);
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice>5){
					System.out.println("Invalid choice try again!");
					
				}
				else{
					switch(choice){
						case 1:
						    ProjectManager.registerAsOfficer(officer);
						    break;
						case 2:
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
						// additional filter such that officer can only view visibility "ON" projects available to their user group (according to marital status)
						print_Filter.checkvisibility = true;
						//check if officer is single or married
						if (officer.get_marital_stat() != true) {
								print_Filter.check2room = true; //turn on filter to check for num of 2 rooms, if no 2 rooms, singles cannot apply for project thus not displayed to them
						}
						ProjectManager.viewAllProject(print_Filter);
						break;
					case 4:
					    ApplicationManager.newApplication(officer);
					    break;
					case 5:
					    System.out.println("Enter new password: ");
					    String new_pwd = sc.nextLine();
					    officer.change_pwd(off_database, new_pwd);
					    UserManager.create_object_lists(data_base); //update objects to have new password
					    return; //prompt relogin after change password
					case 6:
					    return;
					default:
					    System.out.println("Invalid choice!");
					}
				}
			}

			else if (officer.getProjectInCharge()!=null && officer.get_application()==null){
				System.out.println(option2);
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice>11){
					System.out.println("Invalid choice try again!");
					
				}
				else{
					switch(choice){
						case 1:
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
						case 2:
							ProjectManager.viewProjectDetails(officer);
							break;
						case 3:
							enquiryInterface.viewEnquiries(officer);
							break;
						case 4:
						    enquiryInterface.replyEnquiry(officer);
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
							System.out.println("Input the flat type:");
							String flat=sc.nextLine();
							ApplicationManager.bookingFlat(officer,flat);
						case 7:
							ApplicationManager.printReceipt(officer);
							break;
						case 8:
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
						// additional filter such that officer can only view visibility "ON" projects available to their user group (according to marital status)
						print_Filter.checkvisibility = true;
						//check if officer is single or married
						if (officer.get_marital_stat() != true) {
								print_Filter.check2room = true; //turn on filter to check for num of 2 rooms, if no 2 rooms, singles cannot apply for project thus not displayed to them
						}
						ProjectManager.viewAllProject(print_Filter);
						break;
						case 9:
							ApplicationManager.newApplication(officer);
							break;
						case 10:
							System.out.println("Enter new password: ");
					    	String new_pwd = sc.nextLine();
					    	officer.change_pwd(off_database, new_pwd);
					    	UserManager.create_object_lists(data_base); //update objects to have new password
					    	return;
						case 11:
							return;
						default:System.out.println("Invalid choice!");	    
					}
				}
			}

			if (officer.getProjectInCharge()==null && officer.get_application()!=null){
				System.out.println(option3);
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice>11){
					System.out.println("Invalid choice try again!");
					
				}
				else{
					switch(choice){
						case 1:
						    ProjectManager.registerAsOfficer(officer);
						    break;
						case 2:
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
							ApplicationManager.requestWithdrawApplication(officer.get_application());
							break;
						case 4:
							enquiryInterface.submitEnquiry(officer, officer.get_application().getProject());
							break;
						case 5:
							enquiryInterface.editEnquiry(officer);
							break;
						case 6:
							enquiryInterface.deleteEnquiry(officer);
							break;
						case 7:
							enquiryInterface.deleteMessage(officer);
							break;
						case 8:
							Applicant applicant=new Applicant(null, null, null, null);
							applicant=officer;
							enquiryInterface.viewEnquiries(applicant);
							break;
						case 9:
							System.out.println("Current application status: " + officer.get_application().getStatus());
							break;
						case 10:
							System.out.println("Enter new password: ");
					    	String new_pwd = sc.nextLine();
					    	officer.change_pwd(off_database, new_pwd);
					    	UserManager.create_object_lists(data_base); //update objects to have new password
					    	return;
						case 11:
							return;
						default:
							System.out.println("Invalid choice!");
					}
				}
			}
			if (officer.getProjectInCharge()!=null && officer.get_application()!=null){
				System.out.println(option4);
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice>16){
					System.out.println("Invalid choice try again!");
					
				}
				else{
					switch(choice){
						case 1:
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
						case 2:
							ProjectManager.viewProjectDetails(officer);
							break;
						case 3:
							enquiryInterface.viewEnquiries(officer);
							break;
						case 4:
						    enquiryInterface.replyEnquiry(officer);
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
							System.out.println("Input the flat type:");
							String flat=sc.nextLine();
							ApplicationManager.bookingFlat(officer,flat);
						case 7:
							ApplicationManager.printReceipt(officer);
							break;
						case 8:
							ApplicationManager.requestWithdrawApplication(officer.get_application());
							break;
						case 9:
							enquiryInterface.submitEnquiry(officer, officer.get_application().getProject());
							break;
						case 10:
							enquiryInterface.editEnquiry(officer);
							break;
						case 11:
							enquiryInterface.deleteEnquiry(officer);
							break;
						case 12:
							enquiryInterface.deleteMessage(officer);
							break;
						case 13:
							Applicant applicant=new Applicant(null, null, null, null);
							applicant=officer;
							enquiryInterface.viewEnquiries(applicant);
							break;
						case 14:
							System.out.println("Current application status: " + officer.get_application().getStatus());
							break;
						case 15:
							System.out.println("Enter new password: ");
					    	String new_pwd = sc.nextLine();
					    	officer.change_pwd(off_database, new_pwd);
					    	UserManager.create_object_lists(data_base); //update objects to have new password
					    	return;
						case 16:
							return;
						default:
							System.out.println("Invalid choice!");
					}
				}
			}
		}
	}
}

						    
	