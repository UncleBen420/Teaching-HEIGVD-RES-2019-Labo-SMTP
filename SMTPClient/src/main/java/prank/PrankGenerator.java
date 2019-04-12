package prank;

import java.util.ArrayList;
import java.util.List;
import Vuagniaux.SMTPClient.Mail;
import person.Group;
import person.Person;
import properties.PropertiesClientSmtp;

/**
 * S'occupe de generer une liste de mail de prank.
 */
public class PrankGenerator {
	List<Group> groups = null;
	List<Prank> pranks = null;
	PropertiesClientSmtp properties;

	/**
	 * Constructeur.
	 * @param properties les proprietes du prank
	 */
	public PrankGenerator(PropertiesClientSmtp properties) {
		this.properties = properties;
	}

	/**
	 * Recupere les groupes du prank.
	 * @return les groupes du prank
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * Defini les groupes du prank.
	 * @param groups les groupes a definir
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * Recupere les pranks.
	 * @return les pranks
	 */
	public List<Prank> getPranks() {
		return pranks;
	}

	/**
	 * Defini les pranks.
	 * @param pranks les pranks a definir
	 */
	public void setPranks(List<Prank> pranks) {
		this.pranks = pranks;
	}

	/**
	 * Cree les mails de prank
	 * @return la liste de tous les mails a envoyer
	 */
	public List<Mail> generatePrank(){
		int indexPrank = 0;
		List<Mail> temp = new ArrayList<Mail>();
		for(Group g : groups) {
			for(Person p : g.getReceivers()) {
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