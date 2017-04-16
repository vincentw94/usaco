/*
ID: vincent30
LANG: JAVA
TASK: spin
*/

import java.io.*;
import java.util.*;

public class spin {
    static class Wheel {
        int[][] wedges;
        int angle;
        int speed;

        public Wheel(int speed, int[][] wedges) {
            angle = 0;
            this.speed = speed;
            this.wedges = wedges;
        }

        void rotate() {
            angle = (angle + speed) % 360;
        }
    }

    static class Config {
        int r2, r3, r4, r5;

        // find angles relative to wheels[0].angle
        public Config(Wheel[] wheels) {
            r2 = (wheels[1].angle - wheels[0].angle + 360) % 360;
            r3 = (wheels[2].angle - wheels[0].angle + 360) % 360;
            r4 = (wheels[3].angle - wheels[0].angle + 360) % 360;
            r5 = (wheels[4].angle - wheels[0].angle + 360) % 360;
        }

        @Override
        public int hashCode() {
            int hash = r2;
            hash = 360*hash + r3;
            hash = 360*hash + r4;
            hash = 360*hash + r5;
            return hash;
        }

        @Override
        public boolean equals(Object other) {
            Config oth = (Config)other;
            return r2 == oth.r2 && r3 == oth.r3
                && r4 == oth.r4 && r5 == oth.r5;
        }
    }

    static boolean allOverlap(Wheel[] wheels) {
        int[] open = new int[360];

        for (Wheel wheel : wheels) {
            for (int[] wedge : wheel.wedges) {
                int start = (wheel.angle + wedge[0]) % 360;
                int extent = wedge[1];

                System.out.println(start + " -> " + (start + extent) % 360);

                for (int angle=start; angle<=start+extent; angle++)
                    open[ angle % 360 ]++;
            }
        }

        for (int i=0; i<360; i++) {
            if (open[i] == 5) {
                System.out.println("\nall overlap at " + i);
                return true;
            }
        }
        return false;

    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("spin.in")));
        Wheel[] wheels = new Wheel[5];
        for (int i=0; i<5; i++) {
            String[] tokens = in.readLine().split(" ");
            int speed = Integer.parseInt(tokens[0]);
            int numWedges = Integer.parseInt(tokens[1]);
            int[][] wedges = new int[numWedges][2];
            for (int j=0; j<numWedges; j++) {
                wedges[j][0] = Integer.parseInt(tokens[2*j + 2]);
                wedges[j][1] = Integer.parseInt(tokens[2*j + 3]);
            }

            wheels[i] = new Wheel(speed, wedges);
        }

        HashSet<Config> seen = new HashSet<Config>();
        int sec = 0;
        boolean success = true;

        // we know at most 360 iterations
        for (;; sec++) {
            System.out.println("\n" + sec);
            Config curr = new Config(wheels);
            if (seen.contains(curr)) {
                success = false;
                break;
            }
            seen.add(curr);

            if (allOverlap(wheels)) {
                break;
            }

            for (Wheel w : wheels)
                w.rotate();
        }

        String res = success ? ("" + sec) : "none";
        System.out.println(res);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("spin.out")));
        out.write(res);
        out.newLine();
        out.close();
    }
}
