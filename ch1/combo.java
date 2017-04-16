/*
ID: vincent30
LANG: JAVA
TASK: combo
*/

import java.io.*;

public class combo {

    // closest distance between a and b on dial
    static int dist(int a, int b, int N) {
        int dist = Math.abs(a - b);
        if (dist > N / 2)
            dist = N - dist;
        return dist;
    }

    // returns number of digit overlap (within 2)
    // between a and b
    static int overlap(int a, int b, int N) {
        int d = dist(a, b, N);
        return Math.max(0, Math.min(N, 5 - d));
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("combo.in")));


        int N = Integer.parseInt(in.readLine());

        int[] farmer = new int[3];
        String[] tokens = in.readLine().trim().split(" ");
        for (int i=0; i<3; i++)
            farmer[i] = Integer.parseInt(tokens[i]);

        int[] master = new int[3];
        tokens = in.readLine().trim().split(" ");
        for (int i=0; i<3; i++)
            master[i] = Integer.parseInt(tokens[i]);
        in.close();

        // number of combos if no overlap
        int base = 2 * (int)(Math.pow( Math.min(N, 5), 3 ));
        int common = 1;
        for (int i=0; i<3; i++) {
            common *= overlap(farmer[i], master[i], N);
        }
        System.out.println(base - common);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("combo.out")));
        out.write("" + (base - common));
        out.newLine();
        out.close();
    }
}
