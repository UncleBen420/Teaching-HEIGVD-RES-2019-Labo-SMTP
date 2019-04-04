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
        OutputStream out = null;
        InputStream in = null;

        try {
            clientSocket = new Socket(InetAddress.getByName(serverIP), serverPort);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

            // On envoye une requete
            out.write(("MAIL FROM: " + mail.getFrom()).getBytes());
            readResponse(in);
            
            out.write(("RCPT TO: " + mail.getTo()).getBytes());
            readResponse(in);
            
            out.write(("DATA" + mail.getTo()).getBytes());
            readResponse(in);
            
            

        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    public void readResponse(InputStream in) {
        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int newBytes;

        try {
			while((newBytes = in.read(buffer)) != -1) {
			    responseBuffer.write(buffer, 0, newBytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        LOG.log(Level.INFO, "Response sent by the server: ");
        LOG.log(Level.INFO, responseBuffer.toString());
    }

}
