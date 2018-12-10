package Console;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Console.Banner.Sequencer;

public class BannerTest {
	
	Banner.Sequencer testsubject = null; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() 
	{
		
		String s = "This string should contain more than 30 characters. ";
		Banner.Sequencer bseq = new Sequencer(s);
		System.out.println(bseq.sequence);
		
		Banner b = new Banner(bseq);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
