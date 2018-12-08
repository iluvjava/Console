package Console;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConsoleTest {
	
	BetterConsole subject = null; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@Before
	public void setUp() throws Exception 
	{
		this.subject = new BetterConsole("-------","ttt");
		Thread.sleep(5000);
		this.subject.finalize();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConsole() {
		 
	}

}
