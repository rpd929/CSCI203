/*  Assignment 3
    Rory Davis
    5917670
    */
import java.io.*;
import java.lang.Object;
import java.math.*;

public class a3 {
    /*
    public static class Selected {
        private static int index = 0; 
        private static String words[];

    
        public static void push(String word) {
        words[index] = word;
        index = index + 1;
    }

        public static String top() {

            return words[index];
            
        }

        public static void pop() {
            index = index - 1;      
        }

        public static Boolean isEmpty() {   
            return(index == 0);
        }

        public static Boolean empt(int index) {

            if (index > 0) {

            return  true;
            } else return false;
        }
    }

public static class Queue {
        
    private static int index = 0;
    private static int heap[];

   public static void push(int word) {
        heap[index] = word;
        
    }

    public static void siftUp(int newEntryIndex) {

        int parent;

        if(newEntryIndex == 0) {
            return;
        } else {
            parent = (newEntryIndex -1) / 2;
            //System.out.println("Parent of index: " + newEntryIndex + " is index: " + parent);
            if (heap[parent] < heap[newEntryIndex]) {
                return;
            } else {
                if(index < 200) {
                    swap(heap, newEntryIndex, parent);
                    siftUp(parent);
                }
            }

        }
    }

    public static int[] swap(int[] heap, int position1, int position2) {
        //System.out.println("Swapping: " + heap[position1] + " with: " + heap[position2]);
        int temp = heap[position1];
        heap[position1] = heap[position2];
        heap[position2] = temp;
        return heap;
    }

    public static int[] makeheap(int[] heap) {
    for (int i = index  / 2; i >= 0; i--)  
    {
        siftDown(i);
    }

    return heap;
}



public static void siftDown(int newNum) {

        //Child of the newNumber position is 2 * newNumber position + 1.
        int child = newNum * 2 + 1;

        //If the child position is not off the end of the array...
        if (child < index ) { 

            // If the right child is less than the left, set child = right. 
            if (heap[child] > heap[child + 1]) {
                child = child + 1;
            }

            /*If the parent is greater thant than the child, call the swap function 
                to swap parent and child, then call siftDown() to test whether
                new parent is larger than it's two children. 
            if (heap[newNum] > heap[child]) {               
                swap(heap, newNum, child);
                siftDown(child);
            }
        } else {
            return;
        }
    
    
}


    }
    */
    public static class Path {
        int distance;
        char[] path;
        char[] parents;
        int totalVertices;
    }
    public static int[][] adjacencyList = new int[26][26];
    static int totalCount = 0;
    static String alphabet = new String("abcdefghijklmnopqrstuvwxyz");
    static  int vertices = 0;
    static int sides = 0;
    static char targetPos;
    static char startPos;
    static double[] distanceToGoal = new double[26];
    static int[][] positionInGraph = new int[26][2];
    static int targetXpos;
    static int targetYpos;
   
    public static void main(String[] args) {
       
        File file = new File("ass3.txt"); 
       
        try {
            //Read in the file...
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String line = "";

            while(br.ready()) {
                line = br.readLine();

                String[] parts =line.split("\t"); 
                
                // Read in first line and set number of vertices and edges
                if(vertices == 0) {
                    vertices = Integer.parseInt(parts[0]);
                    //System.out.println("vertices: " + vertices);

                } else if(parts.length == 2 ) { // If at the last line where start and end positions are given....
                
                    startPos = parts[0].toCharArray()[0]; // Set start pos
                    targetPos =  parts[1].toCharArray()[0]; //Set end pos
                    System.out.println(startPos + " " + targetPos);

                }
               
                // Ignoring the part with co-ordinates, only looking at which nodes are connected....
                if(!isNumeric(parts[1]) && parts.length == 3) {
                    int position = 0;   
                    char first = '/';
                    char second = '/';
                    int weight = 0;
                    boolean hasEntry = false;
                   
                   
                    // For every letter...
                    for(char letter = 'a'; letter <= 'z'; letter++) {
                        
                        //setting start position of edge to the first character read in....
                        first = parts[0].toCharArray()[0];
                       
                        
                        //If the current letter matches the 'start position' 
                        if(letter == first) {
                            
                            //Add the targest node position to variable second, add weight of edge to variable weight...
                            second = parts[1].toCharArray()[0];
                            weight = Integer.parseInt(parts[2]);
                            
                            //Add the edge to the adjacency matrix at the correct position i.e. array[start][target] = weight
                            adjacencyList[position][alphabet.indexOf(second)] = weight;
                            //System.out.println(first + " end to: " + second + " weight: " + weight);
                           
                            
                        //Current letter is not equal to the new starting position letter, so increment to next letter...
                        } else {
                            hasEntry = false;
                            position++;
                            
                        }
                        
                    }  
                } else if(parts.length == 3) {
                    
                    int xPosition = Integer.parseInt(parts[1]);
                    int yPosition = Integer.parseInt(parts[2]);
                    int pos = alphabet.indexOf(parts[0]);
                    positionInGraph[pos][0] = xPosition;
                    positionInGraph[pos][1] = yPosition;
                    //System.out.println(pos + ": " + xPosition + " " + yPosition + " " + alphabet.indexOf(targetPos));
                    
                    
                }
                
             
            }

            
            targetXpos = positionInGraph[alphabet.indexOf(targetPos)][0];
            targetYpos =  positionInGraph[alphabet.indexOf(targetPos)][1];
           
            for(int x = 0; x < vertices; x++) {
                int distanceToXPos = Math.abs(targetXpos - positionInGraph[x][0]);
                int distanceToYPos = Math.abs(targetYpos - positionInGraph[x][1]);
                int largeValue = (distanceToXPos * distanceToXPos) + (distanceToYPos * distanceToYPos);
                double cValue = Math.sqrt(largeValue);
                distanceToGoal[x] = cValue;
                System.out.println("Distance to X position: " + distanceToXPos + " Distance to Y position: " + distanceToYPos + " Distance: " + cValue );
                
            }
        } catch( Exception e) {
            System.out.println(e.toString() + " ");
            e.printStackTrace();
        }
        
        Path path = Dj(-1, 2); // Finding the fastest path using Dijstra's

            
        System.out.println("-----Shortest path with Djisktra's:----------");
        System.out.print("Path: ");

        for(int x = 0; x < path.path.length; x++) {
        if(x != path.path.length -1){
            System.out.print(path.path[x] + ", "); // Print all nodes in the path... 
        } else {
            System.out.print(path.path[x]);
            System.out.println();

        }
        

    }
     
        System.out.println("Path distance: " + path.distance);
    
        System.out.println("Number of vertices visited: " + path.totalVertices);
        
        System.out.println();
        //Removing each edge for a  
        Path fastestPath = null;
        for(int x = 0; x < path.path.length -1; x++) {
            
            int previous = x +1;
            int temp = adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])];
            //System.out.println("Removing edge between: " + path.path[x] + " " + path.path[previous] + " " +  adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])]);
            adjacencyList[alphabet.indexOf(path.path[x] )][alphabet.indexOf(path.path[previous])] = 0; 
            Path p = Dj(alphabet.indexOf(path.path[x]), x);
            adjacencyList[alphabet.indexOf(path.path[x] )][alphabet.indexOf(path.path[previous])] = temp;
            if(fastestPath == null) {
                fastestPath = p;
            } else if(fastestPath.distance > p.distance) {
                fastestPath = p;
            }
           
        }

            
        System.out.println("---------Second Shortest path with Djisktra's-----------------");
        System.out.print("Path: ");
        for(int x = 0; x < fastestPath.path.length; x++) {
        if(x != fastestPath.path.length -1){
            System.out.print(fastestPath.path[x] + ", "); // Print all nodes in the path... 
        } else {
            System.out.print(fastestPath.path[x]);
            System.out.println();

        }
        

    }
     
        
     
        System.out.println("Path distance: " + fastestPath.distance);
    
        System.out.println("Number of vertices visited: " + fastestPath.totalVertices);

        System.out.println("--------- Shortest path with A*-----------------");

        Path newPath = aStar(-1, 2);
        System.out.print("Path: ");
        for(int x = 0; x < newPath.path.length; x++) {
        if(x != newPath.path.length -1){
            System.out.print(newPath.path[x] + ", "); // Print all nodes in the path... 
        } else {
            System.out.print(newPath.path[x]);
            System.out.println();

        }
        

    }
     
        
     
        System.out.println("Path distance: " + newPath.distance);
    
        System.out.println("Number of vertices visited: " + newPath.totalVertices);

      
        
}



public static Path Dj(int ignore, int position) { 
    int[] distance = new int[vertices]; // Holds the distances between a and each node that a can access...
    char[] candidates = new char[vertices]; // Holds the information about which nodes have not been selected...
    int candidateCount = vertices;
    int selectedCount = 0; // Holds count of the amount of candidates in the candidates array
    candidates = alphabet.substring(0, vertices -1).toCharArray();
    char[] selected = new char[vertices];
    int[] finalDistance = new int[vertices];
    char[] parents = new char[vertices];

   

    

    // For all letters in the alphabet (x)....
    for(int x = 0; x < vertices; x++) {
        
        /*If there exists an edge between a and (x), add the value to correct position i.e. if edge from a to b
            has distance of 7, distance[1] = 7; as b has numric value of 1. */
        if(adjacencyList[0][x] != 0) {
            distance[x] = adjacencyList[0][x];
            //Queue.push(x);
            //Queue.siftUp(selectedCount);
            //System.out.println("A to " + alphabet.charAt(x) + " weight: " + distance[x] );
            parents[x] = 'a';
            
        }
    }

    selectedCount++; //Assuming we have selected the entry node... 
    for(int x = 0; x < vertices -1; x++) {
        int closestIndex;
        char closest;
        //Setting the index and letter to the node which is yet to be added and has least distance...
        closestIndex = findSmallestDistance(distance, ignore, position); 
        closest = alphabet.charAt(findSmallestDistance(distance, ignore, position));
         
            
        //Tracking order and count of nodes that have been visited (moved to selected from candidate set)
        selected[selectedCount] = closest;
        selectedCount++;
        

        System.out.println("Closest: " + closest + " Total weight: " + distance[closestIndex] );
        
        //Setting the finalDistance of the selected node as it has been 'visited' and therefore it's distance is set...
        finalDistance[closestIndex] = distance[closestIndex];
       
        
        //If the closest node is the target node, break loop
        if(closest == 't') break;
        candidateCount--;

        // For all vertices...
        for(int z = 0; z < vertices; z++) {

            //If there exists a line between the selected vertice and vertice z AND the distance of vertice z isnt already set...
            if(adjacencyList[closestIndex][z] != 0 && distance[z] != -1) {
                
                //If the distance has yet to be set, add new distance that is weight of current node + weight of edge to node z
                if(distance[z] == 0) {
                    //System.out.println("New edge added:  " + alphabet.charAt(z) + " Weight: " + distance[closestIndex] +" " + adjacencyList[closestIndex][z] + " parent: " + closest);
                    distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                    parents[z] = closest;
                }/* Else if the distance has been set and current distance of node z is greater than
                    the weight of current node + weight of edge to node z, set new distance to the smaller value 
                    and update the parent to the current node... */
                else if(distance[z] > distance[closestIndex] + adjacencyList[closestIndex][z]) {
                    //System.out.println("Edge updated: " + alphabet.charAt(z) + " Weight: " + distance[closestIndex] + " " + adjacencyList[closestIndex][z] + " old value: " + distance[z] + " parent: " + closest);

                    distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                    parents[z] = closest;
                }
            }
        }
        // Setting the distance of the selected node to null value so isn't used in findSmallestDistance...
        distance[closestIndex] = -1;
        
    }
    /*
    for(int x = 0; x < vertices; x ++) {
        System.out.println(alphabet.charAt(x) +":  " + finalDistance[x] + " parent: " +parents[x]);
        
        
    }
    */

    
    //System.out.println("Shortest path with Djisktra's:");
    char[] path = showParents(0, alphabet.indexOf('t'), parents);
    //System.out.println("Path distance: " + finalDistance[alphabet.indexOf("t")]);
    //System.out.println("Number of vertices visited: " + selectedCount);
    Path finalPath = new Path();
    finalPath.distance =  finalDistance[alphabet.indexOf(targetPos)];
    finalPath.parents = parents;
    finalPath.path = path;
    finalPath.totalVertices = selectedCount;
    return finalPath;
   

}
        
public static char[] showParents(int start, int end, char[] parents) {
    char parent = parents[end]; //Setting the first parent, i.e. the parent of the target node...
    char[] path = new char[vertices]; //Array to hold the path that is taken to reach target node from start node...
    int pathCount = 0; //Counter to count the number of vertices in the path
    
    path[0] = alphabet.charAt(start); // Setting the start of the path to the start node...
    pathCount++; //Incrementing the path count...
    
    //While the parent node isn't the target node...
    while(alphabet.indexOf(parent) != start) {
        //System.out.print(parent + ", ");
        path[pathCount] = parent; //Add parent node to path...
        pathCount++; // Increment the path counter...
        parent = parents[alphabet.indexOf(parent)]; // Set new parent, i.e. the parent of the previous parent node...
    } 

    path[pathCount] = alphabet.charAt(end); // Add the target node to the end of the path... 
    pathCount++;
    
    
    //System.out.print("Path: " + path[0] + ", ");
    for(int x = 1; x <= pathCount; x++) {
        if(x != pathCount){
            //System.out.print(path[x] + ", "); // Print all nodes in the path... 
        } else {
           //  System.out.print(path[x]);
           //  System.out.println();

        }
        

    }
    char[] finalPath = new char[pathCount]; 
    for(int x = 0; x < pathCount; x++) {
        finalPath[x] = path[x];
    }


    return finalPath;
}
    
   


    
    
    public static int findSmallestDistance(int[] distance, int ignore, int ignorePosition) {
        int smallest = -1;
        int second = -1;
        for(int x = 0; x < distance.length ; x++) {
         
            if(smallest == -1 ){
                if(distance[x] > 0) {
                    smallest = x;
                } 
            } else {
                if(distance[x] > 0 && distance[x] < distance[smallest]) {
                    second = smallest;
                    smallest = x;
                }
            }
            


        }

        return smallest;

    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static Path aStar(int ignore, int position) { 
    int[] distance = new int[vertices]; // Holds the distances between a and each node that a can access...
    char[] candidates = new char[vertices]; // Holds the information about which nodes have not been selected...
    int candidateCount = vertices;
    int selectedCount = 0; // Holds count of the amount of candidates in the candidates array
    candidates = alphabet.substring(0, vertices -1).toCharArray();
    char[] selected = new char[vertices];
    int[] finalDistance = new int[vertices];
    char[] parents = new char[vertices];

   

    

    // For all letters in the alphabet (x)....
    for(int x = 0; x < vertices; x++) {
        
        /*If there exists an edge between a and (x), add the value to correct position i.e. if edge from a to b
            has distance of 7, distance[1] = 7; as b has numric value of 1. */
        if(adjacencyList[0][x] != 0) {
            distance[x] = adjacencyList[0][x];
            //Queue.push(x);
            //Queue.siftUp(selectedCount);
            //System.out.println("A to " + alphabet.charAt(x) + " weight: " + distance[x] );
            parents[x] = 'a';
            
        }
    }

    selectedCount++; //Assuming we have selected the entry node... 
    for(int x = 0; x < vertices -1; x++) {
        int closestIndex;
        char closest;
        //Setting the index and letter to the node which is yet to be added and has least distance...
        closestIndex = findSmallestDistanceAStar(distance, ignore, position); 
        closest = alphabet.charAt(findSmallestDistanceAStar(distance, ignore, position));
         
            
        //Tracking order and count of nodes that have been visited (moved to selected from candidate set)
        selected[selectedCount] = closest;
        selectedCount++;
        

        System.out.println("Closest: " + closest + " Total weight: " + distance[closestIndex] );
        
        //Setting the finalDistance of the selected node as it has been 'visited' and therefore it's distance is set...
        finalDistance[closestIndex] = distance[closestIndex];
       
        
        //If the closest node is the target node, break loop
        if(closest == 't') break;
        candidateCount--;

        // For all vertices...
        for(int z = 0; z < vertices; z++) {

            //If there exists a line between the selected vertice and vertice z AND the distance of vertice z isnt already set...
            if(adjacencyList[closestIndex][z] != 0 && distance[z] != -1) {
                
                //If the distance has yet to be set, add new distance that is weight of current node + weight of edge to node z
                if(distance[z] == 0) {
                    //System.out.println("New edge added:  " + alphabet.charAt(z) + " Weight: " + distance[closestIndex] +" " + adjacencyList[closestIndex][z] + " parent: " + closest);
                    distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z] ;
                    parents[z] = closest;
                }/* Else if the distance has been set and current distance of node z is greater than
                    the weight of current node + weight of edge to node z, set new distance to the smaller value 
                    and update the parent to the current node... */
                else if(distance[z] > distance[closestIndex] + adjacencyList[closestIndex][z]) {
                    //System.out.println("Edge updated: " + alphabet.charAt(z) + " Weight: " + distance[closestIndex] + " " + adjacencyList[closestIndex][z] + " old value: " + distance[z] + " parent: " + closest);

                    distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                    parents[z] = closest;
                }

               
            }
        }
        // Setting the distance of the selected node to null value so isn't used in findSmallestDistance...
        distance[closestIndex] = -1;
        
    }
    /*
    for(int x = 0; x < vertices; x ++) {
        System.out.println(alphabet.charAt(x) +":  " + finalDistance[x] + " parent: " +parents[x]);
        
        
    }
    */

    
    //System.out.println("Shortest path with Djisktra's:");
    char[] path = showParents(0, alphabet.indexOf('t'), parents);
    //System.out.println("Path distance: " + finalDistance[alphabet.indexOf("t")]);
    //System.out.println("Number of vertices visited: " + selectedCount);
    Path finalPath = new Path();
    finalPath.distance =  finalDistance[alphabet.indexOf(targetPos)];
    finalPath.parents = parents;
    finalPath.path = path;
    finalPath.totalVertices = selectedCount;
    return finalPath;
   

}

public static int findSmallestDistanceAStar(int[] distance, int ignore, int ignorePosition) {
        int smallest = -1;
        int second = -1;
        double heuristic;
        for(int x = 0; x < distance.length ; x++) {
           
         
            if(smallest == -1 ){
                if(distance[x] > 0) {
                    smallest = x;
                } 
            } else {
                heuristic = distance[x] + distanceToGoal[0];
                if(distance[x] > 0 && heuristic < distance[smallest] + distanceToGoal[smallest]) {
                    second = smallest;
                    smallest = x;
                }
            }
            


        }

        return smallest;

    }

    
}
