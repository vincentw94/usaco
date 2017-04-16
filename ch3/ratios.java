/*
ID: vincent30
LANG: JAVA
TASK: ratios
*/

import java.io.*;
import java.util.*;

public class ratios {
    // check if (a1, a2, a3) is a multiple of (b1, b2, b3)
    // avoid divide by 0
    static int multiple(int[] a, int[] b) {
        int ratio = 0;
        for (int i=0; i<3; i++) {
            // either both zero or both nonzero
            if ( (a[i] == 0) != (b[i] == 0))
                return -1;

            // both zero
            else if (a[i] == 0)
                continue;

            // check remainder and ratio
            else if (a[i] % b[i] != 0 || (ratio != 0 && a[i] / b[i] != ratio))
                return -1;

            ratio = a[i]/ b[i];
        }
        return ratio;
    }

    static boolean exceeds(int[] grains, int[] target) {
        for (int i=0; i<3; i++)
            if (grains[i] > target[i])
                return true;
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("ratios.in")));
        int[][] ratios = new int[4][3];
        for (int i=0; i<4; i++) {
            String[] tokens = in.readLine().split(" ");
            for (int j=0; j<3; j++) {
                ratios[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        // the solution
        int m1 = -1, m2 = -1, m3 = -1, m4 = -1;

        int[] grains = new int[3];   // reuse

        outer: {
        for (m1=0; m1<100; m1++) {
            for (m2=0; m2<100; m2++) {
                for (m3=0; m3<100; m3++) {
//                  System.out.printf("%d %d %d -> %s\n", m1, m2, m3, Arrays.toString(grains));

                    if ((m4 = multiple(grains, ratios[0])) != -1) {
                        m1 = m1;
                        m2 = m2;
                        m3 = m3;
                        break outer;
                    }

                    // update m3
                    grains[0] += ratios[3][0];
                    grains[1] += ratios[3][1];
                    grains[2] += ratios[3][2];
                }
                // update m2 and reset m3
                grains[0] += (ratios[2][0] - 100*ratios[3][0]);
                grains[1] += (ratios[2][1] - 100*ratios[3][1]);
                grains[2] += (ratios[2][2] - 100*ratios[3][2]);
            }
            // update m1 and reset m2
            grains[0] += (ratios[1][0] - 100*ratios[2][0]);
            grains[1] += (ratios[1][1] - 100*ratios[2][1]);
            grains[2] += (ratios[1][2] - 100*ratios[2][2]);
        }
        } // end outer


        String res = m4 == -1 ? "NONE" : String.format("%d %d %d %d", m1, m2, m3, m4);

        System.out.println(res);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("ratios.out")));
        out.write(res);
        out.newLine();
        out.close();
    }
}
