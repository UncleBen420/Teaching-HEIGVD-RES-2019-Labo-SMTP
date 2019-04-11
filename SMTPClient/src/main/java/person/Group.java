package person;

import java.util.ArrayList;
import java.util.List;

public class Group {
		
	Person sender;
	List<Person> victims = new ArrayList<Person>();
	public Person getSender() {
		return sender;
	}
	public void setSender(Person sender) {
		this.sender = sender;
	}
	public List<Person> getVictim() {
		return victims;
	}
	public void addVictims(Person victim) {
		victims.add(victim);
	}
	@Override
	public String toString() {
		return "Group [sender=" + sender + ", victims=" + victims + "]";
	}
	
	
	

}
