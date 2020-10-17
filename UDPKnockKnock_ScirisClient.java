/*
 * Written by: Emmanuel Olatunde
 * Course: CSAS 3211WA
 * Program: UDPKnockKnock_ScirisClient.java
 * Description: A UDP client is created to connect to a UDP server based on its IP address and port#.
 * 				This client is meant to connect to the KnockKnock UDP server program which
 * 				will return a knock-knock joke once this client sends a command and option payload to it.
 * 				i.e commands: jokes,setup,punch 
 * 				i.e options: Any number, preferably number within confines of number of jokes available in server
 * 				Sample Sciris script to get # of jokes: java UDPKnockKnock_ScirisClient joke -1
 * 				Sample Sciris script to get joke setup: java UDPKnockKnock_ScirisClient setup 5
 * 				Sample Sciris script to get joke punchline: java UDPKnockKnock_ScirisClient punch 5
 * */
package KnockKnock_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPKnockKnock_ScirisClient {
	private static void sendString(DatagramSocket s, String IP, int port, String command, int option) throws Exception{
		// To get the host address as an InetAddress, using static method getByName
		InetAddress hostaddress = InetAddress.getByName(IP);
			
		//To parse payload to compatible format
		String protocol = "KKP/1.0.0|";
        String optionint = "|"+Integer.toString(option); 
        String payload = protocol+command+optionint;
		
		//To convert String payload to byte array
		byte[] b = new byte[128];
		b = payload.getBytes();
		
        // To create a datagram packet for sending (arbitrary) data to address on port
		DatagramPacket packet = new DatagramPacket(b, b.length, hostaddress, port);
		
        // Sending datagram	
		System.out.println("Client -> Server: "+payload);
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
		
        //To print out the response
		System.out.println("Server -> Client: "+data);
    	}
	
	//Method for UDP Client
	public static void UDPClient(String host, int port, String command, int option) {
		try {			
	        // Creates new DatagramSocket that listens on an arbitrary port. Note:
	        // Using 0 as port binds the DatagramSocket to first available port 
	        DatagramSocket s = new DatagramSocket(0);
	        
	       //To send UDP request to client
	        sendString(s, host, port, command, option);
	        
	        //Time out for 5 seconds
	        s.setSoTimeout(5000);
	        
	        //To wait for data from client
	        receiveString(s);
	        
	        //Closes Datagram Socket
	        s.close();
	        
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	public static void main(String[] args) {
		//Port
	    int port = 4949;
	    //Host
	    String IP = "104.254.245.22";
	    //Default Command
	    String command = "jokes";
	    //Default Option
	    int option = 0;
	    if (args.length >= 1){
	        try{
	          command = args[0];
	          option = Integer.parseInt(args[1]);
	          }
	        catch(NumberFormatException nfe){
	          System.err.println("Syntax Format: java 'class' 'command' 'option'");
	          System.err.println("Command must be a String (i.e jokes, setup, punch)");
	          System.err.println("Option must be an Integer");
	          System.err.println("Using default command '" + command+"'");
	          System.err.println("Using default option " + option);
	          }
	    	}

	 	//To open a client to connect to a server with
		// a given IP, port#, command & option
	    UDPClient(IP,port, command, option);
	}
}
