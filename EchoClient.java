import java.io.*;
import java.net.*;
public class EchoClient{
	public static void main(String[] args) throws IOException{
	Socket link = null;
	//Variable to send data to server vua the socket's output stream
	PrintWriter output = null;
	//Receive data sent back from the server via socket's output stream
	BufferedReader input = null;

	try{
		link= new Socket("127.0.0.1", 50000);
		output = new PrintWriter(link.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(link.getInputStream()));
	}
	catch(UnknownHostException e)
	{
		System.out.println("Unknown Host");
		System.exit(1);
	}
	catch (IOException ie) {
		System.out.println("Cannot connect to host");
		System.exit(1);
	}

	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		String userInput, serverResponse;

		while ((userInput = stdIn.readLine()) != null) {
			output.println(userInput);

			if ((serverResponse = input.readLine()) != null) {
				System.out.println("Echo from Server: " + serverResponse);
			} else {
				break;  // Exit the loop if the server closes the connection
			}

			if ("Bye".equalsIgnoreCase(userInput)) {
				break;  // Exit the loop if user enters "Bye"
			}
		}

		output.close();
		input.close();
		stdIn.close();
		link.close();
	}
}
