package edu.neu.coe.info6205.union_find;
import java.util.*;
public class UFClient {
    public static void main(String args[]) {
        System.out.println("Enter the value for n: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("No of generated pairs = "+count(n));
        System.out.println("\n\n\n");
        generateDataPoints(25);
    }

    private static void generateDataPoints(int runs) {
        for(int i=1; i<50; i++) {
            int n = 1<<i;
            int avgPairsGenerated = 0;
            for(int j=0; j<runs; j++) {
                avgPairsGenerated += count(n);
            }
            avgPairsGenerated /= runs;
            System.out.println("Average no of pairs generated for n = " + n + " is " + avgPairsGenerated);
        }
    }
    public static int count(int n) {
        Random rnd = new Random();
        UF_HWQUPC ufh = new UF_HWQUPC(n);
        int generatedCount = 0;

        while (ufh.components() > 1) {
            int node1 = rnd.nextInt(n);
            int node2 = rnd.nextInt(n);
            generatedCount++;
            if(!ufh.connected(node1, node2)) {
                ufh.union(node1, node2);
            }
        }
        return generatedCount;
    }
}