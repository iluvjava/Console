package Console;

import java.io.PrintStream;

/**
 * This is a class that makes the terminal of the OS better to view. <br>
 * This class should not be singleton design; because we might have to restart console to stops the animations and stuff. 
 * <br><br>
 * This class can only display one line of dynamic content onto the console. 
 * @author 
 */
public class Console implements Runnable{
	
	public static final String CR="\r"; // Carriage return. 
	public static final String BS ="\b"; // Back Space
	public static final int WIDTH = 30; // The width of the console window. 
	
	static PrintStream console = System.out; 
	
	private boolean flag = false; // true means stops refreshing. 
	
	Thread t = new Thread(this); // The thread that is refreshing items onto the console. 
	String s = new String();
	
	@Override
	public void run() 
	{
		while(!flag)
		{

		}
	}
	
	/**
	 *  one Line of string. 
	 * @param line
	 */
	public Console(String line)
	{
		this.s = String.format("%1$-"+WIDTH+"s", line);  // Setup the string with paddings. 
		this.t.start();
	}
	
	/**
	 * End the thread
	 */
	public void finalize()
	{
		this.flag = true; 
		this.t.interrupt();
	}
	

}
