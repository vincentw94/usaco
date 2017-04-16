/*
ID: vincent30
LANG: JAVA
TASK: skidesign
*/

import java.io.*;
import java.util.*;

public class skidesign {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("skidesign.in")));
        int N = Integer.parseInt(in.readLine());

        // use HashMap as counter so we can do O(N + M) instead of O(NM)
        // where N = number of hills, M = max height
        HashMap<Integer, Integer> hills = new HashMap<Integer, Integer>();
        int min = Integer.MAX_VALUE, max = -1;
        for (int i=0; i<N; i++) {
            int height = Integer.parseInt(in.readLine());
            if (hills.containsKey(height))
                hills.put(height, hills.get(height) + 1);
            else
                hills.put(height, 1);

            min = Math.min(min, height);
            max = Math.max(max, height);
        }
        in.close();

        int best = Integer.MAX_VALUE;
        if (max - min <= 17)
            best = 0;

        for (int lower=min; lower<=max-17; lower++) {
            int upper = lower + 17;
            int cost = 0;
            for (int height : hills.keySet()) {
                int count = hills.get(height);
                if (height < lower)
                    cost += count * (lower-height)*(lower-height);
                else if (height > upper)
                    cost += count * (height-upper)*(height-upper);
            }

            System.out.printf("lower = %d upper = %d cost = %d\n", lower, upper, cost);
            best = Math.min(best, cost);
        }

        System.out.println(best);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("skidesign.out")));
        out.write("" + best);
        out.newLine();
        out.close();
    }
}
