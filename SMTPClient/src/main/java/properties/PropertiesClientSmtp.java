package properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesClientSmtp {
	
	String cc, address, login, password, separator, subjectBalise, textBalise;
	int port, nbPersonGroup;
	
	public PropertiesClientSmtp(String path) {
		Properties properties = setProperties(path);
		
		cc 		 	 = properties.getProperty("cc");
		address  	 = properties.getProperty("address");
		port 	 	 = Integer.parseInt(properties.getProperty("port"));
		nbPersonGroup= Integer.parseInt(properties.getProperty("nbPersonGroup"));
		login 	 	 = properties.getProperty("login");
		password 	 = properties.getProperty("password");
		separator	 = properties.getProperty("separator");
		subjectBalise= properties.getProperty("subjectBalise");
		textBalise	 = properties.getProperty("textBalise");
		
		
	}
	
	public Properties setProperties(String path) {
		Properties properties = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(path);
			properties.load(fr);
			
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		return properties;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSubjectBalise() {
		return subjectBalise;
	}

	public void setSubjectBalise(String subjectBalise) {
		this.subjectBalise = subjectBalise;
	}

	public String getTextBalise() {
		return textBalise;
	}

	public void setTextBalise(String textBalise) {
		this.textBalise = textBalise;
	}

	public int getNbPersonGroup() {
		return nbPersonGroup;
	}

	public void setNbPersonGroup(int nbPersonGroup) {
		this.nbPersonGroup = nbPersonGroup;
	}
}
