public class Application {
	private Applicant applicant;
	private Project project;
	private String status;
	private String roomtype; //will set to "two" or "three" depending on applicant's marital status
	
	
	public Application(Applicant applicant,Project project, String num_room) {
		this.applicant=applicant;
		this.project=project;
		this.status="Pending";
		this.roomtype=num_room;
		
	}
	
	public Project getProject() {
		return project;
	}
	
	public Applicant getApplicant() {
		return applicant;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getRoomType() {
		return roomtype;
	}
	
	
	public void setFlat(String flat) {
		this.roomtype=flat;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	
}
