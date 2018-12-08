package Console;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BetterConsole implements Runnable
{
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
			slst.add(String.format("%1$"+Console.WIDTH+"s", s).replace('\n', ' '));
		}
	}
	
	public void run() 
	{
		while(!flag)
		{
			
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
				Console.console.println(s);
			}
		}
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
	
	
	
	
}
