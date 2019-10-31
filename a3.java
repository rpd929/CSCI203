
/*  Assignment 3
    Rory Davis
    5917670
    */
import java.io.*;
import java.lang.Object;
import java.math.*;

public class a3 {
    static final int infinity = Integer.MAX_VALUE / 2;

    public static class Queue {

        private static int index = 0;
        private static int heap[] = new int[25];
        private static int distanceToIndex[] = new int[2000];

        public static void push(int word, int distances) {
            if (word != 0) {
                heap[index] = distances;
            } else {
            }

            siftUp(index);
            index++;
            distanceToIndex[distances] = word;

        }

        public static void intitialiseQueue() {
            index = 0;
            heap = new int[25];
            distanceToIndex = new int[2000];
        }

        public static int find(int searchNumber) {
            for (int x = 0; x < index; x++) {
                if (heap[x] == searchNumber) {
                    return x;
                } 
            }
            return -1;
        }

        public static int dequeue() {
            int heapReturn = heap[0];
            heap[0] = infinity;
            index--;
            siftDown(0);
            return distanceToIndex[heapReturn];

        }

        public static void printHeap() {
            for (int x = 0; x < index; x++) {
                if (x == 0) {
                    System.out.print(heap[x] + ", ");
                } else if (x != index - 1) {
                    System.out.print(heap[x] + ", ");
                } else {
                    System.out.print(heap[x]);
                }
            }
            System.out.println();
        }

        public static void siftUp(int newEntryIndex) {
            int parent;

            if (newEntryIndex == 0) {
                return;
            } else {
                parent = (newEntryIndex - 1) / 2;
                if (heap[parent] < heap[newEntryIndex]) {
                    return;
                } else {
                    if (index < 200) {
                        swap(heap, newEntryIndex, parent);
                        siftUp(parent);
                    }
                }
            }
        }

        public static int[] swap(int[] heap, int position1, int position2) {
            int temp = heap[position1];
            heap[position1] = heap[position2];
            heap[position2] = temp;
            return heap;
        }

        public static int[] makeheap(int[] heap) {
            for (int i = index / 2; i >= 0; i--) {
                siftDown(i);
            }
            return heap;
        }

        public static void siftDown(int newNum) {
            // Child of the newNumber position is 2 * newNumber position + 1.
            int child = newNum * 2 + 1;
            // If the child position is not off the end of the array...
            if (child < index) {
                // If the right child is less than the left, set child = right.
                if (heap[child] > heap[child + 1]) {
                    child = child + 1;
                }
                /*
                 * If the parent is greater thant than the child, call the swap function to swap
                 * parent and child, then call siftDown() to test whether new parent is larger
                 * than it's two children.
                 */
                if (heap[newNum] > heap[child]) {
                    swap(heap, newNum, child);
                    siftDown(child);
                }
            } else {
                return;
            }
        }
    }

    public static class Path {
        int distance;
        char[] path;
        char[] parents;
        int totalVertices;
    }

    public static int[][] adjacencyList = new int[26][26];
    static int totalCount = 0;
    static String alphabet = new String("abcdefghijklmnopqrstuvwxyz");
    static int vertices = 0;
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
            // Read in the file...
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";

            while (br.ready()) {
                line = br.readLine();
                String[] parts = line.split("\t");
                // Read in first line and set number of vertices and edges
                if (vertices == 0) {
                    vertices = Integer.parseInt(parts[0]);
                } else if (parts.length == 2) { // If at the last line where start and end positions are given....
                    startPos = parts[0].toCharArray()[0]; // Set start pos
                    targetPos = parts[1].toCharArray()[0]; // Set end pos
                    System.out.println("Start and End Position: " + startPos + " " + targetPos);
                }

                // Ignoring the part with co-ordinates, only looking at which nodes are
                // connected....
                if (!isNumeric(parts[1]) && parts.length == 3) {
                    int position = 0;
                    char first = '/';
                    char second = '/';
                    int weight = 0;
                    boolean hasEntry = false;
                    // For every letter...
                    for (char letter = 'a'; letter <= 'z'; letter++) {
                        // setting start position of edge to the first character read in....
                        first = parts[0].toCharArray()[0];
                        // If the current letter matches the 'start position'
                        if (letter == first) {
                            // Add the targest node position to variable second, add weight of edge to
                            // variable weight...
                            second = parts[1].toCharArray()[0];
                            weight = Integer.parseInt(parts[2]);
                            // Add the edge to the adjacency matrix at the correct position i.e. array[start][target] = weight
                            adjacencyList[position][alphabet.indexOf(second)] = weight;
                          
                        // Current letter is not equal to the new starting position letter, so increment to next letter...
                        } else {
                            hasEntry = false;
                            position++;
                        }
                    }
                } else if (parts.length == 3) {
                    int xPosition = Integer.parseInt(parts[1]);
                    int yPosition = Integer.parseInt(parts[2]);
                    int pos = alphabet.indexOf(parts[0]);
                    positionInGraph[pos][0] = xPosition;
                    positionInGraph[pos][1] = yPosition;
                }
            }
            targetXpos = positionInGraph[alphabet.indexOf(targetPos)][0];
            targetYpos = positionInGraph[alphabet.indexOf(targetPos)][1];
            for (int x = 0; x < vertices; x++) {
                int distanceToXPos = Math.abs(targetXpos - positionInGraph[x][0]);
                int distanceToYPos = Math.abs(targetYpos - positionInGraph[x][1]);
                int largeValue = (distanceToXPos * distanceToXPos) + (distanceToYPos * distanceToYPos);
                double cValue = Math.sqrt(largeValue);
                distanceToGoal[x] = cValue;
            }
        } catch (Exception e) {
            System.out.println(e.toString() + " ");
            e.printStackTrace();
        }

        Path path = Dj(); // Finding the fastest path using Dijstra's

        System.out.println("-----Shortest path with Djisktra's:----------");
        System.out.print("Path: ");
        for (int x = 0; x < path.path.length; x++) {
            if (x != path.path.length - 1) {
                System.out.print(path.path[x] + ", "); // Print all nodes in the path...
            } else {
                System.out.print(path.path[x]);
                System.out.println();
            }
        }
        System.out.println("Path distance: " + path.distance);
        System.out.println("Number of vertices visited: " + path.totalVertices);
        System.out.println();
        
        // Removing each edge for a
        Path fastestPath = null;
        for (int x = 0; x < path.path.length - 1; x++) {
            Queue.intitialiseQueue();
            int previous = x + 1;
            int temp = adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])];
            adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])] = 0;
            Path p = new Path();
            p = Dj();
        
            adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])] = temp;
            if (fastestPath == null) {
                fastestPath = p;
            } else if (fastestPath.distance > p.distance && p.distance != 0) {
                fastestPath = p;
            }
        }

        System.out.println("---------Second Shortest path with Djisktra's-----------------");
        System.out.print("Path: ");
        for (int x = 0; x < fastestPath.path.length; x++) {
            if (x != fastestPath.path.length - 1) {
                System.out.print(fastestPath.path[x] + ", "); // Print all nodes in the path...
            } else {
                System.out.print(fastestPath.path[x]);
                System.out.println();
            }
        }
        System.out.println("Path distance: " + fastestPath.distance);
        System.out.println("Number of vertices visited: " + fastestPath.totalVertices);
        System.out.println();

        System.out.println("--------- Shortest path with A*-----------------");
        Queue.intitialiseQueue();
        Path newPath = aStar();
        System.out.print("Path: ");
        for (int x = 0; x < newPath.path.length; x++) {
            if (x != newPath.path.length - 1) {
                System.out.print(newPath.path[x] + ", "); // Print all nodes in the path...
            } else {
                System.out.print(newPath.path[x]);
                System.out.println();
            }
        }
        System.out.println("Path distance: " + newPath.distance);
        System.out.println("Number of vertices visited: " + newPath.totalVertices);
        System.out.println();

        Path AStarfastestPath = null;
        for (int x = 0; x < path.path.length - 1; x++) {
            Queue.intitialiseQueue();
            int previous = x + 1;
            int temp = adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])];
            adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])] = 0;
            Path p = new Path();
            p = aStar();
            adjacencyList[alphabet.indexOf(path.path[x])][alphabet.indexOf(path.path[previous])] = temp;
            if (AStarfastestPath == null) {
                AStarfastestPath = p;
            } else if (AStarfastestPath.distance > p.distance && p.distance != 0) {
                AStarfastestPath = p;
            }
        }

        System.out.println("---------Second Shortest path with A*-----------------");
        System.out.print("Path: ");
        for (int x = 0; x < AStarfastestPath.path.length; x++) {
            if (x != AStarfastestPath.path.length - 1) {
                System.out.print(AStarfastestPath.path[x] + ", "); // Print all nodes in the path...
            } else {
                System.out.print(AStarfastestPath.path[x]);
                System.out.println();
            }
        }
        System.out.println("Path distance: " + AStarfastestPath.distance);
        System.out.println("Number of vertices visited: " + AStarfastestPath.totalVertices);
    }

    public static Path Dj() {
        int[] distance = new int[vertices]; // Holds the distances between a and each node that a can access...
        int selectedCount = 0; // Holds count of the amount of candidates in the candidates array
        char[] selected = new char[vertices];
        int[] finalDistance = new int[vertices];
        char[] parents = new char[vertices];

        distance[0] = infinity;

        // For all letters in the alphabet (x)....
        for (int x = 0; x < vertices; x++) {
            
            /* * If there exists an edge between a and (x), add the value to correct position
             * i.e. if edge from a to b has distance of 7, distance[1] = 7; as b has numric
             * value of 1.*/
            if (adjacencyList[0][x] != 0) {
                distance[x] = adjacencyList[0][x];
                Queue.push(x, adjacencyList[0][x]);
                parents[x] = 'a';
            }
        }
        selectedCount++; // Assuming we have selected the entry node...

        for (int x = 0; x < vertices - 1; x++) {
            int closestIndex;
            char closest;
            // Setting the index and letter to the node which is yet to be added and has least distance...
            closestIndex = Queue.dequeue();
            closest = alphabet.charAt(closestIndex);

            // Tracking order and count of nodes that have been visited (moved to selected
            // from candidate set)
            selected[selectedCount] = closest;
            selectedCount++;

            // Setting the finalDistance of the selected node as it has been 'visited' and therefore it's distance is set...
            finalDistance[closestIndex] = distance[closestIndex];

            // If the closest node is the target node, break loop
            if (closest == targetPos) break;
            
            // For all vertices...
            for (int z = 0; z < vertices; z++) {
                // If there exists a line between the selected vertice and vertice z AND the
                // distance of vertice z isnt already set...
                if (adjacencyList[closestIndex][z] != 0 && distance[z] != infinity && distance[closestIndex] != -1) {
                    // If the distance has yet to be set, add new distance that is weight of current
                    // node + weight of edge to node z
                    if (distance[z] == 0) {
                        distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                        Queue.push(z, distance[closestIndex] + adjacencyList[closestIndex][z]);
                        parents[z] = closest;
                    } /* Else if the distance has been set and current distance of node z is greater
                       * than the weight of current node + weight of edge to node z, set new distance
                       * to the smaller value and update the parent to the current node...*/
                    else if (distance[z] > distance[closestIndex] + adjacencyList[closestIndex][z] && distance[z] != -1) {
                        int queuePosition = Queue.distanceToIndex[distance[z]];
                        Queue.heap[queuePosition] = distance[closestIndex] + adjacencyList[closestIndex][z];
                        Queue.makeheap(Queue.heap);
                        distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                        parents[z] = closest;
                    } 
                }
            }
            // Setting the distance of the selected node to null value so isn't used in findSmallestDistance...
            distance[closestIndex -1] = infinity;
        }

        char[] path = showParents(0, alphabet.indexOf('t'), parents);
        Path finalPath = new Path();
        finalPath.distance = finalDistance[alphabet.indexOf(targetPos)];
        finalPath.parents = parents;
        finalPath.path = path;
        finalPath.totalVertices = selectedCount;
        return finalPath;
    }

    public static char[] showParents(int start, int end, char[] parents) {
        char parent = parents[end]; // Setting the first parent, i.e. the parent of the target node...
        char[] path = new char[vertices]; // Array to hold the path that is taken to reach target node from start node...
        int pathCount = 0; // Counter to count the number of vertices in the path
        path[0] = alphabet.charAt(start); // Setting the start of the path to the start node...
        pathCount++; // Incrementing the path count...

        // While the parent node isn't the target node...
        while (alphabet.indexOf(parent) != start) {
            path[pathCount] = parent; // Add parent node to path...
            pathCount++; // Increment the path counter...
            parent = parents[alphabet.indexOf(parent)]; // Set new parent, i.e. the parent of the previous node...
        }
        path[pathCount] = alphabet.charAt(end); // Add the target node to the end of the path...
        pathCount++;

        char[] finalPath = new char[pathCount];
        for (int x = 0; x < pathCount; x++) {
            finalPath[x] = path[x];
        }
        return finalPath;
    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Path aStar() {
        int[] distance = new int[vertices]; // Holds the distances between a and each node that a can access...
        int selectedCount = 0; // Holds count of the amount of candidates in the candidates array
        char[] selected = new char[vertices];
        int[] finalDistance = new int[vertices];
        char[] parents = new char[vertices];

        distance[0] = infinity;

        // For all letters in the alphabet (x)....
        for (int x = 0; x < vertices; x++) {
            /* If there exists an edge between a and (x), add the value to correct position
             * i.e. if edge from a to b has distance of 7, distance[1] = 7; as b has numric
             * value of 1.*/
            if (adjacencyList[0][x] != 0) {
                distance[x] = adjacencyList[0][x];
                Queue.push(x, adjacencyList[0][x] + (int) distanceToGoal[x]);
                // System.out.println("A to " + alphabet.charAt(x) + " weight: " + distance[x]
                // );
                parents[x] = 'a';
            }
        }

        selectedCount++; // Assuming we have selected the entry node...

        for (int x = 0; x < vertices - 1; x++) {
            int closestIndex;
            char closest;
            // Setting the index and letter to the node which is yet to be added and has least distance...
            closestIndex = Queue.dequeue();
            closest = alphabet.charAt(closestIndex);

            // Tracking order and count of nodes that have been visited (moved to selected
            // from candidate set)
            selected[selectedCount] = closest;
            selectedCount++;

            // Setting the finalDistance of the selected node as it has been 'visited' and therefore it's distance is set...
            finalDistance[closestIndex] = distance[closestIndex];

            // If the closest node is the target node, break loop
            if (closest == targetPos) break;

            // For all vertices...
            for (int z = 0; z < vertices; z++) {

                // If there exists a line between the selected vertice and vertice z AND the distance of vertice z isnt already set...
                if (adjacencyList[closestIndex][z] != 0 && distance[z] != infinity && distance[closestIndex] != -1) {

                    // If the distance has yet to be set, add new distance that is weight of current node + weight of edge to node z
                    if (distance[z] == 0) {
                        distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                        Queue.push(z, distance[closestIndex] + (int) distanceToGoal[x] + adjacencyList[closestIndex][z]
                                + (int) distanceToGoal[z]);
                        parents[z] = closest;
                    } /*Else if the distance has been set and current distance of node z is greater
                       * than the weight of current node + weight of edge to node z, set new distance
                       * to the smaller value and update the parent to the current node...*/
                    else if (distance[z] > distance[closestIndex] + adjacencyList[closestIndex][z]&& distance[z] != -1) {
                        int queuePosition = Queue.distanceToIndex[distance[z]];// Queue.find(distance[z]);
                        Queue.heap[queuePosition] = distance[closestIndex] + adjacencyList[closestIndex][z] + (int) distanceToGoal[z];
                        distance[z] = distance[closestIndex] + adjacencyList[closestIndex][z];
                        parents[z] = closest;
                    }
                }
            }
            // Setting the distance of the selected node to null value so isn't used in findSmallestDistance...
            distance[closestIndex] = infinity;
        }

        char[] path = showParents(0, alphabet.indexOf('t'), parents);
        Path finalPath = new Path();
        finalPath.distance = finalDistance[alphabet.indexOf(targetPos)];
        finalPath.parents = parents;
        finalPath.path = path;
        finalPath.totalVertices = selectedCount;
        return finalPath;
    }
}

/* This solution uses the following data structures and algorithms: The Distances to nodes were kept in min heap to always return the smallest distance,
the edges are kept in a adjacency matrix to quickly access the different edges in the graph by index. Another 2D array is also used to store each vertexes
X and Y position in relation to the target vertex in the graph in order to determine euclid distance for each vertex easily. Dijstra's algrothithm has been
used to find the shortest and second shortest paths. In both cases, the algrothim stops once it has reached the target, reducing unneeded searched. The 
second shortest path with Dijstra's is found by removing each edge of the fastest path and finding a new path and taking the smallest of these paths. The A 
star algorithm is implemented in a similar way except that it also uses the euclid distance to goal as a further heuristic when finding next closest node. 
The second shortest path is found in A star the same way, by removing each edge from the pastest path and then calling Astar to find the fastest path without 
that edge, then taking the smallest of these paths to be the second shortest path. QUESTIONS: 
What if we require that the second shortest path be longer than the shortest path: This solutions would be need to be changed in order to make sure this
condition was met as the current solution does not take into account the path lengths (in terms of vertex's) when determining fastest path. Change would 
involve tracking path length and exiting if length is greater than the length of the shortest path.
What if the graph contains cycles: This would no change the implementation as only a new vertex is added at each iteration, and therefore, a vertex is 
only visited once, even if the graph contains cycles. 
What if the graph is undirected: If it were undirected, when reading in the data into the adjacency matrix, the vertext at the starting position and end
position will be added into both positions i.e. adjacencyList[a][b] = 5, adjacencyList[b][a] = 5 and now the algrothim will work exactly as it did before as
this essentially makes it into a directed graph from the adjacencyList perspective.
 */
