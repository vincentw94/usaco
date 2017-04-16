/*
ID: vincent30
LANG: JAVA
TASK: transform
*/

import java.io.*;
import java.util.*;

public class transform {
    // new (row, col) in transformed square
    // take int[] instead of 2 ints because java can't return 2 values
    static interface Trans {
        void apply(int[] rc, int n);
    }

    // composition of 0 or more transforms
    static class TransCompose implements Trans {
        List<Trans> trans;
        public TransCompose() {
            trans = new LinkedList<Trans>();
        }

        @Override
        public void apply(int[] rc, int n) {
            for (Trans t : trans)
                t.apply(rc, n);
        }

        void addTrans(Trans t) {
            trans.add(t);
        }

        void clear() {
            trans.clear();
        }
    }

    // (row, col) -> (col, N - row - 1)
    static class Rotate90 implements Trans {
        @Override
        public void apply(int[] rc, int n) {
            int r = rc[0], c = rc[1];
            rc[0] = c;
            rc[1] = n - r - 1;
        }
    }

    // (row, col) -> (row, N - col - 1)
    static class Flip implements Trans {
        @Override
        public void apply(int[] rc, int n) {
            int c = rc[1];
            rc[1] = n - c - 1;
        }
    }

    static boolean becomes(char[][] orig, char[][] target, TransCompose compose) {
        int N = orig.length;
        int[] rc = new int[2];
        for (int r=0; r<N; r++) {
            for (int c=0; c<N; c++) {
                rc[0] = r;
                rc[1] = c;
                compose.apply(rc, N);
                System.out.printf("r=%d c=%d tr=%d tc=%d orig=%s tr=%s\n",
                        r, c, rc[0], rc[1], orig[r][c], target[rc[0]][rc[1]]);

                if (orig[r][c] != target[rc[0]][rc[1]])
                    return false;
            }
        }
        return true;
    }

    static void debugPrint(char[][] orig, TransCompose compose) {
        int N = orig.length;
        char[][] image = new char[N][N];
        int[] rc = new int[2];

        for (int r=0; r<N; r++) {
            for (int c=0; c<N; c++) {
                rc[0] = r;
                rc[1] = c;
                compose.apply(rc, N);
                image[rc[0]][rc[1]] = orig[r][c];
            }
        }

        for (char[] arr : image)
            System.out.println(Arrays.toString(arr));
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("transform.in")));

        int N = Integer.parseInt(in.readLine());
        char[][] orig = new char[N][N];
        for (int i=0; i<N; i++) {
            String s = in.readLine();
            for (int j=0; j<N; j++)
                orig[i][j] = s.charAt(j);
        }

        char[][] target = new char[N][N];
        for (int i=0; i<N; i++) {
            String s = in.readLine();
            for (int j=0; j<N; j++)
                target[i][j] = s.charAt(j);
        }

        // Don't keep reallocating these
        Trans rotate90 = new Rotate90();
        Trans flip = new Flip();

        int output = 7;
        TransCompose compose = new TransCompose();

        for (int numFlips=0; numFlips<=1; numFlips++) {
            for (int numRotates=0; numRotates<=3; numRotates++) {
                System.out.println("\nflip = " + numFlips + "  rot = " + numRotates);
                debugPrint(orig, compose);

                if (becomes(orig, target, compose)) {
                    int id = numRotates;
                    if (numFlips + numRotates == 0)
                        id = 6;
                    else if (numFlips == 1)
                        id = (numRotates == 0) ? 4 : 5;

                    System.out.printf("\nflip = %d rotate = %d id = %d\n", numFlips, numRotates, id);
                    output = Math.min(output, id);
                }

                compose.addTrans(rotate90);
            }

            // don't need to clear since 4 rotates cancel,
            // but clear anyways to keep it simple
            compose.clear();
            compose.addTrans(flip);
        }

        System.out.println(output);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("transform.out")));
        out.write("" + output);
        out.newLine();
        out.close();
    }
}
