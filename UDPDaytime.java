/*
 * Written by: Emmanuel Olatunde
 * Course: CSAS 3211WA
 * Program: UDPDaytime.java
 * Description: A UDP client is created to connect to a given UDP server based on its hostname and port#.
 * 				This client is meant to connect to the UDPDaytimed program which
 * 				will return the current  time and date once this client connects to it
 * */
package Daytimed_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPDaytime {

	//Method to send UDP request to Client at given port
	private static void sendRequest(DatagramSocket s, String host, int port) throws Exception{
		// To get the host address as an InetAddress, using static method getByName
		InetAddress hostaddress = InetAddress.getByName(host);
        // To create a datagram packet for sending (arbitrary) data to address on port
		byte[] data = new byte[1];
		DatagramPacket packet = new DatagramPacket(data, data.length, hostaddress, port);
        // Sending datagram	
		s.send(packet);
		}	
	
	//Method to wait for data from Server
	private static void waitForDate(DatagramSocket s) throws Exception {
		//buffer to contain data
		byte[] buffer = new byte[256];
		// To create a datagram packet for receiving data of length data.length
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		// To wait for incoming datagram packet (which will contain the date/time)
		s.receive(packet);
		// Decode the data received in the datagram and print it out	
		String today = new String(packet.getData());
		System.out.println(today);
    	}
	
	//Method for UDP Client
	public static void UDPClient(String host, int port) {
		try {			
	        // Creates new DatagramSocket that listens on an arbitrary port. Note:
	        // Using 0 as port binds the DatagramSocket to first available port 
	        DatagramSocket s = new DatagramSocket(0);
	        //To send UDP request to client
	        sendRequest(s, host, port);
	        //Time out for 5 seconds
	        s.setSoTimeout(5000);
	        //To wait for data from client
	        waitForDate(s);
	        //Closes Datagram Socket
	        s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		/*
		 * IDE Terminal Run
		//Port to Connect to
		int port = 13;
		//Host to Connect to
		String host = "localhost";
		
		//To Start UDP Client
		UDPClient(host,port);
		*/
		
		//Default Port
	    int port = 1300;
	    //Default Host
	    String host = "localhost";
	    if (args.length >= 1){
	        try{
	          port = Integer.parseInt(args[1]);
	          host = args[0];
	          }
	        catch(NumberFormatException nfe){
	          System.err.println("Syntax Format: java 'class' port# hostname");
	          System.err.println("Invalid port must be an integer");
	          System.err.println("Invalid hostname must be String");
	          System.err.println("Using default hostname " + host + " and port " + port);
	          }
	    	}

	 	//To open a client to connect to a server with
		// a given hostname & port #
	    UDPClient(host,port);

	}

}
