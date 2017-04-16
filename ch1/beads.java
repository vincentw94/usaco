/*
ID: vincent30
LANG: JAVA
TASK: beads
*/

import java.io.*;

public class beads {

    static boolean equals(char c1, char c2) {
        return c1 == 'w' || c2 == 'w' || c1 == c2;
    }

    static char update(char orig, char curr) {
        if (orig == 'w')
            return curr;
        return orig;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("beads.in")));

        int N = Integer.parseInt(in.readLine());
        String beads = in.readLine();

        int best = 0;
        // consider splitting right before the i-th bead
        for (int i=0; i<N; i++) {
            // look left
            // start at j=1 to avoid counting i-th bead
            int left = 0;
            char leftColor = 'w';
            for (int j=1; j<N; j++) {
                int leftInd = (i - j + N) % N;
                if (equals(leftColor, beads.charAt(leftInd))) {
                    left++;
                    leftColor = update(leftColor, beads.charAt(leftInd));
                } else {
                    break;
                }
            }

            // look right
            // look up to N-left so we don't double count beads,
            // e.g. if all beads are blue
            int right = 0;
            char rightColor = 'w';
            for (int j=0; j<N-left; j++) {
                int rightInd = (i + j) % N;
                if (equals(rightColor, beads.charAt(rightInd))) {
                    right++;
                    rightColor = update(rightColor, beads.charAt(rightInd));
                } else {
                    break;
                }
            }

            best = Math.max(best, left + right);
        }

        System.out.println(best);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("beads.out")));
        out.write("" + best);
        out.newLine();
        out.close();
    }
}
