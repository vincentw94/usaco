/*
ID: vincent30
LANG: JAVA
TASK: kimbits
*/

import java.io.*;
import java.util.*;

public class kimbits {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("kimbits.in")));
        String[] line = in.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int L = Integer.parseInt(line[1]);
        long I = Long.parseLong(line[2]);
        in.close();

        String res = doWork(N, L, I);
        System.out.println(res);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("kimbits.out")));
        out.write(res);
        out.newLine();
        out.close();
    }

    static String doWork(int n, int l, long i) {
        if (n == 1) {
            return i == 1 ? "0" : "1";
        }

        long lead0Count = sizeOf(n-1, l);
        String ret;
        if (i <= lead0Count)
            ret = "0" + doWork(n-1, l, i);
        else
            ret = "1" + doWork(n-1, l-1, i - lead0Count);
        System.out.printf("n = %d l = %d i = %d ret = %s\n", n, l, i, ret);
        return ret;
    }

    static long sizeOf(int n, int l) {
        l = Math.min(n, l);

        // sum( (n choose k) for 0 <= k <= l )
        int total = 0;
        for (int k=0; k<=l; k++)
            total += choose(n, k);
        return total;
    }

    // n! / (k! * (n-k)!)
    static long choose(int n, int k) {
        k = Math.max(k, n-k);
        int toDivide = n - k;

        long prod = 1;
        while (n > k || toDivide > 1) {
            if (toDivide > 1 && prod % toDivide == 0)
                prod /= toDivide--;
            else
                prod *= n--;
        }
        return prod;
    }
}
