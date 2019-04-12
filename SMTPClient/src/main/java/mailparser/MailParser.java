package mailparser;

import java.util.ArrayList;
import java.util.List;
import Vuagniaux.reader.Reader;
import person.Group;
import person.Person;
import prank.Prank;
import properties.PropertiesClientSmtp;

/**
 * S'occupe de parser les messages de prank et les infos sur les groupes de victimes
 */
public class MailParser {
	PropertiesClientSmtp properties;
	
	public MailParser(PropertiesClientSmtp properties) {
		this.properties = properties;
	}

	/**
	 * Recupere le message de prank et le forme correctement pour pouvoir
	 * l'inclure dans le mail
	 * @param path le chemin du fichier des messages
	 * @return le message de prank correctement forme
	 */
	public List<Prank> parsePrank(String path) {
		// Lecture du fichier
		Reader reader = new Reader(path);
		List<Prank> pranks = new ArrayList<Prank>();
		
		String pranksList = reader.bufferedReading();

		// On separe les messages suivant le separateur voulu
		for(String s : pranksList.split(properties.getSeparator())) {
			Prank prank = new Prank();

			try {
				// On separe le sujet du message lui meme
				prank.setSubject(s.substring(s.indexOf(properties.getSubjectBalise()) + properties.getSubjectBalise().length(), s.indexOf(properties.getTextBalise())));
				prank.setPrank(s.substring(s.indexOf(properties.getTextBalise()) + properties.getTextBalise().length()));
				
				pranks.add(prank);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Une prank n'a pas ete correctement parsee");			
			}
		}
		return pranks;
	}

	/**
	 * S'occupe de lire le fichier des mails des victimes et de former
	 * les groupes et les personnes dans ces groupes
	 * @param path le chemin du fichier des mails des victimes
	 * @return Les groupes de personnes
	 */
	public List<Group> parsePerson(String path) {
		// Lecture du fichier
		Reader reader = new Reader(path);
		List<Group> groups  = new ArrayList<Group>();
		
		List<String> mailsList = reader.lineBufferedReading();
		
		int i = 0;
		Group group = null;

		for(String mail : mailsList) {
			// On doit d'abord creer le groupe. Le premier mail est celui de l'expediteur
			if(i == 0) {
				group = new Group();
				group.setSender(new Person(mail));
				i++;
			}else{
				// On ajoute le reste des mails pour ce groupe comme expediteurs
				group.addReceiver(new Person(mail));
				i = (i + 1) % properties.getNbPersonGroup();

				// On a rempli le groupe
				if(i == 0){
					groups.add(group);
				}	
			}
		}

		return groups;
	}
}
