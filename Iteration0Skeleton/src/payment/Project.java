package payment;

public abstract class Project {
	int projectID; //ID for the project, should auto increment in DB
	String projectName; //Name for the project
	int vendorID; // ID for vendor
	Double payRate; //payrate for whomever accepts this project
	
	public Project(String inProjectName, int inVendorID, double inPayRate){
		
	}
}
