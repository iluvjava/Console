package Console;

import java.util.Arrays;
import java.util.Iterator;
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
		
		pause(3000);
		b.finalize();
		
		b = new Banner(new LetterCascade("This is a string, which is a true statement."));
		pause(5000);
		
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
			Console.console.print(Console.CR);
			pause(50);
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
		 * A frame of the animated string for banner to display. Null to end the sequence. 
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
		//The constructor ensures this will happen. 
		 private void construct()
		 {
			 char[] charlist = source.toCharArray(); 
			 List<String> res = new LinkedList<>();
			 if(this.source.length()>=Console.WIDTH)
			 {
				 for(int i = 0; i< charlist.length; i++)
				 {
					 StringBuilder sb = new StringBuilder();
					 for(int j = 0 ; j<Console.WIDTH;j++)
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
	
	
	
	/**
	 * A different kind of animated horizontal string effect. <br>
	 * Strings enter from the left, one letter by one letter, and exists from the right,
	 * one letter by one letter. 
	 * @author Administrator
	 *
	 */
	static class LetterCascade implements BannerSequencer
	{
		
		protected List<String> strlist = new LinkedList<>(); // the strlist can contain partition of empty string! 
		
		protected int partitionindex = 0; // Index of the current partition that we are looking at. 
		
		//protected int letterindex = 0; // Index of the current letter in the string that we are trying to cascade. 
		protected int parkedindex= 0; //The right most index of the letter that parked on the array. 
		// these two parameters are the same thing. 
		
		
		protected  char[] cascading;		// the sequence of characters we will push when next frame is invoked. 
		protected char[] current_str; // the string we are looking at. 
		
		protected int step = 0 ; // -_-
		
		/**---Method Needs Testing--- <br>
		 * str, a sting, it can be empty but there will also no animation. 
		 * This method partition the string for according to the width of the console. 
		 * @param str
		 */
		public LetterCascade(String str)
		{
			// split the str into string with equal length to the width of the console. 
			int len = str.length();
			
			for(int i =0; i<len;i+=Console.WIDTH)
			{
				if(i+Console.WIDTH< str.length())
				{
					strlist.add(str.substring(i, i+Console.WIDTH));
					continue;
				}
				strlist.add(str.substring(i));
			}
			
			// pad the last string to the width of the console and remove all '\n'
			{
				// padding
				int size = strlist.size();
				strlist.set(size-1, String.format("%1$-"+Console.WIDTH+"s",strlist.get(size-1)));
				//removing
				List<String> newstrlist = new LinkedList<>();
				while(!strlist.isEmpty())
				{
					newstrlist.add(strlist.remove(0));
				}
				
				//Reestablish field. 
				strlist = newstrlist;
			}
			
			
			//*********************************
			//setup the other vars in the field: 
			this.current_str = this.strlist.get(this.partitionindex).toCharArray();
			this.cascading = new char[Console.WIDTH];
			Arrays.fill(cascading, ' ');
		}
		
		
		
		/**--Tested, it should work well.---
		 *<br> <br>
		 * Run and prepared the next frame. 
		 */
		protected void refresh()
		{
			//Find the index of the letter that is moving on the banner. 
			int movingletter = getMovingLeter();
			//Move the letter by one if it hasn't crashed into the parked index. 
			//Setting up booleans conditions as variables. 
			boolean Is_Empty_String = movingletter==-1;
			boolean Letter_Can_Move = movingletter>parkedindex;
			boolean Not_Last_Letter = parkedindex<current_str.length-1; 
			
			if(Is_Empty_String)
			{
				//The string is empty, which is the case at the beginning. 
				// It's possible that the first letter is a space char. 
				if(current_str[0]!=' ')cascading[cascading.length-1] = current_str[parkedindex];
				else
				{
					parkedindex = getSpaceEnd()+1;
					cascading[cascading.length-1]=current_str[parkedindex];
				}
			}
			else if(Letter_Can_Move)
			{
				if(current_str[parkedindex]!=' ')
				{
					//Shift the letter one index to the left. if it's not a space character. 
					char pre = this.cascading[movingletter-1];
					this.cascading[movingletter-1] = cascading[movingletter];
					cascading[movingletter] =pre; 
				}
				else
				{
					//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					// Skip one or a block of space character. 
					int spaceend = getSpaceEnd();
					parkedindex = spaceend+1;
					cascading[cascading.length-1]=current_str[parkedindex];
				}
			}
			//The letter has been moved to the parked position. 
			//Skipped the letter if it's a space character. 
			//increment the parked index and move the next letter. 
			else
			{
				if(Not_Last_Letter)
				{
					parkedindex++;
					cascading[cascading.length-1]= current_str[parkedindex];
				}
				else
				{
					this.cascading = Arrays.copyOf(this.current_str, this.current_str.length);
					// if there is next partition, move one to the next partition (and return!), if not, just skip
					if(partitionindex+1!=strlist.size())
					{
						
						current_str = strlist.get(++partitionindex).toCharArray(); //<== Out of index error. 
						Arrays.fill(cascading, ' '); //<== Clear up the cascading string. 
						//-----resetting the parameters----
						parkedindex =0; 
					}
				}
			}
		}
		
		
		/**
		 * 
		 * @return the index of the last non empty char from the right. 
		 * -1 if there is no such a character. -> an empty string. 
		 */
		protected int getMovingLeter()
		{
			for(int i = cascading.length-1;i>=0;i--)
			{
				if(cascading[i]!=' ')return i;
			}
			return -1;
		}
		
		
		/**
		 * 
		 * @return
		 * true if we are at the last partition. 
		 */
		protected boolean lastPartition()
		{
			return partitionindex==strlist.size()-1;
		}
		
		/**
		 * 
		 * @return
		 * The end of the block of string we are looking at, the index is inclusive. 
		 */
		protected int getSpaceEnd()
		{
			int end = parkedindex;
			while(end<current_str.length-1&&current_str[++end]==' ');
			return end-1;
		}
		
		

		@Override
		public String nextFrame() {
			if(lastPartition()&&parkedindex==cascading.length-1)return null;
			refresh();
			return new String(cascading);
		}
		
		public String toString()
		{
			String s ="Current StringFrame:"+ new String(this.current_str); 
			s+='\n'+"Current Cascading: "+ new String(cascading);
			s+='\n'+"This is the whole list of string of partition:" +strlist.toString();
			return s; 
		}
		
	}
	
}
