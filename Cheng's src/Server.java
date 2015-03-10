import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server {
	private JTextField userText;
	private JTextArea chatWindow;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private PrintStream outputStream;
	private BufferedReader inputStream;
	
	public Server()
	{
		userText = new JTextField();
		userText.setEditable(false);
		
	}
	
	public void run(){
	
	try{
		server = new ServerSocket(6789, 100);  //backlog: how many people will be waiting
		while(true){
			try{
				waitForConnection();
				setupStreams();
				whileChatting();
			}catch(EOFException eofException){
				showMessage("\n Server ended the connection!");
			}finally{
				closeCrap();
			}
		}
	}catch(IOException ioException){
		ioException.printStackTrace();
	}
}
	
	private void waitForConnection() throws IOException
	{
		showMessage("Waiting for someone to connect... \n");
		connection = server.accept();
		showMessage(" Now connected to " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Steams are not setup! \n");
	}
	
	private void whileChatting() throws IOException
	{
		String message = "You are now connected!";
		sendMessage(message);
		ableToType(true);
		do
		{
			try
			{
				message = (String) input.readObject();
				showMessage("\n"+ message);
			}catch(ClassNotFoundException classNotFoundException)
			{
				showMessage("\n idk the user send!");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	private void closeCrap()
	{
		showMessage("\n Closing connection...\n");
		ableToType(false);
		try
		{
			output.close();
			input.close();
			connection.close();
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	private void sendMessage(String message)
	{
		try
		{
			output.writeObject("SERVER - "  + message);
			output.flush();
			showMessage("\nSERVER - " + message);
		}
		catch (IOException ioException)
		{
			chatWindow.append("\n ERROR); DUDE I CANT SEND THAT MESSAGE");
		}
	}
	
	
	private void showMessage(final String text)
	{
		SwingUtilities.invokeLater(
		new Runnable()
		{
			public void run()
			{
				chatWindow.append(text);
			}
		}
	);
}
	
	private void ableToType(final boolean tof)
	{
		SwingUtilities.invokeLater(
				new Runnable()
				{
					public void run()
					{
						userText.setEditable(tof);
					}
				}
			);
	}
}

	
