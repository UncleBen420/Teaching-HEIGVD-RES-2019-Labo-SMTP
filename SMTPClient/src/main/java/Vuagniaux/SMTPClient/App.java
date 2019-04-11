package Vuagniaux.SMTPClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
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

			readResponse(in);

			sendMessage(out,"EHLO Prank");
			readResponse(in);

			// On envoye une requete
			
			/*sendMessage(out,"AUTH LOGIN");
			readResponse(in);
			
			sendMessage(out,"NWVjZTQ2MTE2NDlhODU=");
			readResponse(in);
			
			sendMessage(out,"OTg4YjNhMmQ0ZWYwNmI=");
			readResponse(in);*/


			sendMessage(out,"MAIL FROM: " + mail.getFrom());
			readResponse(in);

			sendMessage(out,"RCPT TO: " + mail.getTo());
			readResponse(in);

			sendMessage(out,"DATA");
			readResponse(in);
			
			sendMessage(out,"From:" + mail.getFromInMail() + "\nTo:" + mail.getToInMail() + "\nCc:" + mail.getCc() + "\nSubject:"
					+ mail.getToInMail() + "\n\n" + mail.getMessage() + "\r\n.\r\n");
			readResponse(in);
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

	public static void main(String[] args) {

		Mail mail = new Mail();
		mail.setFrom("test@test.ch");
		mail.setTo("haha@haha.ch");
		mail.setFromInMail("test@test.ch");
		mail.setToInMail("haha@haha.ch");
		mail.setSubject("test");
		mail.setMessage("Prank bro");

		App app = new App();

		app.sendmail("localhost", 25, mail);
	}

	public void readResponse(BufferedReader in) {
		String c;
		try {
			while ((c = in.readLine()) != null && checkCode(c) < 0) {

				System.out.print(c);
			}

			System.out.println(c);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int checkCode(String line) {

		String[] codeContinue = { "220", "250", "421", "500", "501", "334", "354", "235" };

		for (String s : codeContinue) {

			if (line.startsWith(s + " ")) {
				return Integer.parseInt(s);
			}

		}

		return -1;

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
