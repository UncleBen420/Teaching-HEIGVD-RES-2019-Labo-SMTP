package Vuagniaux.SMTPClient;

public class Mail {
	
	private String from, to;
	private String fromInMail, toInMail, subject, message;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFromInMail() {
		return fromInMail;
	}
	public void setFromInMail(String fromInMail) {
		this.fromInMail = fromInMail;
	}
	public String getToInMail() {
		return toInMail;
	}
	public void setToInMail(String toInMail) {
		this.toInMail = toInMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
