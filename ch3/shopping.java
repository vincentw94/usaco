/*
ID: vincent30
LANG: JAVA
TASK: shopping
*/

import java.io.*;
import java.util.*;

public class shopping {

    static int[][][][][] sols = new int[6][6][6][6][6];
    static int[] target = new int[5];
    static ArrayList<int[]> offers;  // array of length 6: 5 counts, 1 price
    static HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();

    static int to(int id) {
        if (idToIndex.get(id) != null)
            return idToIndex.get(id);

        int ind = idToIndex.size();
        idToIndex.put(id, ind);
        return ind;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("shopping.in")));

        int numOffers = Integer.parseInt(in.readLine());
        offers = new ArrayList<int[]>(numOffers + 5);
        for (int i=0; i<numOffers; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int numProd = Integer.parseInt(st.nextToken());
            int[] offer = new int[6];
            for (int p=0; p<numProd; p++) {
                int prodId = Integer.parseInt(st.nextToken());
                int prodCount = Integer.parseInt(st.nextToken());
                offer[to(prodId)] = prodCount;
            }

            int price = Integer.parseInt(st.nextToken());
            offer[5] = price;
            offers.add(offer);
        }

        int toBuy = Integer.parseInt(in.readLine());
        for (int i=0; i<toBuy; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int prodId = Integer.parseInt(st.nextToken());
            int prodCount = Integer.parseInt(st.nextToken());
            int regularPrice = Integer.parseInt(st.nextToken());
            target[to(prodId)] = prodCount;

            // add regular price as offer
            int[] reg = new int[6];
            reg[to(prodId)] = 1;
            reg[5] = regularPrice;
            offers.add(reg);
        }
        in.close();

        for (int a=0; a<=target[0]; a++) {
        for (int b=0; b<=target[1]; b++) {
        for (int c=0; c<=target[2]; c++) {
        for (int d=0; d<=target[3]; d++) {
        for (int e=0; e<=target[4]; e++) {
            if (a+b+c+d+e == 0)
                continue;

            sols[a][b][c][d][e] = Integer.MAX_VALUE;
            for (int[] offer : offers) {
                sols[a][b][c][d][e] = Math.min(sols[a][b][c][d][e], apply(a,b,c,d,e, offer));
            }

            //System.out.printf("%d %d %d %d %d -> %d\n", a, b, c, d, e, sols[a][b][c][d][e]);
        }}}}}

        int ans = sols[target[0]][target[1]][target[2]][target[3]][target[4]];
        System.out.println(ans);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("shopping.out")));
        out.write(""+ans);
        out.newLine();
        out.close();
    }

    static int apply(int a, int b, int c, int d, int e, int[] offer) {
        a -= offer[0];
        b -= offer[1];
        c -= offer[2];
        d -= offer[3];
        e -= offer[4];

        if (a < 0 ||
            b < 0 ||
            c < 0 ||
            d < 0 ||
            e < 0)
            return Integer.MAX_VALUE;

        return sols[a][b][c][d][e] + offer[5];
    }
}
