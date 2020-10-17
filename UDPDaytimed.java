/*
 * Written by: Emmanuel Olatunde
 * Course: CSAS 3211WA
 * Program: UDPDaytimed.java
 * Description: A UDP server is created on the given port and the current date
 * 				and time is sent to any UDP client that connects to server
 * */
package Daytimed_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class UDPDaytimed {
	
	//Method to wait for request from Client
	private static DatagramPacket waitForTeaser(DatagramSocket s) throws Exception {
		//A datagram packet for receiving arbitrary data
		byte[] buffer = new byte[256];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		// To wait for incoming datagram packet
		s.receive(packet);
		// return the incoming datagram packet
		return packet;
	}
	
	//Method to Generate current Date & Time and encode data in a datagram packet
	//to send to Client
	private static void sendDate(DatagramSocket s, InetAddress client, int port) throws IOException {
		// To create data, an array of bytes encoding today's date & time
		Date today = new Date();
		byte[] data = today.toString().getBytes();
        // To create a datagram packet for sending encoded data to client on port
		DatagramPacket packet = new DatagramPacket(data, data.length, client, port);
        // To send datagram to client on port	
		s.send(packet);
	}

	//Method for UDP Server
	public static void UDPServer(int port) {
		try {
			System.out.println("Creating a DatagramSocket to listen at port "+port);		
	        // To create a new DatagramSocket that listens to the specified port. 
	        DatagramSocket s = new DatagramSocket(port);
	        //Boolean for infinite loop
	        boolean running = true;
	        System.out.println("Listening at port "+port);
	        while (running){
		        DatagramPacket teaser = waitForTeaser(s);
		        //To extract client InetAddress and port that client listens to
		        sendDate(s, teaser.getAddress(), teaser.getPort());
	        }
	        System.out.println("Received Request from Client");
	        System.out.println("Sending data to Client");
	        s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) throws Exception {
		/*
		 * IDE Terminal Run
		//Port to listen on
		int port = 13;
		//To Start UDP Server
		UDPServer(port);
		*/
		
		//Default Port
	    int port = 1300;
	    if (args.length >= 1){
	        try{
	          port = Integer.parseInt(args[0]);
	          }
	        catch(NumberFormatException nfe){
	          System.err.println("Invalid port must be an integer");
	          System.err.println("Using default port " + port);
	          }
	    	}
		
			//To open a UDP server in given port
			UDPServer(port);
	}
	

}
