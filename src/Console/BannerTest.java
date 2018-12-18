package Console;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Console.Banner.LetterCascade;
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
	public void test() throws Throwable 
	{
		//test1("This string should contain more than 30 characters. <=exactly 30 character...");
		//test2();
		test3();
	}
	
	public void test1(String s)
	{
		 
		Banner.Sequencer bseq = new Sequencer(s);
		System.out.println("This is the sequence: ");
		for(int i = 0 ; i<= 40;i++)
		{
			System.out.println(bseq.nextFrame());
		}
		
//		Banner b = new Banner(bseq);
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Testing the letter cascade class. 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void test2() throws Exception
	{
		Banner.LetterCascade bl = new Banner.LetterCascade("This is not a long string. ");
		Banner.LetterCascade bl2 = new Banner.LetterCascade("However this is is a string that possibly has more than 30 letters becaust "
				+ "we need to use it to test the vadility of the lettercascade class. ");
		Field[] f = bl.getClass().getDeclaredFields();
		Field[] f2 = bl2.getClass().getDeclaredFields();
		System.out.println("---This is the Field of the class---");
		for(Field ff : f)
		{
			ff.setAccessible(true);
			System.out.println(ff.get(bl));
		}for(Field ff : f2)
		{
			ff.setAccessible(true);
			System.out.println(ff.get(bl2));
		}
		
		char[] w = new char[4];
		System.out.println(Arrays.toString(w));
		
	}
	
	
	/**
	 * testing: <br>
	 * <ol>
	 * <li> Refresh method in Banner cascade class.
	 * </ol>
	 */
	public void test3()
	{
		Banner.BannerSequencer t = new Banner.LetterCascade("This is a string. A short one."); 
		LetterCascade tt = (LetterCascade)t;
		tt.refresh();
		println("This is a null character: "+'\0');
		println(tt);
		
		while(true)
		{
			String s = tt.nextFrame();
			if(s==null)break;
			println(s);
		}
	}
	
	
	public static void println(Object o)
	{
		System.out.println(o);
	}

}
