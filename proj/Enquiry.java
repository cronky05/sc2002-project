import java.util.ArrayList;
import java.util.List;

public class Enquiry {
	private Applicant sender;
	private List<String[]> messageReplyPairs;
	private Project project;
	
	public Enquiry(Applicant applicant,Project pro) {
		this.sender=applicant;
		this.messageReplyPairs = new ArrayList<>();
		this.project=pro;
	}
	
	 public List<String[]> getMessageReplyPairs() {
	        return messageReplyPairs;
	    }

    public String getMessage(int index) {
        if (index >= 0 && index < messageReplyPairs.size()) {
            return messageReplyPairs.get(index)[0];
        }
        return "Invalid index";
    }
    
    public String getReply(int index) {
        if (index >= 0 && index < messageReplyPairs.size()) {
            return messageReplyPairs.get(index)[1];
        }
        return "Invalid index";
    }
	
	public Project getProject() {
		return project;
	}
	
	public Applicant getSender() {
		return sender;
	}
	
	
	public void addMessage(String message) {
	        this.messageReplyPairs.add(new String[]{message, null});
	}
	public void setReply(int index, String reply) {
	        if (index >= 0 && index < messageReplyPairs.size()) {
	            messageReplyPairs.get(index)[1] = reply;
	        }
	}
	
	
}
