package Console;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BetterConsole implements Runnable
{
	
	public static void main(String[] args) throws Throwable
	{
		BetterConsole bc = new BetterConsole("line 1","line 2");
		Thread.sleep(3000);
		bc.set(1, "Line 2 is changed");
		Thread.sleep(2000);
		bc.finalize();
	}
	
	private boolean flag = false; // True means stop refreshing. 
	
	protected List<String> slst = new LinkedList<>();
	
	private Thread t =new Thread(this);
	
	/**
	 * Enter a line of string, any \n character in the string will be replaced by ' '. 
	 * @param strings
	 */
	public BetterConsole(String...strings)
	{
		for(String s : strings)
		{
			slst.add(String.format("%1$-"+Console.WIDTH+"s", s).replace('\n', ' '));
		}
		this.t.start();
	}
	
	public void run() 
	{
		while(!flag)
		{
			printAll();
			try{Thread.sleep(Console.DELAY);}catch(Exception e){}
			clearAll();
		}
	}
	
	/**
	 * This method prints all lines store in this object. 
	 */
	private void printAll()
	{
		{
			String s = null;
			for(Iterator<String> itr =slst.iterator();itr.hasNext();)
			{
				s = itr.next();
				Console.console.print('\n'+s);
			}
		}
	}
	
	private void clearAll()
	{
		StringBuilder sb = new StringBuilder();
		for(; sb.length()<=Console.WIDTH;sb.append(Console.BS));
		
		
		// Moves the cursor back.... 
		for(int i = 0 ; i< this.slst.size(); i++)
		{
			Console.console.print(sb.toString());
		}
		
		// make an empty line.
		sb = new StringBuilder();
		for(; sb.length()<=Console.WIDTH;sb.append(' '));
		
		for(int i = 0 ; i< this.slst.size(); i++)
		{
			Console.console.println(sb.toString());
		}
//		try 
//		{
//			Runtime.getRuntime().exec("cls");
//		}
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
	}
	
	
	
	/**
	 * 
	 * @param index
	 * The index of line 
	 */
	public synchronized BetterConsole set(int index, String s)
	{
		this.slst.set(Math.abs(index%this.slst.size()), s);
		return this;
	}
	
	public void finalize()
	{
		this.flag =true;
	}
	
	
	
	
}
