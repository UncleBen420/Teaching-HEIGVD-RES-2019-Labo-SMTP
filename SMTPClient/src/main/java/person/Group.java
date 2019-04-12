package person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente un groupe de personnes (Person) a qui envoyer les mails
 */
public class Group {
	Person sender;
	List<Person> receivers = new ArrayList<Person>();

	/**
	 * Recupere l'envoyeur
	 * @return la personne qui envoie le mail au groupe
	 */
	public Person getSender() {
		return sender;
	}

	/**
	 * Defini l'envoyeur
	 * @param sender la personne qui envoie le mail au groupe
	 */
	public void setSender(Person sender) {
		this.sender = sender;
	}

	/**
	 * Recupere les destinataires
	 * @return
	 */
	public List<Person> getReceivers() {
		return receivers;
	}

	/**
	 * Ajoute un destinataire
	 * @param receiver
	 */
	public void addReceiver(Person receiver) {
		receivers.add(receiver);
	}

	@Override
	public String toString() {
		return "Group [sender=" + sender + ", receiver=" + receivers + "]";
	}
}
