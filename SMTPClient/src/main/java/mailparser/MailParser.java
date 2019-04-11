package mailparser;

import java.util.ArrayList;
import java.util.List;
import Vuagniaux.reader.Reader;
import person.Group;
import person.Person;
import prank.Prank;
import properties.PropertiesClientSmtp;

public class MailParser {
	
	PropertiesClientSmtp properties;
	
	public MailParser(PropertiesClientSmtp properties) {
		this.properties = properties;
	}

	
	public List<Prank> parsePrank(String path) {
		Reader reader = new Reader(path);
		List<Prank> pranks = new ArrayList<Prank>();
		
		String list = reader.fileReading();
		
		for(String s : list.split(properties.getSeparator())) {
			
			Prank prank = new Prank();
			try {
				prank.setSubject(s.substring(s.indexOf(properties.getSubjectBalise()) + properties.getSubjectBalise().length() + 2, s.indexOf(properties.getTextBalise())-2));
				prank.setPrank(s.substring(s.indexOf(properties.getTextBalise()) + properties.getTextBalise().length() + 2));
				
				pranks.add(prank);
				
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Une prank n'a pas ete correctement parsee");			
			}
		}
		return pranks;
		
	}
	
	public List<Group> parsePerson(String path) {
		Reader reader = new Reader(path);
		List<Group> groups  = new ArrayList<Group>();
		
		List<String> list = reader.readLine();
		
		int i = 0;
		
		Group group = null;

		for(String s : list) {
			
			if(i == 0) {
				group = new Group();
				group.setSender(new Person(s));
				i++;
			}else{
				group.addVictims(new Person(s));
				i = (i + 1) % properties.getNbPersonGroup();
				if(i == 0){
					groups.add(group);
				}	
			}
			
		}
		return groups;
		
	}

}
