/*
Exercise 5
Rory Davis
rpd929
*/

import java.io.*;
import java.util.*;

public class ex6 {

    public static final int SIZE_OF_BUFF  = 100;
    public static final int CHAR_NUM = 4;  // All Characters A, C, G, and T
    public static final int prime =  7;  //PRIME NUMBER TO USE FOR MODS


    public static void main(String[] args)
    {
        char[] TARGET = new char[5000];
        char[] SEARCH = new char[10];

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file name");

        String fileName = scan.nextLine();
        scan.close();

        File file = new File(fileName); 
        try {

            BufferedReader br = new BufferedReader(new FileReader(file)); 
    
            while (br.ready()) {
                String newLine1 = br.readLine();

                TARGET = newLine1.toCharArray();

                String newLine2 = br.readLine();

                SEARCH = newLine2.toCharArray();

            }
            br.close();

           RK_search(TARGET, SEARCH);
        } catch( Exception e) {
                System.out.println(e.getMessage());
            }    
       
    
    }

    
    public static void RK_search(char[] TARGET, char[] SEARCH)
    {
        int s_size = SEARCH.length;
        int t_size = TARGET.length;
        int i, j;
        int p = 0;  // HASH FOR PATTERN
        int t = 0;  // HASH FOR TEXT STRING

        //CALCULATING HASH OF p
        //HASH: pow(CHAR_NUM, M - 1) mod prime
        int h = 1;
        for (int x = 0; x < s_size - 1; x++) {
            h = (h * CHAR_NUM) % prime;
        }

        // CALCULATING HASH OF PATTERN AND FIRST PART OF SEARCH
        for (int x = 0; x < s_size; x++) {
            p = (CHAR_NUM * p + SEARCH[x]) % prime;
            t = (CHAR_NUM * t + TARGET[x]) % prime;
        }

        // MATCHING .... 
        for (int x = 0; x <= t_size; x++) {
            
            // IF MATCHES, ITERATE OVER AND MATCH CHARACTERS DIRECTLY
            if (p == t) {
                for (j = 0; j < s_size; j++) {
                    if (TARGET[x+j] != SEARCH[j]) break;  // NO MATCH: THEREFORE BREAK
                }
                
                if (j == s_size) {
                   System.out.print(x + ", ");
                }
            }

            // HASH NEXT SUBSTRING BY TRIMMING FIRST CHARACTER AND ADDING NEXT CHARACTER
            if (x < t_size - s_size) {  // N - M because if the remaining string < M then stop
                t = (CHAR_NUM * (t - TARGET[x] * h) + TARGET[x + s_size]) % prime;  // rolling update

                if (t < 0) t += prime;  // MAKE T POSITIVE
            }
        }
    }
}
