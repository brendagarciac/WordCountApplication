//Design Programming Project 3 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: December 12th, 2021

import java.io.*;
import java.net.*;
import java.util.Scanner;

//Client class. This class communicates with the server
public class Client {

	public static void main(String[] args) {

		// establish a connection by providing host and port number
		try (Socket socket = new Socket("localhost", 1989)) {

			// writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
  
            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  
            // object of scanner class
            Scanner sc = new Scanner(System.in);
			
			String clientMessage = "";
			String serverMessage = "";
			String clientFileName = "";
			
			System.out.println("\nWelcome to Brenda's Word Counter Application! \n");

			while(!clientMessage.equals("10"))
			{
			    System.out.println("\nPick a number from the following menu: \n");
			    System.out.println("1. Upload a file");
			    System.out.println("2. Update an existing file");
			    System.out.println("3. Remove an existing file");
			    System.out.println("4. See all stored files");
			    System.out.println("5. Read an existing file");
			    System.out.println("6. Get # of lines in an existing file");
			    System.out.println("7. Get # of words in an existing file");
			    System.out.println("8. Get # of characters in an existing file");
			    System.out.println("9. Get total characteristics of an existing file (lines, words, characters)");
			    System.out.println("10. Exit");
			    
			    clientMessage = sc.nextLine();//read user input
			    out.println(clientMessage); //sends input to the server
                out.flush();
                                
                if(clientMessage.equals("1") || clientMessage.equals("2")|| clientMessage.equals("3")) {
    			    System.out.print("Please enter filename: ");
    			    
    			    clientFileName = sc.nextLine();
    			    out.println(clientFileName);
                    out.flush();
                    
                    // displaying server reply
                    System.out.println(in.readLine());
                    
                } 
                else if(clientMessage.equals("5")|| clientMessage.equals("6") || 
                		clientMessage.equals("7") || clientMessage.equals("8") || 
                		clientMessage.equals("9")) {
                	System.out.print("Please enter filename: ");
    			    
    			    clientFileName = sc.nextLine();
    			    out.println(clientFileName);
                    out.flush();
                    
                    // displaying server reply
                    while(!(serverMessage = in.readLine()).equals("done")) 
                		System.out.println(serverMessage);
                }
                else if(clientMessage.equals("4")) {
                	// displaying server reply with all files 
                	while(!(serverMessage = in.readLine()).equals("done")) 
                		System.out.println(serverMessage);
                }  
                else if(clientMessage.equals("10")) {
    			    System.out.println("Bye! Exiting the program... \n");

                }
                else {
    			    System.out.println("Invalid input. Try again \n");
                }    
		    }
			// closing the scanner object
			sc.close();
		}
		catch (IOException e) {
            e.printStackTrace();
        }

	}

}