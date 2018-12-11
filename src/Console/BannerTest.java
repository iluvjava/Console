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
		test1("This string should contain more than 30 characters. <=exactly 30 character...");
		
	}
	
	public void test1(String s)
	{
		 
		Banner.Sequencer bseq = new Sequencer(s);
		System.out.println("This is the sequence: ");
		for(int i = 0 ; i<= 40;i++)
		{
			System.out.println(bseq.nextFrame());
		}
		
		Banner b = new Banner(bseq);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
