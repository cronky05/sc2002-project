import java.util.HashMap;
import java.util.List;

public class HDBOfficer extends Applicant{
	
	private Project project_in_charge;
	private String status; //Successful or failed, null if never apply any
	
	public HDBOfficer(String nric, String pwd, String role, HashMap<String, List<String>> correct_map) {
		super(nric,pwd,role,correct_map);
		this.project_in_charge=null;
		this.status=null;
		
	}
	
	
	public Project getProjectInCharge(){
		return project_in_charge;
	}
	public String getStatus() {
		return status;
	}

	
	public void setProjectInCharge(Project project) {
		this.project_in_charge=project;
	}
	public void setStatus(String s) {
		this.status=s;
	}
	
	
}
