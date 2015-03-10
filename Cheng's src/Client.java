import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	
	private Socket clientSocket;
	private PrintStream outputStream;
	private BufferedReader inputStream;
	private boolean inGame;
	private String username;
	private InetAddress ipAdress;
	  
	
	
	public void Client()
	{
		
	}
	
	public void main()
	{
		
	}
	
	public void run()
	{
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
	        String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" + "running the date service on port 9090:");
	        Socket s = new Socket(serverAddress, 9090);
	        BufferedReader inputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
	        String answer = inputStream.readLine();
	        JOptionPane.showMessageDialog(null, answer);
	        System.exit(0);
	    }
	}

