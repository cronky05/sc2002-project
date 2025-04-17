package everything;

public class Filter {
    String flatType; //"two" or "three" --> everything2.Application attribute is String type_of_room
    String filter_marital; //include in report if eg. "single", all singles included, not filtered away
    Integer minAge; //include in report if above minAge
    Integer maxAge; //include in report if below maxAge
    //use wrapper class so can set as null to check if there is any specified value set to filter

    //everything2.HDBManager report filters --> initially all true to allow printing of all details if no filters are set
    boolean showFlatType = true;
    boolean showProjectName = true;
    boolean showAge = true;
    boolean showMarital = true;
    
    //everything2.User (+subclasses) view project details filters
    String location; //neighbourhood
    Integer minPrice;
    Integer maxPrice;
    boolean check2room = false; //auto-set filter to true for singles, if project does not have 2-rooms then not shown to singles
    boolean checkvisibility = false; //turn on for applicants only
    //check for each everything2.Project and print name if pass filters

    //everything2.ProjectManager - viewOwnProject()
    boolean showVisibility = true;
    boolean showLocation = true;
    boolean check_old_upcoming = false; //see projects with visibility off

    //in everything2.Main.java --> can choose which filters to apply or specify
}
