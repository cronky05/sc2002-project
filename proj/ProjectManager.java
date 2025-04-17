import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectManager {
	//array list of all projects
	private static ArrayList<Project> project_list;
	private static ArrayList<Project> active_list;
	private static ArrayList<Project> expired_list;
	private static ArrayList<Project> inactive_list;
	
	public ProjectManager() {
		project_list = new ArrayList<Project>();
		active_list = new ArrayList<Project>();
		expired_list = new ArrayList<Project>();
		inactive_list = new ArrayList<Project>();
	}
	static ArrayList<Project> getProjectList() {
		return project_list;
	}
	static ArrayList<Project> getActiveList() {
		return active_list;
	}
	static ArrayList<Project> getExpiredList() {
		return expired_list;
	}
	static ArrayList<Project> getInactiveList() {
		return inactive_list;
	}
	static void setProjectList(ArrayList<Project> p) {
		project_list = p;
	}
	static void setActiveList(ArrayList<Project> a) {
		active_list = a;
	}
	static void setExpiredList(ArrayList<Project> e) {
		expired_list = e;
	}
	static void setInactiveList(ArrayList<Project> i) {
		inactive_list = i;
	}
	static Scanner sc = new Scanner(System.in);
	
	public static void registerAsOfficer(HDBOfficer officer) {
		//project class have an array list of officers that register to be officer for that particular project
		boolean found = false;
		Project proj = null;
		do {
			viewAllProject(null);
			System.out.println("Select a project to register as officer (Enter project name): ");
			String projName = sc.nextLine();
			for (Project p : project_list) {
				if (p.get_title().equals(projName)) {
					found = true;
					proj = p;
					break;
				}
				System.out.println("Project not found, try again");
			}
		} while(found == false);
		ArrayList<Application> proj_app = proj.get_submissions();
		ArrayList<HDBOfficer> off_list = proj.get_officerList();
		if (off_list.size() >= proj.get_numOfOfficerSlots()) {
			System.out.println("Maximum number of officer slots reached!");
			return;
		}
		boolean non_valid = false;
		for (Application app : proj_app) {
			if (app.getApplicant() == officer) {
				non_valid = true;
			}
		}
		if (non_valid == false && officer.getProjectInCharge() == null) { //condition to register
			//add to pendinglist
			ArrayList<HDBOfficer> pending_reg = proj.get_pendingList();
			pending_reg.add(officer);
		}
	}
	public static void processOfficerRegistration(HDBManager manager) { //approve or reject pending officer registration for particular active project
		Project project = manager.getProject();
		viewOfficerRegistration(manager);
		System.out.println("Enter the number corresponding to the officer to process");
		int choice = sc.nextInt();
		System.out.println("1. Approve");
		System.out.println("2. Reject");
		int decision = sc.nextInt();
		if (decision == 1) {
			//move from pending list to officer list
			ArrayList<HDBOfficer> officers = project.get_officerList();
			ArrayList<HDBOfficer> pending_reg = project.get_pendingList();
			officers.add(pending_reg.get(choice - 1));
			pending_reg.remove(choice - 1);
			officers.get(choice - 1).setStatus("Successful");
			officers.get(choice - 1).setProjectInCharge(project);
		}
		else if (decision == 2) {
			ArrayList<HDBOfficer> officers = project.get_officerList();
			ArrayList<HDBOfficer> pending_reg = project.get_pendingList();
			pending_reg.remove(choice - 1);
			officers.get(choice - 1).setStatus("Failed");
			officers.get(choice - 1).setProjectInCharge(null);
			//remove from pending
		}
	}
	public static void viewOfficerRegistration(HDBManager manager) {
		//view pending
		AtomicInteger index = new AtomicInteger(1);
		Project project = manager.getProject();
		ArrayList<HDBOfficer> pending_reg = project.get_pendingList();
		pending_reg.stream().forEach(officer -> System.out.println("Name: " + index.getAndIncrement() + officer.get_name()));
	}
	public static void viewAllProject(Filter filter) { //include those with visibility off and created by other HDBManagers
		AtomicInteger index = new AtomicInteger(1);
		  project_list.stream().filter(project -> {
		            boolean match = true;
		            if (filter.location != null && !project.get_neighbourhood().equalsIgnoreCase(filter.location)) {match = false;}
		            //2 room flats are cheaper than 3 room flats, still print project if price of either not filtered out
		            if (filter.minPrice != null && project.get_price3room()<filter.minPrice) {match = false;} //ie. although 2 room below min price, 3 room may be above min price --> still list out
		            if (filter.maxPrice != null && project.get_price2room()>filter.maxPrice) {match = false;} //ie. although 3 room above max price, but 2 room below max price --> still list out
					if (filter.check2room == true && project.get_numof2room()<=0) {match = false;} //check if there are two rooms available when this filter is on for singles, else dont show project
		            if (filter.checkvisibility == true && project.get_visibility() != true) {match = false;} //only allow applicants to view projects with visbility on
					return match;
		        }).forEach(project -> System.out.println("Name: " + index.getAndIncrement() + project.get_title())); // if define in ProjectManager class
	}
	public static void viewOwnProject(HDBManager manager, Filter filter) { //can filter by location, view + details to print out
		ArrayList<Project> my_proj = manager.getProjList();

		for (Project p : my_proj) { 
			boolean match = true;
			if (filter.location != null && !p.get_neighbourhood().equalsIgnoreCase(filter.location)) {match = false;}
			if (filter.checkvisibility == true && p.get_visibility() != true) {match = false;} //view current active project with visiblity "ON"
			if (filter.check_old_upcoming == true && p.get_visibility() == true) {match = false;} //view past and upcoming projects with visibility "OFF"

			if (match) {
				System.out.print("Project title: " + p.get_title()+ "; ");
				if (filter.showVisibility) {
					System.out.print("Visibility: ");
					if (p.get_visibility()) //true = ON
						System.out.print("ON; ");
					else
						System.out.print("OFF; ");
				}
				if (filter.showLocation) {
					System.out.print("Location: " + p.get_neighbourhood() +"; ");
				}
			}
			System.out.println();
		}


	}

	public static void createProject(HDBManager manager) {
		System.out.println("Project name: ");
		String name = sc.nextLine();
		System.out.println("Neighborhood: ");
		String neighborhood = sc.nextLine();
		System.out.println("Number of 2 room units: ");
		int numOf2Room = sc.nextInt();
		System.out.println("Price of 2 room units: ");
		int priceOf2Room = sc.nextInt();
		System.out.println("Number of 3 room units: ");
		int numOf3Room = sc.nextInt();
		System.out.println("Price of 3 room units: ");
		int priceOf3Room = sc.nextInt();
		System.out.println("Application Open Date: ");
		System.out.println("Year: ");
		int yearInput1 = sc.nextInt();
		System.out.println("Month: ");
		int monthInput1 = sc.nextInt();
		System.out.println("Day: ");
		int dayInput1 = sc.nextInt();
		LocalDate appOpenDate = LocalDate.of( yearInput1 , monthInput1 , dayInput1);
		while (manager.getProject() != null && manager.getProject().get_closing_date().isAfter(appOpenDate)) { // check whether manager is handling another project at the moment
			System.out.println("Invalid, manager is handling another project at the moment");
			System.out.println("Application Open Date: ");
			System.out.println("Year: ");
			yearInput1 = sc.nextInt();
			System.out.println("Month: ");
			monthInput1 = sc.nextInt();
			System.out.println("Day: ");
			dayInput1 = sc.nextInt();
			appOpenDate = LocalDate.of( yearInput1 , monthInput1 , dayInput1);
		}
		System.out.println("Application Close Date: ");
		System.out.println("Year: ");
		int yearInput2 = sc.nextInt();
		System.out.println("Month: ");
		int monthInput2 = sc.nextInt();
		System.out.println("Day: ");
		int dayInput2 = sc.nextInt();
		LocalDate appCloseDate = LocalDate.of( yearInput2 , monthInput2 , dayInput2);
		System.out.println("Number of officer slots: ");
		int officerSlotsNum = sc.nextInt(); 
		while (officerSlotsNum > 10) {
			System.out.println("Invalid input, enter number smaller than 10");
			officerSlotsNum = sc.nextInt(); 
		}
		Project project = new Project(name, neighborhood, numOf2Room, priceOf2Room, numOf3Room, priceOf3Room, appOpenDate, appCloseDate, manager, officerSlotsNum);
		project_list.add(project);
		if (appOpenDate.isBefore(LocalDate.now())) {
			inactive_list.add(project);
		}
		else {
			active_list.add(project);
		}
		manager.addToProjList(project);
		// add to pending list? active list? 
		// add to expired list if pass application date - use TimerTask
	}
	public static void editProject(HDBManager manager) {
		// include option to select which project??
		System.out.println("Attributes to edit");
		System.out.println("1. Name");
		System.out.println("2. Neighborhood");
		System.out.println("3. Flat type");
		System.out.println("4. Number of units");
		System.out.println("5. Application Open Date");
		System.out.println("6. Application Close Date");
		System.out.println("7. Number of officer slots");
		System.out.println("8. Visibility");
		int attribute = sc.nextInt();
		switch (attribute) {
			case 1:
				System.out.println("Project name: ");
				String name = sc.nextLine();
				manager.getProject().set_title(name);
				break;
			case 2:
				System.out.println("Neighborhood: ");
				String neighborhood = sc.nextLine();
				manager.getProject().set_neighbourhood(neighborhood);
				break;
			case 3:
				System.out.println("Number of 2 room units: ");
				int numOf2Room = sc.nextInt();
				manager.getProject().set_numof2room(numOf2Room);
				break;
			case 4:
				System.out.println("Number of 3 room units: ");
				int numOf3Room = sc.nextInt();
				manager.getProject().set_numof3room(numOf3Room);
				break;
			case 5:
				System.out.println("Application Open Date: ");
				System.out.println("Year: ");
				int yearInput1 = sc.nextInt();
				System.out.println("Month: ");
				int monthInput1 = sc.nextInt();
				System.out.println("Day: ");
				int dayInput1 = sc.nextInt();
				LocalDate appOpenDate = LocalDate.of( yearInput1 , monthInput1 , dayInput1);
				manager.getProject().set_opening_date(appOpenDate);
				break;
			case 6:
				System.out.println("Application Close Date: ");
				System.out.println("Year: ");
				int yearInput2 = sc.nextInt();
				System.out.println("Month: ");
				int monthInput2 = sc.nextInt();
				System.out.println("Day: ");
				int dayInput2 = sc.nextInt();
				LocalDate appCloseDate = LocalDate.of( yearInput2 , monthInput2 , dayInput2);
				manager.getProject().set_closing_date(appCloseDate);
				break;
			case 7:
				System.out.println("Number of officer slots: ");
				int officerSlotsNum = sc.nextInt(); 
				while (officerSlotsNum > 10) {
					System.out.println("Invalid input, enter number smaller than 10");
					officerSlotsNum = sc.nextInt(); 
				}
				manager.getProject().set_numOfOfficerSlots(officerSlotsNum);
				// have a parameter in officer list to make sure it do not exceed limit
				break;
			case 8:
				System.out.println("Current Visibility: " + manager.getProject().get_visibility());
				System.out.println("Visibility set to: ");
				boolean visibility = sc.nextBoolean();
				manager.getProject().set_visibility(visibility);
				System.out.println("Current Visibility: " + manager.getProject().get_visibility());
				break;
		}
		
	}
	public static void deleteProject(HDBManager manager) {
		Project deletedProject = manager.getProject();
		manager.setProject(null);
		project_list.remove(deletedProject);
		if (active_list.contains(deletedProject)) {
			active_list.remove(deletedProject);
		} 
		else {
			inactive_list.remove(deletedProject); // questionable
		}
	}
	public static void viewProjectDetails(HDBOfficer officer) { //officer can view details of project in charge of regardless of visbility
		Project project = officer.getProjectInCharge();
		System.out.println("Project name: " + project.get_title());
		System.out.println("Neighborhood: " + project.get_neighbourhood());
		System.out.println("Number of 2 room units: " + project.get_numof2room());
		System.out.println("Number of 3 room units: " + project.get_numof3room());
		System.out.println("Application Open Date: " + project.get_opening_date());
		System.out.println("Application Close Date: " + project.get_closing_date());
		AtomicInteger index = new AtomicInteger(1);
		project.get_officerList().stream().forEach(off -> System.out.println(index.getAndIncrement() + ". " + off.get_name()));
		System.out.println("HDB Manager: " + project.get_managerIC());
		// add applications submissions??
	}

	//manually turn on or off the visibility of the projects HDBManager created
    public static void toggle_visibility(HDBManager manager) {
        Scanner sc = new Scanner(System.in);
		ArrayList<Project> projs = manager.getProjList();
        while (true) {
            System.out.println("Enter title of project: ");
            String pj_title = sc.nextLine();
			Project edit = null;
            for (Project p : projs) {
                if (p.get_title().equalsIgnoreCase(pj_title)) {
                    edit = p;
                }
            }
            System.out.println("1. Turn On, 2. Turn Off");
            int choice = sc.nextInt();
            if (choice == 1)
                edit.set_visibility(true);
            else
                edit.set_visibility(false);
            System.out.println("Edit more projects? Y/N");
            char choose = Character.toUpperCase(sc.next().charAt(0));
            if (choose =='N')
                break;
        }
		sc.close();
    }
}
