package Vuagniaux.SMTPClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mailparser.MailParser;
import prank.PrankGenerator;
import properties.PropertiesClientSmtp;

public class App {
	private final static Logger LOG = Logger.getLogger(App.class.getName());
	private static PropertiesClientSmtp properties;
	private Socket clientSocket;
	private final static int BUFFER_SIZE = 1024;
	
    /**
     * Constructeur
     */
	public App() {
		clientSocket = null;
		properties = new PropertiesClientSmtp("conf.properties");
	}
	
	
    /**
     * methode principale
     */
	public static void main(String[] args) {
		App app = new App();
		PrankGenerator prankGenerator = new PrankGenerator(properties);
		MailParser parser = new MailParser(properties);
		
		prankGenerator.setGroups(parser.parsePerson("victims.txt"));
		prankGenerator.setPranks(parser.parsePrank("prank.txt"));
		
		List<Mail> mails = prankGenerator.generatePrank();

		for(Mail mail : mails) {
			app.sendmail(mail);			
		}
	}

    /**
     * Cette methode permet d'envoyer un mail 
     * 
     *  @param mail le mail devant etre envoye
     */
	public void sendmail(Mail mail) {
		Socket clientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			clientSocket = new Socket(InetAddress.getByName(properties.getAddress()), properties.getPort());
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
			dialog(readResponse(in), out, in, mail);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}


	/**
     * lit la reponse du server 
     * 
     *  @param in le buffer de lecture de reponse du server
     *  @return le code de la dernière reponse du server
     */
	public int readResponse(BufferedReader in) {
		String c;
		int temp = -1;

		try {
			// on lit tant que le server n'a pas envoye un code de fin
			while ((c = in.readLine()) != null && (temp = checkCode(c)) < 0) {
				System.out.print(c);
			}

			System.out.println(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return temp;
	}

	
	/**
     * lit si la ligne correspond a un code de reponse smtp retourne -1 sinon
     * 
     *  @param line ligne a lire
     *  @return le code de reponse du server
     */
	public int checkCode(String line) {
		String[] codeContinue = {"221", "220", "250", "421", "500", "501", "502", "334", "354", "235", "530", "550" };

		for (String s : codeContinue) {
			if (line.startsWith(s + " ")) {
				return Integer.parseInt(s);
			}
		}

		return -1;
	}
	
	
	/**
     * Cette methode permet de faire le dialogue avec le serveur.
	 * Elle permet d'agir par exemple si celui ci a besoin d'une authentification
     */
	public void dialog(int code, PrintWriter out, BufferedReader in, Mail mail) throws IOException {
		int code2 = 0;
		switch(code) {
			case 220:
				System.out.println("Connection Ok");
				sendMessage(out,"EHLO Prank");

				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				break;
			case 530:
				System.out.println("Server want a autentification");
				auth(out, in);
			case 250:
				System.out.println("OK");

				sendMessage(out,"MAIL FROM: " + mail.getFrom());
				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				sendMessage(out,"RCPT TO: " + mail.getTo());
				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				sendMessage(out,"DATA");
				code2 = readResponse(in);

				break;
			case 501:
				sendMessage(out,"MAIL FROM: <" + mail.getFrom() + ">");
				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				sendMessage(out,"RCPT TO: <" + mail.getTo() + ">");
				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				sendMessage(out,"DATA");
				code2 = readResponse(in);

				break;
			case 354:
				sendMessage(out, "From:" + mail.getFromInMail() +
								 "\nTo:" + mail.getToInMail()   +
								 "\nCc:" + mail.getCc()         +
								 "\nSubject:" + mail.getSubject() +
								 "\n\n" + mail.getMessage() + "\r\n.\r\n");

				if((code2 = readResponse(in)) != 250) {
					dialog(code2, out, in, mail);
					return;
				}

				sendMessage(out,"quit");
				System.out.println("Message send successfully");

				return;
			case 221:
				return;
			default:
				throw new IOException("unespected error");
		}
		
		
		dialog(code2, out, in, mail);
	}
		
	
	/**
     * Permet de faire une authentification sur un serveur smtp
     */
	public void auth(PrintWriter out, BufferedReader in) throws IOException {
		sendMessage(out,"AUTH LOGIN");
		if(readResponse(in) == 334) {
			sendMessage(out,properties.getLogin());
			if(readResponse(in) == 334) {
				sendMessage(out,properties.getPassword());
				if(readResponse(in) == 235) {
					System.out.println("Auth is successful");
				} else {
					throw new IOException("Autentification fail");
				}
			} else {
				throw new IOException("Autentification fail");
			}
		} else {
			throw new IOException("Autentification fail");
		}
	}

	/**
	 * Envoie un mail sur le serveur SMTP.
     */
	public void sendMessage(PrintWriter out, String message) {
		System.out.println(message);
		out.print(message);
		out.print("\r\n");
		out.flush();

		/*
		 * Ecrit dans le buffer d'ecriture le message puis attends une seconde.
		 * Cela permet d'eviter une erreur de trop d'envoie de mail a la seconde.
		 */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public PropertiesClientSmtp getProperties() {
		return properties;
	}
}