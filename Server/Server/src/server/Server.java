/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package server;

 // AREAS OF THE CODE WERE TAKEN FROM JOHN'S ONLINE LECTURES
 
import java.lang.System;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    private int serverPort = -1;
    private DatagramSocket socket = null;

    public Server(int port) {
        serverPort = port;
    }

    public void runSystem() {
        System.out.println("-----------------------------------------");
        System.out.println("Server system is running...");
        System.out.println("Listening on port number: " + serverPort);
        System.out.println("-----------------------------------------");

        try {
            socket = new DatagramSocket(serverPort);
            socket.setSoTimeout(0);
            // Creates a Continuous loop to receive and process messages from the user class
            while (true) {
                byte[] packetData = new byte[1024];
                DatagramPacket packet = new DatagramPacket(packetData, packetData.length);
                socket.receive(packet);
                String message = new String(packetData);
                
                String[] jobRequests = message.split(";");
                
                //waits for incoming Registration requests from the NodeManager
                String[] elements = message.trim().split(",");
                switch (elements[0]) {
                    case "REG" -> {
                        System.out.println("---> got a REG instructions");
                        String regIp = elements[1];
                        String regPort = elements[2];
                        String regNode = elements[3];
                        System.out.println("------> RegIp = " + regIp + " " + " RegPort = " + regPort + " " + " RegNode = " + regNode);
                    }
                    //Waits for incoming JOB requests from the User
                    case "JOB" -> {
                        System.out.println("---> Got a JOB instructions");
                        int jobTime = Integer.parseInt(elements[1]);
                        int jobNumber = Integer.parseInt(elements[2]);
                        System.out.println("-> Job Time  " + elements[1]);
                        System.out.println("-> Job Number  " + elements[2]);
                        for (String jobRequest : jobRequests) {
                            System.out.println("---> Processing job request: " + jobRequest);
                            forwardJobRequestToNodeManager(message);
                        }
                        //Waits for job to be complete through the simulated time to then print the complete the job statement
                        try {
                            Thread.sleep(jobTime * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Job completed");
                        System.out.println("-----------------------------------------");                
                    }
                    case "FINISHED" -> System.out.println("---> got a FINISHED instructions");
                    case "STOP" -> System.out.println("---> got a STOP instructions");
                }
            }

        } catch (IOException | NumberFormatException error) {
            error.printStackTrace();
        } 
    }

    public void forwardJobRequestToNodeManager(String jobDetails) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            int nodeManagerPort = 3000;
            byte[] sendData = jobDetails.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, nodeManagerPort);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}  
                   
        
   