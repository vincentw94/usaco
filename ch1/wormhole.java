/*
ID: vincent30
LANG: JAVA
TASK: wormhole
*/

import java.io.*;
import java.util.*;
import java.awt.Point;

public class wormhole {

    static int total;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("wormhole.in")));
        int N = Integer.parseInt(in.readLine());
        Point[] wormholes = new Point[N];
        for (int i=0; i<N; i++) {
            String[] xy = in.readLine().split(" ");
            wormholes[i] = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }
        in.close();

        // sort by y value then by x, so to check next wormhole
        // we only have to look at next index
        Arrays.sort(wormholes, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                if (p1.y != p2.y)
                    return p1.y - p2.y;
                return p1.x - p2.x;
            }
        });

        // We can be smart and efficient using combinatorics
        // but let's be lazy and just brute force
        doWork(wormholes);

        System.out.println(total);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("wormhole.out")));
        out.write("" + total);
        out.newLine();
        out.close();
    }

    static void doWork(Point[] wormholes) {
        boolean[] paired = new boolean[wormholes.length];
        int[][] pairings = new int[wormholes.length/2][2];
        doWorkRecurse(0, wormholes, paired, pairings);
    }

    static void doWorkRecurse(int pairs, Point[] holes, boolean[] paired, int[][] pairings) {
        // If we have unpaired wormholes, go find all pairings recursively
        if (pairs * 2 != holes.length) {

            // Find first unpaired wormhole
            // Only pair this one so we don't duplicate
            for (int i=0; i<paired.length; i++) {
                if (!paired[i]) {
                    paired[i] = true;

                    // Find wormhole to pair it with
                    for (int j=i+1; j<paired.length; j++) {
                        if (!paired[j]) {
                            paired[j] = true;
                            pairings[pairs][0] = i;
                            pairings[pairs][1] = j;

                            // Recursive call
                            doWorkRecurse(pairs+1, holes, paired, pairings);

                            paired[j] = false;
                        }
                    }
                    paired[i] = false;
                    break;
                }
            }
        }

        else {
            // Check if cycle is possible
            for (int start=0; start<holes.length; start++) {
                if (hasCycle(holes, pairings, start)) {
                    total++;
                    break;
                }
            }
            //System.out.println(Arrays.deepToString(pairings));
        }
    }

    static boolean hasCycle(Point[] holes, int[][] pairings, int start) {
        // wormholes we have entered
        boolean[] entered = new boolean[holes.length];

        int curr = start;
        while (true) {
            if (entered[curr])
                return true;

            // Find pair
            entered[curr] = true;
            for (int[] pair : pairings) {
                int out = -1;
                if (pair[0] == curr)
                    out = pair[1];
                else if (pair[1] == curr)
                    out = pair[0];

                if (out != -1) {
                    // came out of top-right-most hole
                    if (out == holes.length - 1)
                        return false;

                    Point outHole = holes[out];
                    Point next = holes[out + 1];

                    // no hole to the right of the one we came out of
                    if (next.y != outHole.y)
                        return false;

                    curr = out + 1;

                    break;
                }
            }
        }
    }
}
