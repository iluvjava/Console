package Console;

import java.util.LinkedList;
import java.util.List;

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
	private BannerSequencer sequence; 
	private Thread t = new Thread(this); 
	
	/**
	 * @param str strings of animation. 
	 */
	
	
	public Banner (BannerSequencer sq)
	{
		sequence = sq;
		t.start();
	}
	
	
	public void run() 
	{
		while(!flag)
		{
			Console.console.print(sequence.nextFrame());
			pause(200);
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
	
	
	static interface BannerSequencer
	{
		/**
		 * The return string has these two Properties: 
		 * <br>
		 * <ol>
		 * <li>The string doesn't contain escape character (They should be stripped away when 
		 * BannerSequencer instances are created)
		 * <li>It should in the length of the width of the console specified in the Console class. 
		 * </ol>
		 * @return
		 * A frame of the animated string for banner to display. 
		 */
		String nextFrame();
	}
	
	
	/**
	 * Provides an animated sequence for a certain input string.<br><br>
	 * It has the same name as sequencer in java. 
	 * <br>
	 * 
	 * <b> Test Needed</b>
	 * @author Administrator
	 *
	 */
	static class Sequencer implements BannerSequencer
	 {
		
		private int frameindex =0; 
		 String source;
		 List<String> sequence;
		 /**
		  * Takes a string and turn it into a sequence of strings for the banner class. <br>
		  * The string will be padded if shorter than the width of the console,
		  * new line characters are replaced by space. 
		  * @param s
		  */
		 public Sequencer(String s)
		 {
			 // Padding 
			 if(s.length()<Console.WIDTH)
				 this.source = String.format("%1$-"+Console.WIDTH+"s", s); 
			 //No padding if wider than the width. 
			 else 
				this.source = s;
			 
			 // Stripp away new line character
			 source.replace('\n', ' ');
			 construct();
		 }
		 
		 //Construct and store the sequencer of animation. 
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
		 
		 
		 public String nextFrame()
		 {
			 String res = sequence.get(frameindex);
			 frameindex = (frameindex+1)%sequence.size();
			 return res; 
		 }
		 
		 
	 }
	
	
	
	static class	MultiSequencer
	{
		
		
	}
	
}
