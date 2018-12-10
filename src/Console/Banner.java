package Console;

import java.util.LinkedList;
import java.util.List;

import Console.Banner.Sequencer;

/**
 * The banners moves text on a single line horizontally. It should have good API and stuff to make it easy to use. <br><br>
 * One instacnce of banner for one instance of sequence. 
 * @author Administrator
 *
 */
public class Banner implements Runnable{
	
	public static void main(String[] args)
	{
		String s = "This string should contain more than 30 characters. ";
		Banner.Sequencer bseq = new Sequencer(s);
		
		Banner b = new Banner(bseq);
		
		pause(10000);
	}

	private boolean flag = false; // true means stop refreshing. 
	private String[] content; 
	private Thread t = new Thread(this); 
	
	/**
	 * @param str strings of animation. 
	 */
	public Banner(String[] str)
	{
		content = str;
		t.start();
	}
	
	public Banner (Sequencer sq)
	{
		this(sq.sequence.toArray(new String[sq.sequence.size()]));
	}
	
	
	public void run() 
	{
		while(!flag)
		{
			for(String s : this.content)
			{
				Console.console.print(s);
				pause(100);
				Console.console.print(Console.CR);
			}
		}
	}
	
	public void finalize()
	{
		this.flag = true; 
	}

	private static void pause(final int n)
	{
		try
		{
			Thread.sleep(n);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Provides an animated sequence for a certain input string.<br><br>
	 * It has the same name as sequencer in java. 
	 * @author Administrator
	 *
	 */
	static class Sequencer
	 {
		 String source;
		 List<String> sequence;
		 /**
		  * Takes a string and turn it into a sequence of strings for the banner class. 
		  * @param s
		  */
		 public Sequencer(String s)
		 {
			 this.source = String.format("%1$-"+Console.WIDTH+"s", s); 
			 construct();
		 }
		 
		 private void construct()
		 {
			 char[] charlist = source.toCharArray(); 
			 List<String> res = new LinkedList<>();
			 
			 //The constructor ensures this will happen. 
			 if(this.source.length()>=Console.WIDTH)
			 {
				 for(int i = 0; i< charlist.length; i++)
				 {
					 StringBuilder sb = new StringBuilder();
					 for(int j = 0 ; j<charlist.length;j++)
					 {
						 sb.append(charlist[(i+j)%charlist.length]);
					 }
					 res.add(sb.toString());
				 }
			 }
			 sequence = res;
		 }
		 
	 }
	
}
