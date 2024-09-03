/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nodemanager;

/**
 *
 * @author Tavian
 */

public class Node {
    private String nodeName;
    private boolean isProcessingJob;

    public Node(String nodeName, int port) {
        this.nodeName = nodeName;
        this.isProcessingJob = false;
    }

    public boolean getIsProcessingJob() {
        return isProcessingJob;
    }

    public void setIsProcessingJob(boolean isProcessingJob) {
        this.isProcessingJob = isProcessingJob;
    }

    public String getNodeName() {
        return nodeName;
    }
}