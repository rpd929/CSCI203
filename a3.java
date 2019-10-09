/*  Assignment 3
    Rory Davis
    5917670
    */
import java.io.*;
import java.lang.Object;

public class a3 {
    public static void main(String[] args) {
       
        File file = new File("ass3.txt"); 
        int[][] adjacencyList = new int[26][26];
        String alphabet = new String("abcdefghijklmnopqrstuvwxyz");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String line = "";

            while(br.ready()) {
                line = br.readLine();
                String[] parts =line.split("\t"); 
               

                if(!isNumeric(parts[1])) {
                    int position = 0;
                   
                    char first = '/';
                    char second = '/';
                    int weight = 0;
                    for(char letter = 'a'; letter <= 'z'; letter++) {
                        first = parts[0].toCharArray()[0];
                      

                        if(letter == first) {
                            second = parts[1].toCharArray()[0];
                            weight = Integer.parseInt(parts[2]);
                            adjacencyList[position][alphabet.indexOf(second)] = weight;
                            //System.out.println(first + " end to: " + second + " weight: " + weight);
                           


                        } else {
                            position++;
                           
                        }
                    }
                    
                   
                }
                
             
            }
        } catch( Exception e) {

        }
        int counter = 0;
        char c = 'a';
        for(int x = 0; x < 6; x++) {
            for(int y = 0; y <= 25; y++){

                if(adjacencyList[x][y] != 0) { 
                    if(counter == 0) {
                        System.out.print(alphabet.charAt(x) + ": " + alphabet.charAt(y) + "(" + adjacencyList[x][y] + ") ");
                        counter++;
                    } else {
                        System.out.print(alphabet.charAt(y) + "(" + adjacencyList[x][y] + ") ");
                    }
                   
                } else {
                    

                }   

            }
            System.out.println();
            counter = 0;
        }
    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
