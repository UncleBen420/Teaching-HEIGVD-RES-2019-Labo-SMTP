package Vuagniaux.SMTPClient;

import junit.framework.TestCase;
import Vuagniaux.reader.Reader;

public class ReaderTest extends TestCase {

	public void testApp()
    {
		Reader test = new Reader("prank.txt");
		
		System.out.println(test.bufferedReading());
		
		assertTrue( true );
    	
    }

}
