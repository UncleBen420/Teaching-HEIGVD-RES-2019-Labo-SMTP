package Vuagniaux.SMTPClient;

public class Mail {
	
	private String from, to;
	private String fromInMail, toInMail, cc, subject, message;
	
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
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
	@Override
	public String toString() {
		return "Mail [from=" + from + ", to=" + to + ", fromInMail=" + fromInMail + ", toInMail=" + toInMail + ", cc="
				+ cc + ", subject=" + subject + ", message=" + message + "]";
	}
	

}
