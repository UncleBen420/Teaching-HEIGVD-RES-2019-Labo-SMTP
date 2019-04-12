package person;

/**
 * Represente une personne qui possede un e-mail
 */
public class Person {
	String address;

	/**
	 * Constructeur
	 * @param address l'adresse mail de la personne
	 */
	public Person(String address) {
		this.address = address;
	}

	/**
	 * Recupere l'adresse mail de la personne
	 * @return l'adresse mail de la personne
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Defini l'adresse mail de la personne
	 * @param address l'adresse mail de la personne
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [address=" + address + "]";
	}
}