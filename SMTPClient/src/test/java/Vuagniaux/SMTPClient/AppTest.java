package Vuagniaux.SMTPClient;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
    	App temp = new App();
    	Mail mail = new Mail();
    	
    	mail.setFrom("caca@outlook.com");
    	mail.setTo("remy.vuagniaux@heig-vd.ch");
    	mail.setFromInMail("cacaman");
    	mail.setToInMail("remy.vuagniaux@heig-vd.ch");
    	mail.setCc("gaetan.bacso@heig-vd.ch");
    	mail.setSubject("test");
    	mail.setMessage("coucou");
    	
    	temp.sendmail("localhost", 25000, mail);
    	
        assertTrue( true );
    }
}
