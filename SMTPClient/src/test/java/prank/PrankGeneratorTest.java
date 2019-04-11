package prank;

import junit.framework.TestCase;
import mailparser.MailParser;
import properties.PropertiesClientSmtp;

public class PrankGeneratorTest extends TestCase {
	
	public void testApp()
    {

    	
    	
		PropertiesClientSmtp properties = new PropertiesClientSmtp("conf.properties");
		MailParser parser = new MailParser(properties);
		PrankGenerator prankGenerator = new PrankGenerator(properties);
		
		prankGenerator.setGroups(parser.parsePerson("victims.txt"));
		prankGenerator.setPranks(parser.parsePrank("prank.txt"));
		
		System.out.println(prankGenerator.generatePrank());
		
        assertTrue( true );
    }

}
