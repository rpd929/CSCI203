
import java.io.*;

public class ex6 {

    public static final int SIZE_OF_BUFF  = 100;
    public static final int CHAR_NUM = 4;  // All Characters A, C, G, and T
    public static final int prime =  7;  // prime number to use


    public static void main(String[] args)
    {
        char[] TARGET = new char[5000];
        char[] S = new char[10];

        File file = new File("ex6.txt"); 
        try {

            BufferedReader br = new BufferedReader(new FileReader(file)); 
    
            while (br.ready()) {
                String newLine1 = br.readLine();

                TARGET = newLine1.toCharArray();

                String newLine2 = br.readLine();

                S = newLine2.toCharArray();

                System.out.println(TARGET);
                System.out.println(S);

            }
            br.close();

            rabin_karp_search(TARGET, S);
        } catch( Exception e) {
                System.out.println("here" + e.getMessage());
            }    
       
    
    }

    /* A string pattern matching search using the Rabin-Karp Algorithm
    * :param TARGET: Char array to search.
    * :param P: Char array pattern to match.
    * :param prime: A prime number to use in hashing.
    * */
    public static void rabin_karp_search(char[] TARGET, char[] S)
    {
        int s_len = S.length;
        int t_len = TARGET.length;
        int i, j;
        int p = 0;  // hash value for pattern
        int t = 0;  // hash value for text string

        // preprocessing the hash value of p
        // the hash function is pow(CHAR_NUM, M - 1) mod prime
        int h = 1;
        for (int x = 0; x < s_len - 1; x++) {
            h = (h * CHAR_NUM) % prime;
        }

        // preprocessing the hash value of pattern and first part of search
        for (int x = 0; x < s_len; x++) {
            p = (CHAR_NUM * p + S[x]) % prime;
            t = (CHAR_NUM * t + TARGET[x]) % prime;
        }

        // matching
        for (int x = 0; x <= t_len; x++) {
            // check the value of the current substr and iterate over chars if they match
            if (p == t) {
                for (j = 0; j < s_len; j++) {
                    if (TARGET[x+j] != S[j]) break;  // if the next char doesnt match, no match found
                }
                
                if (j == s_len) {
                   System.out.printf("%-3d ", x);
                }
            }

            // apply the hash function for the next substr by
            // removing the leading digit and adding trailing digit
            if (x < t_len - s_len) {  // N - M because if the remaining string < M then stop
                t = (CHAR_NUM * (t - TARGET[x] * h) + TARGET[x + s_len]) % prime;  // rolling update

                if (t < 0) t += prime;  // ensure t is always positive
            }
        }
    }
}