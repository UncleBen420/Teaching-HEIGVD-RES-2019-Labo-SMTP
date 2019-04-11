package prank;

import java.util.ArrayList;
import java.util.List;

import Vuagniaux.SMTPClient.Mail;
import person.Group;
import person.Person;
import properties.PropertiesClientSmtp;

public class PrankGenerator {
	
	List<Group> groups = null;
	List<Prank> pranks = null;
	PropertiesClientSmtp properties;
	
	public PrankGenerator(PropertiesClientSmtp properties) {
		this.properties = properties;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<Prank> getPranks() {
		return pranks;
	}
	public void setPranks(List<Prank> pranks) {
		this.pranks = pranks;
	}
	
	public List<Mail> generatePrank(){
		int indexPrank = 0;
		List<Mail> temp = new ArrayList<Mail>();
		for(Group g : groups) {
			for(Person p : g.getVictim()) {
				Mail mail = new Mail();
				mail.setCc(properties.getCc());
				mail.setFrom(g.getSender().getAddress());
				mail.setFromInMail(g.getSender().getAddress());
				mail.setTo(p.getAddress());
				mail.setToInMail(p.getAddress());
				mail.setSubject(pranks.get(indexPrank).getSubject());
				mail.setMessage(pranks.get(indexPrank).getPrank());
				indexPrank = (indexPrank + 1) % pranks.size();
				temp.add(mail);
				
			}
		}
		
		return temp;
		
	}

}
