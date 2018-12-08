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
	
	public static void main(String[] args)
	{
		StringBuilder sb = new StringBuilder("-");
		Console con = new Console(sb.toString());
		String[] lines = {"line1", "line2", "line3"};
		for(int i =0; i<30;i++)
		{
			try{Thread.sleep(500);}catch(Exception e){};
			sb.append("-");
			con.setLine(sb.toString());
		}
		for(String s : lines)
		{
			con.setLine(s);
			try{Thread.sleep(1000);}catch(Exception e){}
		}
		
		con.finalize();
		
	}
	
	public static final String CR="\r"; // Carriage return. 
	public static final String BS ="\b"; // Back Space
	public static final int WIDTH = 30; // The width of the console window. 
	public static final int DELAY = 40;
	
	static PrintStream console = System.out; 
	
	private boolean flag = false; // true means stops refreshing. 
	
	Thread t = new Thread(this); // The thread that is refreshing items onto the console. 
	
	volatile String s = new String(); // will be accessed by multiple thread. 
	
	@Override
	public void run() 
	{
		while(!flag)
		{
			console.print(this.s);
			try {Thread.sleep(DELAY);} catch (InterruptedException e) {e.printStackTrace();}
			console.print(CR);
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
	 * Change the content of the line. 
	 * @param s 
	 */
	public synchronized void setLine(String s)
	{
		this.s = String.format("%1$-"+WIDTH+"s", s); 
	}
	
	/**
	 * End the thread
	 */
	public void finalize()
	{
		this.flag = true; 
		this.flag =true; 
	}
	

}
