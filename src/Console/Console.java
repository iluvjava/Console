package Console;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a class that makes the terminal of the OS better to view. <br>
 * This class should not be singleton design; because we might have to restart console to stops the animations and stuff. 
 * @author 
 */
public class Console implements Runnable{
	
	public static final String CR="\r"; // Carriage return. 
	public static final String BS ="\b"; // Back Space
	public static final int WIDTH = 30; // The width of the console window. 
	static OutputStream console = System.out; 
	Thread t = new Thread(); // The thread that is refreshing items onto the console. 
	List<String> lines = new LinkedList<>();
	
	@Override
	public void run() 
	{
		
	}
	
	public Console(String...strings)
	{
		
	}
	

}
