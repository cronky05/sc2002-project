import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnquiryManager implements EnquiryInterface{
	private List<Enquiry> enquiries;
	
	public EnquiryManager() {
        this.enquiries = new ArrayList<>();
    }
	
	
	public void submitEnquiry(Applicant applicant,Project project) {
        if (applicant.get_application()==null){
            System.out.println("Error");
            return;
        }

		Enquiry enquiry=new Enquiry(applicant,project);
		
		System.out.println("Enter your enquiry message:");
		Scanner sc=new Scanner(System.in);
		String message=sc.nextLine();
		enquiry.addMessage(message);
		
		this.enquiries.add(enquiry);
		System.out.println("Enquiry submitted!");
		sc.close();
	}
	
	
	public void editEnquiry(Applicant applicant) {
		
		System.out.println("Here are your enquiries:");
		Scanner sc=new Scanner(System.in);
		
		
        Enquiry applicantEnquiries = new Enquiry(null, null);
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getSender().equals(applicant)) {
                applicantEnquiries=enquiry;
            }
        }
        
        if (applicantEnquiries.getSender()==null) {
            System.out.println("No enquiries found for this applicant.");
            return;
        }
        System.out.println("Here are all the messages you have submitted:");
        for (int j = 0; j < applicantEnquiries.getMessageReplyPairs().size(); j++) {
            System.out.println("Message " + j + ": " + applicantEnquiries.getMessage(j));
            System.out.println("Reply   " + j + ": " + (applicantEnquiries.getReply(j) == null ? "No reply yet" : applicantEnquiries.getReply(j)));
        }
        
        System.out.println("Select an massage number to edit (1 - " + applicantEnquiries.getMessageReplyPairs().size() + "):");
        int Index = sc.nextInt() - 1;
        sc.nextLine();
        if (Index < 0 || Index >= applicantEnquiries.getMessageReplyPairs().size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        
        System.out.println("Enter new message:");
        String newMessage = sc.nextLine();
        applicantEnquiries.getMessageReplyPairs().get(Index)[0] = newMessage;
        
        System.out.println("Message edited successfully!");
        sc.close();
    }
	
	
	public void deleteEnquiry(Applicant applicant) {
		
    boolean deleted = false;
    for (int i = 0; i < enquiries.size(); i++) {
      if (enquiries.get(i).getSender().equals(applicant)) {
        enquiries.remove(i);
        i--;
        deleted = true;
        applicant.set_enquiry(null);
      }
    }
    if (deleted) {
      System.out.println("All enquiries from the applicant have been deleted.");
    } 
    else {
      System.out.println("No enquiries found for this applicant.");
    }
  }
	
	public void deleteMessage(Applicant applicant) {
        if(applicant.get_enquiry()==null){
            System.out.println("No enquiries found for this applicant");
            return;
        }
		
		Enquiry applicantEnquiries = new Enquiry(null, null);
		for (int i=0;i<enquiries.size();i++) {
			if(enquiries.get(i).getSender().equals(applicant)) {
				 applicantEnquiries=enquiries.get(i);
			}
		}
		Scanner sc=new Scanner(System.in);
		System.out.println("Here are all the messages you have submitted:");
        for (int j = 0; j < applicantEnquiries.getMessageReplyPairs().size(); j++) {
            System.out.println("Message " + j + ": " + applicantEnquiries.getMessage(j));
            System.out.println("Reply   " + j + ": " + (applicantEnquiries.getReply(j) == null ? "No reply yet" : applicantEnquiries.getReply(j)));
        }
        System.out.println("Choose the message you want to delete:");
		int index=sc.nextInt()-1;
		if (index<0||index>=applicantEnquiries.getMessageReplyPairs().size()) {
			System.out.println("Invalid selection.");
			return;
		}
        applicantEnquiries.getMessageReplyPairs().remove(index);
        System.out.println("Massage successfully deleteMessage!");
		
		
	}
	
	public void replyEnquiry(HDBManager manager) {
  
        Scanner sc=new Scanner(System.in);
        
        List<Enquiry> projectEnquiries = new ArrayList<>();
              for (Enquiry e : enquiries) {
               ArrayList<Project> p=manager.getProjList();
               for (Project project : p) {
                if(project.equals(e.getProject())) {
                  projectEnquiries.add(e);
                }
               }
                  
              }
              if (projectEnquiries.isEmpty()) {
                  System.out.println("No enquiries for your project.");
                  return;
              }
              
              System.out.println("Available applicants:");
              for (int i = 0; i < projectEnquiries.size(); i++) {
                  System.out.println((i + 1) + ". " + projectEnquiries.get(i).getSender().get_nric());
              }
      
              System.out.println("Select an applicant number:");
              int applicantIndex = sc.nextInt() - 1;
              sc.nextLine();
              
              if (applicantIndex < 0 || applicantIndex >= projectEnquiries.size()) {
                  System.out.println("Invalid selection.");
                  return;
              }
              
              Enquiry selected = projectEnquiries.get(applicantIndex);
              List<String[]> pairs = selected.getMessageReplyPairs();
      
              System.out.println("Messages from applicant:");
              for (int i = 0; i < pairs.size(); i++) {
                  System.out.println(i + ": " + pairs.get(i)[0]);
                  System.out.println("   Current reply: " + (pairs.get(i)[1] == null ? "No reply yet" : pairs.get(i)[1]));
              }
      
              System.out.println("Select a message number to reply:");
              int msgIndex = sc.nextInt();
              sc.nextLine();
      
              if (msgIndex < 0 || msgIndex >= pairs.size()) {
                  System.out.println("Invalid message index.");
                  return;
              }
              System.out.println("Enter your reply:");
              String reply = sc.nextLine();
              selected.setReply(msgIndex, reply);
      
              System.out.println("Reply added successfully!");
              
              sc.close();
          }

	public void replyEnquiry(HDBOfficer officer) {
		
		Scanner sc=new Scanner(System.in);
		
		List<Enquiry> projectEnquiries = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (e.getProject().equals(officer.getProjectInCharge())) {
                projectEnquiries.add(e);
            }
        }
        if (projectEnquiries.isEmpty()) {
            System.out.println("No enquiries for your project.");
            return;
        }
        
        System.out.println("Available applicants:");
        for (int i = 0; i < projectEnquiries.size(); i++) {
            System.out.println((i + 1) + ". " + projectEnquiries.get(i).getSender().get_nric());
        }

        System.out.println("Select an applicant number:");
        int applicantIndex = sc.nextInt() - 1;
        sc.nextLine();
        
        if (applicantIndex < 0 || applicantIndex >= projectEnquiries.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Enquiry selected = projectEnquiries.get(applicantIndex);
        List<String[]> pairs = selected.getMessageReplyPairs();

        System.out.println("Messages from applicant:");
        for (int i = 0; i < pairs.size(); i++) {
            System.out.println(i + ": " + pairs.get(i)[0]);
            System.out.println("   Current reply: " + (pairs.get(i)[1] == null ? "No reply yet" : pairs.get(i)[1]));
        }

        System.out.println("Select a message number to reply:");
        int msgIndex = sc.nextInt();
        sc.nextLine();

        if (msgIndex < 0 || msgIndex >= pairs.size()) {
            System.out.println("Invalid message index.");
            return;
        }
        System.out.println("Enter your reply:");
        String reply = sc.nextLine();
        selected.setReply(msgIndex, reply);

        System.out.println("Reply added successfully!");
        
        sc.close();
    }
		
	
	public void viewEnquiries(Applicant applicant) {
        if(applicant.get_enquiry()==null){
            System.out.println("No enquiry for this applicant.");
            return;
        }
		for (Enquiry enquiry : enquiries) {
            if (enquiry.getSender().equals(applicant)) {
                System.out.println("Project: " + enquiry.getProject());
                List<String[]> pairs = enquiry.getMessageReplyPairs();
                for (int j = 0; j < pairs.size(); j++) {
                    System.out.println("Message " + (j + 1) + ": " + pairs.get(j)[0]);
                    System.out.println("Reply   " + (j + 1) + ": " + (pairs.get(j)[1] == null ? "No reply yet" : pairs.get(j)[1]));
                }
                System.out.println();
            }
        }
	}
	
	
	public void viewEnquiries(HDBOfficer officer) {
        boolean found=false;
		 for (Enquiry enquiry : enquiries) {
	            if (enquiry.getProject().equals(officer.getProjectInCharge())) {
                    found=true;
	                System.out.println("Applicant: " + enquiry.getSender());
	                List<String[]> pairs = enquiry.getMessageReplyPairs();
	                for (int j = 0; j < pairs.size(); j++) {
	                    System.out.println("Message " + (j + 1) + ": " + pairs.get(j)[0]);
	                    System.out.println("Reply   " + (j + 1) + ": " + (pairs.get(j)[1] == null ? "No reply yet" : pairs.get(j)[1]));
	                }
	                System.out.println("Project: " + enquiry.getProject());
	                System.out.println();
	            }
	        }
         if(!found){
             System.out.println("No enquiries for project: " + officer.getProjectInCharge());
         }

	}
	
	
	public void viewEnquiries(HDBManager manager) {
        boolean found=false;
		Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to view enquiries of all projects or only your project?");
        System.out.println("1 - All projects, 2 - Your project");
        int choice = sc.nextInt();

        for (Enquiry enquiry : enquiries) {
            if (choice == 1 ) {
              ArrayList<Project> projects = manager.getProjList();
              for (Project p : projects) {
                if (enquiry.getProject().equals(p)) {
                    found=true;
                  System.out.println("Project:"+ enquiry.getProject());
                  System.out.println("Applicant: " + enquiry.getSender());
                }
               
                List<String[]> pairs = enquiry.getMessageReplyPairs();
                for (int j = 0; j < pairs.size(); j++) {
                    System.out.println("Message " + (j + 1) + ": " + pairs.get(j)[0]);
                    System.out.println("Reply   " + (j + 1) + ": " + (pairs.get(j)[1] == null ? "No reply yet" : pairs.get(j)[1]));
                }
                System.out.println();
            }
        }
    }
        if(!found){
            System.out.println("No enquiries found.");
        }
	
  }
}


    