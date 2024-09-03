/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nodemanager;

import java.util.List;
public class LoadBalancer {
    //will hold a list of the nodes and wait for the LoadBalancer to distribute requests to
    private List<Node> nodes;
    //Will keep track of the index of the last node which was used for a Job request (Due to it being the first it will be index 0)
    private int NodeIndexes = -1;

    public LoadBalancer(List<Node> nodes) {
        this.nodes = nodes;
    } //Creates an exception if there are no nodes which can be used for the Job request
        public static class NoAvailableNodes extends Exception {
        public NoAvailableNodes() {
             super("There are no available nodes for your Job request");
        }
    }
    //Set for assigning the next available node to the Job request taking note of each Node used(Later updates the index)-
    //      for jobs allowing for an increment of +1 for the incoming Job requests
    public synchronized Node getNextNode() throws NoAvailableNodes {
        if (nodes.isEmpty()) {
            throw new NoAvailableNodes();
        }
        NodeIndexes = (NodeIndexes + 1) % nodes.size();
        return nodes.get(NodeIndexes);
    }
}