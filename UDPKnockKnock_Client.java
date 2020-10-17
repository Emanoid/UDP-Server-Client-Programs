 /* Written by: Emmanuel Olatunde
 * Course: CSAS 3211WA
 * Program: UDPKnockKnock_ScirisClient.java
 * Description: A UDP client is created to connect to a UDP server based on its IP address and port#.
 * 				This client is meant to connect to the KnockKnock UDP server program which
 * 				will return a knock-knock joke once this client sends payload to it.
 * 				payload: protocol|command|option
 * 				i.e protocol: KKP/1.0.0
 * 				i.e commands: jokes,setup,punch 
 * 				i.e options: Any number, preferably number within confines of number of jokes available in server
 * */
package KnockKnock_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPKnockKnock_Client {
	private static void sendString(DatagramSocket s, String IP, int port, String command) throws Exception{
		// To get the host address as an InetAddress, using static method getByName
		InetAddress hostaddress = InetAddress.getByName(IP);
		
		//To convert String payload to byte array
		byte[] b = new byte[128];
		b = command.getBytes();
		
        // To create a datagram packet for sending (arbitrary) data to address on port
		DatagramPacket packet = new DatagramPacket(b, b.length, hostaddress, port);
		
        // Sending datagram	
		s.send(packet);
		}	
	
	//Method to wait for data from Server
	private static void receiveString(DatagramSocket s) throws Exception {
		//buffer to contain data
		byte[] buffer = new byte[128];
		
		// To create a datagram packet for receiving data of length data.length
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		// To wait for incoming datagram packet (which will contain the date/time)
		s.receive(packet);
		
		// Decode the data received in the datagram and print it out	
		String data = new String(packet.getData());
		
        //To print out the joke
		System.out.println("Server -> Client: "+data);
    	}
	
	//Method for UDP Client
	public static void UDPClient(String host, int port) {
		try {			
	        // Creates new DatagramSocket that listens on an arbitrary port. Note:
	        // Using 0 as port binds the DatagramSocket to first available port 
	        DatagramSocket s = new DatagramSocket(0);
	        
	        //Array of Commands
	        String[] commands = new String[]{"KKP/1.0.0|jokes|-1","KKP/1.0.0|setup|6","KKP/1.0.0|punch|6"};
	        
	        //For loop for requesting, joke->setup->punch
	        for(int i = 0; i < commands.length; i++) {
	        	//To send UDP request to client
		        System.out.println("Client -> Server: "+commands[i]);
		        sendString(s, host, port, commands[i]);
		        
		        //Time out for 5 seconds
		        s.setSoTimeout(5000);
		        
		        //To wait for data from client
		        receiveString(s);
	        }
	        //Closes Datagram Socket
	        s.close();
	        
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	public static void main(String[] args) {
		// * IDE Terminal Run
		//Port to Connect to
		int port = 4949;
		//Host to Connect to
		String IP = "104.254.245.22";
		
		//To Start UDP Client
		UDPClient(IP,port);
	}	

}
