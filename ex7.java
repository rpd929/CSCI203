/*  Exercise 7
    Rory Davis
    5917670
    */
import java.io.*;
import java.lang.*;
import java.util.Arrays;
    
    
public class ex7 {
    public static int[][] adjacencyList;
    public static void main(String[] args) {

        //Declaring File to read from...
        File file = new File("ex7.txt"); 
        
        try {
            // Creating reader to read in file...
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String line = "";
            

            // Reading in the first line and setting number of total vertices and initialising adjacencyMatrix to this number
            int numVertices = Integer.parseInt(br.readLine().trim());
            adjacencyList = new int[numVertices][numVertices];
            //System.out.println(numVertices);

            // While the file isn't at the end...
            while(br.ready()) {
                
                line = br.readLine();

                //Splitting the line into it's 2 Integer parts...
                String[] parts =line.split("\t"); 

                
                int first = Integer.parseInt(parts[0]);
                int second = Integer.parseInt(parts[1]);

                //System.out.println(first + " " + second );

                //Setting adjacency matrix to show edge between vertices...
                //Setting the inverse as it is an undirected graph
                adjacencyList[first][second] = 1;
                adjacencyList[second][first] = 1;
                
                
                
            }
            
            //Calling Breadth First Search...
            BFS(0);

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

                

    }

    public static boolean contains(int[] setOfVertices, int counter, int checkNumber) {
        boolean contains = false;
        for(int x = 0; x <= counter; x++) {
            if(setOfVertices[x] == checkNumber ) {
                contains = true;
                break;
            } else {
                
            }
        }
        return contains;

    }

    public static void BFS(int vertex) {
        
        int[] setOfVertices = new int[adjacencyList.length];    // Set of vertics that have been added....
        int[] parentVertices = new int[adjacencyList.length];   // Set of vertices that are parents of those added to verticeSet...
        int counter = 0; //Counter of vertices added to set...
        
        //Enqueue the starting node...
        Queue.enqueue(vertex);
        int current;    //Variable to hold the current vertex being examined...
        
        //Parent of first node is null as it has no parent...
        parentVertices[vertex] = -1;

        // While the Queue of Nodes is not empty 
        while(!Queue.isEmpty() && setOfVertices.length <= adjacencyList.length){
            
            //Dequeue the next vertex and set it to current...
            current = Queue.dequeue();
            
            //Add the current vertex to the setOfVertices array...
            setOfVertices[counter] = current;
            
            if(current != 0){
                //Print parent and current vertice being added to list except for the first vertice (has no parent...)
                System.out.println(parentVertices[current] + " " + current );
            }
            
            counter++;

            //For all nodes reachable from the current node...
            for(int x = 0; x < adjacencyList[current].length; x++) {
                if(adjacencyList[current][x] == 1) {
                    
                    
                    int checkNumber = x;    //Variable holding the vertex to be checked...

                    //If the vertice isn't in the set of vertices or the queue to be added...
                    if(Arrays.stream(setOfVertices).anyMatch(y -> checkNumber == y) == false && Arrays.stream(Queue.queue).anyMatch(y -> checkNumber == y) == false) {
                        
                        //Add vertex to Queue...
                        Queue.enqueue(checkNumber);
                        
                        //Add parent of vertex to parents array...
                        parentVertices[checkNumber] = current;
                        
                    }

                }
            }
        }


    }

    public static  class Queue {
        static int head = 0;
        static int tail = 0;
        static int[] queue = new int[100];

        public static boolean isEmpty() {
            if(head >= tail) {
                return true;
            } else return false;
        }

        public static void enqueue(int num ) {
            queue[tail] = num;
            tail++;

        }

        public static int dequeue() {
            int num = queue[head];
            head++;
            return num;
            
        }
    }
}

                
