/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
// BARE BONES OF THE CODE WAS TAKEN FROM JOHN'S ONLINE LECTURES
package user;

import java.net.*;
import java.util.Scanner;

public class User {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: User <sendPort> <receivePort>");
            System.exit(1);
        }
        int sendPort = Integer.parseInt(args[0]);
        int receivePort = Integer.parseInt(args[1]);
        Scanner scanner = new Scanner(System.in);     //For the username and password content (W3Schools,2024) W3Schools, W. (2024) Java user input (scanner), Java User Input (Scanner class). Available at: https://www.w3schools.com/java/java_user_input.asp (Accessed: 08 April 2024).
        String username = "";
        String password = "";
        boolean Auth = false;
        
        //While loop enables the loop to continue until the correct Username & Password is inputted. If not it prints "Authentication failed. Try again..."
        //   and will keep requesting the users input until 
        while (!Auth) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            System.out.println("-----------------------------------------"); 
            //Calls the hardcoded username and password which is stored authenticating if the use has used the correct information
            Auth = authUser(username, password);
            if (!Auth) {
                System.out.println("Authentication failed. Try again...");
            }
        }
        try {
            InetAddress address = InetAddress.getByName("localhost");

            while (true) {
                // Prompt user to enter the message
                System.out.print("Enter message (format: JOB,time,number; Type 'exit' to quit): ");
                String input = scanner.nextLine();
                
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                if (!input.matches("JOB,\\d+,(\\d+|exit)")) {           //((TheCoder, 2012) TheCoder, T. (2012) Regex java discrepancy, Stack Overflow. Available at: https://stackoverflow.com/questions/9908970/regex-java-discrepancy (Accessed: 03 April 2024).      
                    System.out.println("Error: Invalid message format. Please try again.");
                    System.out.println("-----------------------------------------");   
                    continue;
                }
                System.out.println("Node has been found for Job");
                System.out.println("Job request has been sent");
                System.out.println("-----------------------------------------");   
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                DatagramPacket packet = new DatagramPacket(input.getBytes(), input.getBytes().length, address, sendPort);
                DatagramSocket socket = new DatagramSocket(receivePort);
                
                socket.send(packet);
                socket.close();
            }
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    // CREDENTIALS WHICH YOU NEED TO LOG IN TO THE USER ACCOUNT- Called for when users attempt to login 
    private static boolean authUser(String usernamelogon, String passwordlogon) {
        return usernamelogon.equals("user") && passwordlogon.equals("password");
    }
}