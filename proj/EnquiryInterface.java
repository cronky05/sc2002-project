public interface EnquiryInterface {
	void submitEnquiry(Applicant applicant, Project project);
    void editEnquiry(Applicant applicant);
    void deleteEnquiry(Applicant applicant);
    void deleteMessage(Applicant applicant);
    void replyEnquiry(HDBOfficer officer);
    void replyEnquiry(HDBManager manager);
    void viewEnquiries(Applicant applicant);
    void viewEnquiries(HDBOfficer officer);
    void viewEnquiries(HDBManager manager);

}
