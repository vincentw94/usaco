/*
ID: vincent30
LANG: JAVA
TASK: fact4
*/

import java.io.*;
import java.util.*;

public class fact4 {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("fact4.in")));
        int N = Integer.parseInt(in.readLine());
        in.close();

        int digits = 1;
        for (int i=2; i<=N; i++) {
            digits *= i;
            while (digits % 10 == 0)
                digits /= 10;

            // keep as many digits as we can without overflow
            // max N is 4220; Integer.MAX_VALUE / 4220 ~ 500000
            digits %= 100000;
            System.out.printf("i = %d digits = %d\n", i, digits);
        }

        int digit = digits %= 10;
        System.out.println(digit);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("fact4.out")));
        out.write("" + digit);
        out.newLine();
        out.close();
    }
}
