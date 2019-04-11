package Vuagniaux.SMTPClient;

import Vuagniaux.prankManager.PrankBuilder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
	final static Logger LOG = Logger.getLogger(App.class.getName());

	Socket clientSocket;

	final static int BUFFER_SIZE = 1024;

	public void sendmail(String serverIP, int serverPort, Mail mail) {
		Socket clientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			clientSocket = new Socket(InetAddress.getByName(serverIP), serverPort);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			if(readResponse(in) != 220) {
				throw new IOException("cannot connect");
			}

			sendMessage(out,"EHLO Prank");
			
			dialog(readResponse(in), out, in, mail);
			
			
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

	public static void main(String[] args) {
		/*
		 * Pas encore testé la classe PrankBuilder.
		 * Il faut en tout cas compléter le fichier de config et
		 * vérifier la methode forgeMail car je sais pas quel
		 * mail mettre dans toSMTP et toInMail.
		 *
		 * Mais a part les quelques bugs qu'il va y avoir, tout est la.
		 */
		PrankBuilder prank = new PrankBuilder("config.properties", "victimsMails", "prankMessages");
		List<Mail> forgedMails = prank.getForgedMails();

		// Y'a pas besoin de creer une Ap ici vu qu'on est dans app, mais j'imagine
		// que c'était si on foutait le main ailleurs ^^
		App app = new App();

		for(Mail mail : forgedMails) {
			app.sendmail("localhost", 25, mail);
		}
	}

	public int readResponse(BufferedReader in) {
		String c;
		int temp = -1;
		try {
			while ((c = in.readLine()) != null && (temp = checkCode(c)) < 0) {

				System.out.print(c);
			}

			System.out.println(c);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;

	}

	public int checkCode(String line) {

		String[] codeContinue = {"221", "220", "250", "421", "500", "501", "502", "334", "354", "235", "530" };

		for (String s : codeContinue) {

			if (line.startsWith(s + " ")) {
				return Integer.parseInt(s);
			}

		}
		
		

		return -1;

	}
	
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
				
	public void auth(PrintWriter out, BufferedReader in) throws IOException {
		
		sendMessage(out,"AUTH LOGIN");
		if(readResponse(in) == 334) {
			sendMessage(out,"NWVjZTQ2MTE2NDlhODU=");
			if(readResponse(in) == 334) {
				sendMessage(out,"OTg4YjNhMmQ0ZWYwNmI=");
				if(readResponse(in) == 235) {
					System.out.println("Auth is successful");
				}else {
					throw new IOException("Autentification fail");
				}
				
			}else {
				throw new IOException("Autentification fail");
			}
			
		}else {
			throw new IOException("Autentification fail");
		}
		
	}
		
	
	

	public void sendMessage(PrintWriter out, String message) {

		System.out.println(message);
		out.print(message);
		out.print("\r\n");
		out.flush();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
