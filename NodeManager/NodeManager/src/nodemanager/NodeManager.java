/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nodemanager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class NodeManager {

    private DatagramSocket receiveSocket;
    private List<Node> nodes;
    private LoadBalancer loadBalancer;
    
    //initialises the creation of the nodes- creates them- stores an array for them to be stored inside
    public NodeManager(int receivePort, int nodePort) {
        try {
            receiveSocket = new DatagramSocket(receivePort);
            nodes = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Node node = new Node("Node " + (i + 1), nodePort);
                nodes.add(node);
                registerNodes(node, receivePort, nodePort + i);
            }
            loadBalancer = new LoadBalancer(nodes);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    // nodes are registered in the server and printed out into the terminal (Receives IP, nodeport and a nodeID)
    public void registerNodes(Node node, int receivePort, int nodePort) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String registrationMessage = "REG," + address.getHostAddress() + "," + nodePort + "," + node.getNodeName();
            DatagramSocket registerSocket = new DatagramSocket();
            byte[] sendData = registrationMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, receivePort);
            registerSocket.send(sendPacket);
            registerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // once connected to the server, It will wait for a request with the parameters of "REG" if this is not achieved-
    //      it will take the data received as a Job and start the process of processing the job.
    public void receivedJobRequests() {
        try {
            byte[] receiveData = new byte[1024];
            System.out.println("Server connected.");
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                receiveSocket.receive(receivePacket);
                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (receivedData.startsWith("REG")) {
                    System.out.println("Received registration request: " + receivedData);
                    System.out.println("-----------------------------------------");
                    continue;
                }
                System.out.println("Received job request from User: " + receivedData);
                
                // Process job request using load balancer
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        processJobRequest(receivedData);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //if the job given has a request time of 0 it will return an error to the user terminal
        private void processJobRequest(String jobDetails) {
        int jobTime = getJobTime(jobDetails);
        if (jobTime < 0) {
            System.out.println("Invalid job time: " + jobTime);
            return;
        }

        Node nextNode = null;
        long startTime = 0; 

        try {
            nextNode = loadBalancer.getNextNode();
            System.out.println("Processing job request: " + jobDetails);
            // Marks the time started of the Job request
            startTime = System.currentTimeMillis();
            // Current node occupied by Job is set to Busy so it cannot be used
            nextNode.setIsProcessingJob(true);
            // Job is simulated for the expected jobTime(Set by users and linked to match it)
            try {
                Thread.sleep(jobTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (LoadBalancer.NoAvailableNodes e) {
            // no nodes are available for load balancing
            System.err.println("No nodes are available for load balancing.");
            return; // Exit if no nodes are available
        }

        // Marks the time finished of the Job request
        long endTime = System.currentTimeMillis();
        // Calculates the time spent for the Job start and finish in milliseconds
        long processingTime = endTime - startTime;
        // Prints the actual time in seconds making it readable 
        System.out.println("Actual time for Job: " + (processingTime / 1000) + " seconds");
        // Prints the Job completed statement including the node which has done it
        System.out.println("Job completed at " + nextNode.getNodeName());
        System.out.println("-----------------------------------------");
    }
    //takes the time of the job and ensures that the time is simulated to the time requested
    private int getJobTime(String jobDetails) {
        String[] parts = jobDetails.split(",");
        if (parts.length < 2) {
            return -1;
        }
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static void main(String[] args) {
        int receivePort = Integer.parseInt(args[0]);
        int startingNodePort = Integer.parseInt(args[1]);
        NodeManager nodeManager = new NodeManager(receivePort, startingNodePort);
        nodeManager.receivedJobRequests();
    }
}