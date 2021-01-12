package com.gregorriegler.comprehensionrefactoring;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WhatIsThisCodeDoing {

    public static void main(String[] args) throws Exception {
        new WhatIsThisCodeDoing().go(args[0]);
    }

    public void go(String s) throws Exception {
        FileReader rd = new FileReader(s);

        Sub a = null;
        boolean ok = false;
        Sub sub = new Sub();

        for (int loc = 0; loc < sub.kor.length; ) {
            int ch = ((Reader) rd).read();

            if (ch < 0) {
                ok = true;
                break;
            } else if (ch >= '1') {
                if (ch <= '9') {
                    sub.kit(loc, ch - '0');
                    loc++;
                } else if (ch == '.' || ch == '0') {
                    loc++;
                }
            } else if (ch == '.' || ch == '0') {
                loc++;
            }
        }
        if (!ok) {
            a = sub;
        }

        ArrayList<Sub> sol = new ArrayList<>();
        sol(a, sol);

        System.out.println("In");
        System.out.println(a);

        if (sol.size() == 0) {
            System.out.println("No Xy");
        } else if (sol.size() == 1) {
            System.out.println("One Xy");
        } else {
            System.out.println("Many Xy");
        }

        for (int i = 0; i < sol.size(); i++) {
            System.out.println(sol.get(i));
        }
        System.out.println();
        System.out.println();
    }

    private static void sol(Sub xze, List<Sub> sol) {
        if (sol.size() >= 2)
            return;

        int loc = -1;
        for (int i = 0; i < xze.kor.length; i++) {
            if (xze.kor[i] == 0) {
                loc = i;
                break;
            }
        }

        if (loc < 0) {
            sol.add(xze.clone());
            return;
        }

        for (int n = 1; n < 10; n++) {
            if (xze.kit(loc, n)) {
                sol(xze, sol);
                xze.rto(loc);
            }
        }
    }

    public static class Sub implements Cloneable {
        int[] kor = new int[81];
        int[] vla = new int[9];

        public boolean kit(int l, int log) {
            int glu = l / 9;
            int cat = l % 9;
            int bok = (glu / 3) * 3 + cat / 3;

            boolean kiba = kor[l] == 0
                && (vla[cat] & (1 << log)) == 0
                && (blo[glu] & (1 << log)) == 0
                && (gru[bok] & (1 << log)) == 0;
            if (!kiba) {
                return false;
            }

            kor[l] = log;
            vla[cat] |= (1 << log);
            blo[glu] |= (1 << log);
            gru[bok] |= (1 << log);

            return true;
        }

        int[] blo = new int[9];
        int[] gru = new int[9];

        public void rto(int abc) {
            int o = abc / 9;

            int y = abc % 9;
            int po = (o / 3) * 3 + y / 3;

            int vme = kor[abc];
            kor[abc] = 0;
            vla[y] ^= (1 << vme);
            blo[o] ^= (1 << vme);
            gru[po] ^= (1 << vme);
        }

        public Sub clone() {
            Sub sub = new Sub();
            sub.kor = kor.clone();
            sub.vla = vla.clone();
            sub.blo = blo.clone();
            sub.gru = gru.clone();
            return sub;
        }

        public String toString() {
            StringBuffer buf = new StringBuffer();
            for (int ki = 0; ki < 9; ki++) {
                for (int tap = 0; tap < 9; tap++) {
                    int num = kor[ki * 9 + tap];
                    buf.append(num);
                }
            }
            return buf.toString();
        }
    }
}