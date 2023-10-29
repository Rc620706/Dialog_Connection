import java.net.*;
import java.io.*;
import java.util.Date;
public class EchoServer{
	public static void main(String[] args) throws IOException{
		//This is acting as the gate for getting incoming requests (sockets) from the client
		//It is set to null at initial stage until it gets triggered to listen on port.
		ServerSocket serverSock = null;
		try{
			// Now, the ServerSocket's object is initialized with a port to listen for incoming requests (The door is open now)
			serverSock = new ServerSocket(50000);
		}
		catch (IOException ie){
			System.out.println("Can't listen on 50000");
			System.exit(1);
		}
			//A copy of the socket is created to represent the client's socket connection request,
			//once this socket is arrived at the gate (ServerSocket serverSock).
			Socket link = null;
			//Print message to indicate the server is ready to accept the sockets from the client
			System.out.println("Listening for connection ...");

			//When the requests (Sockets) from the client is arrived
		try {
			//The socket object is initialized with an accept method which is used to accept the client's socket connection request
			link = serverSock.accept();
			//In multi-threading process, there can have multiple sockets (connection requests) sent from the client.
			//Each socket stands for a unique connection between the client and the server,
			//thus, there would accordingly have the same amount of copied sockets on server side to match those sockets from the client
			//In result, there would have many Socket objects be created on server side like:
			// 															Socket link1;
			//															Socket link2;
			//															Socket link3:
			//															...
		}
		catch (IOException ie){
			System.out.println("Accept failed");
			System.exit(1);
		}

		// Connection successfully message
		System.out.println("Connection successful");
		//Wait the actual input from the client
		System.out.println("Listening for input ...");

		//outputStream is set up for sending back response to the client via the corresponding Socket object which is link in this case
		PrintWriter output = new PrintWriter(link.getOutputStream(), true);

		//inputStream is set up for receiving the data from the client via the Socket object link as well.
		BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));

		//Save the received data in a String object
		String inputLine;

		//
		while ((inputLine = input.readLine()) != null) {
			switch (inputLine) {
				case "What time is it?":
					String timeResponse = new Date().toString(); //Save date value to this String variable
					output.println(timeResponse);
					System.out.println(timeResponse);
					break;

				case "Bye":
					String byeResponse = "Bye!";
					output.println(byeResponse);
					System.out.println("See you next time!");

					// Close the resources
					output.close();
					input.close();
					link.close();
					serverSock.close();

					// Exit the program
					System.exit(0);
					break;

				default:
					output.println(inputLine);  // If client entered other input, sending back to the client
					System.out.println("Server: " + inputLine);
					break;
			}
		}

		output.close();
		input.close();
		link.close();
		serverSock.close();
	}
}
