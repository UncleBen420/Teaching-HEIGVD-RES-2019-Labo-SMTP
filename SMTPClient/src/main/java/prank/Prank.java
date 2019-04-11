package prank;

public class Prank {
	
	String subject, prank;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPrank() {
		return prank;
	}

	public void setPrank(String prank) {
		this.prank = prank;
	}

	@Override
	public String toString() {
		return "Prank [subject=" + subject + ", prank=" + prank + "]";
	}
	
	

}
