import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HDBManager extends User{
    private ArrayList<Project> projs; //list of those created by this.HDBManager
    private Project activeProject; //HDBManager can only handle 1 project at the time

    public HDBManager(String nric, String pwd, String role, HashMap<String, List<String>> correct_map) {
        super(nric, pwd, role, correct_map);
        this.projs = new ArrayList<>();
        this.activeProject = null;
    }

    public void addToProjList(Project p) {
        projs.add(p);
    }
    
    public Project getProject() {
        return activeProject;
    }

    //setter method for Project array;
    public void setProject(Project proj) {
        activeProject = proj;
    }
    public ArrayList<Project> getProjList() {return projs;}

    public void delProjListItem(Project p) {
        projs.remove(p);
    }

    //generate report of list of applicant with respecitve flat booking + filters
        //filters to generate list based on various categories --> eg. print only married couples with type of flat
        //print flat type, project name, age, marital status
    public void generate_report(Filter filter) {
        //for each proj in projs
        for (Project proj : projs) {
            for (Application app : proj.get_successful()) {
                Applicant applicant = app.getApplicant();
                boolean match = true;
                if (filter.flatType != null && !app.getRoomType().equalsIgnoreCase(filter.flatType)) match = false;
                if (filter.filter_marital != null && !(applicant.get_marital_stat()==(filter.filter_marital=="married"))) match = false;
                if (filter.minAge != null && applicant.get_age()<filter.minAge) match = false;
                if (filter.maxAge != null && applicant.get_age()>filter.maxAge) match = false;

                if (match) {
                    if (filter.showProjectName) {
                        System.out.print("Project name: " + proj.get_title() +"; ");
                    }
                    if (filter.showFlatType) {
                        System.out.print("Flat type: " + app.getRoomType() + "-room; "); //to check getter method name in Application class
                    }
                    if (filter.showMarital) {
                        System.out.print("Marital Status: ");
                        if (applicant.get_marital_stat())
                            System.out.print("Married; ");
                        else
                            System.out.print("Single; ");
                    }
                    if (filter.showAge) {
                        System.out.println("Age: " + applicant.get_age() + "; ");
                    }
                }
                System.out.println();
            }
            System.out.println();

        }
        //in Main.java --> Filter filter = new Filter();
        //specify and set filters ie. filter.element = ...
        //have an option on whether want to set filters or not; else can skip setting filters and print default report (have all elements)
    }
}
