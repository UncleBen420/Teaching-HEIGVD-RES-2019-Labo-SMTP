package mailparser;

import junit.framework.TestCase;
import properties.PropertiesClientSmtp;

public class MailParserTest extends TestCase {
	
	public void testApp()
    {

    	
    	
		PropertiesClientSmtp properties = new PropertiesClientSmtp("conf.properties");
		MailParser parser = new MailParser(properties);
		
		System.out.println(parser.parsePrank("prank.txt"));
		System.out.println(parser.parsePerson("victims.txt"));
    	
        assertTrue( true );
    }
	

}
