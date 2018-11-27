package edu.texas.social.computing.housing;

import java.util.*;

/**
 * Created by tiffanytillett on 11/8/18.
 */
public class OldImpl {
    private static int n;
    private static int[][] pref;
    private static int[] G;

    private static ArrayList<ArrayList<Integer>> GlobalStates;
    private static ArrayList<Set<Integer>> Js;


    public static void main(String[] args) {
        n = 3;
        pref = new int[n][n];
        G = new int[n];
        GlobalStates = new ArrayList<>();
        Js = new ArrayList<>();

        Init();
        Solve();

    }


    private static void Init() {
        GenGlobalStates();
        GenJs();
        //System.out.println(Js.toString());
        //System.out.println(GlobalStates.toString());
        for (int i=0; i<n; i++) {
            G[i] = 0;
        }

    }

    private static void Solve() {
        Stack<Integer> S = new Stack<>();
        for (int i=0; i<n; i++) {
            S.push(i); //add all agents to stack
        }

        while (!S.empty()) {
            int agent = S.pop();

            // pick best house
            int index = G[agent];
            int house = pref[agent][index];

           /* int fiance = owners.get(choosee);
            if (fiance == -1) { // choosee is free
                owners.put(choosee, chooser); // chooser gets engaged to choosee
            } else {
                List<Integer> wPrefs = chooseePref.get(choosee);
                int prefCur = wPrefs.indexOf(fiance);
                int prefNew = wPrefs.indexOf(chooser);

                if (prefCur < prefNew) { // choosee prefers existing fiancee
                    Q.add(chooser);
                } else {
                    owners.put(choosee, chooser);
                    Q.add(fiance);
                }
            } */

        }
    }


    private static boolean B() {
        if (! matching()) return false;

        for (ArrayList<Integer> F: GlobalStates) {
            if (geq(F)) continue;
            for (Set<Integer> J: Js) {
                //System.out.println(J.toString());
                if (submatching(J)) {
                    if (!eq(F, J)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int wish (int i) {
        return pref[i][G[i]];
    }

    private static ArrayList<Integer> wish(Set<Integer> J) {
        ArrayList<Integer> wishArr = new ArrayList<>();
        for (int i: J) {
            wishArr.add(wish(i));
        }
        return wishArr;
    }

    private static boolean matching() {
        for (int i=0; i<n; i++) {
            int iWish = wish(i);
            for (int j=i+1; j<n; j++) {
                if (iWish == wish(j)) return false;
            }
        }
        return true;
    }

    private static boolean submatching(Set<Integer> J) {
        ArrayList<Integer> wishJ = wish(J);
        return isPermutation(J, wishJ);
    }

    private static boolean isPermutation(Set<Integer> A, ArrayList<Integer> B) {
        Map<Integer, Integer> Amap = new HashMap<>();
        Map<Integer, Integer> Bmap = new HashMap<>();

        if (A.size() != B.size()) return false;

        for (int a: A) {
            Amap.put(a, 1);
        }
        for (int b: B) {
            if (Bmap.containsKey(b)) {
                //Bmap.put(b, Bmap.get(b) + 1);
                return false;
            } else {
                Bmap.put(b, 1);
            }
        }

        if (! Amap.keySet().equals(Bmap.keySet())) return false;

        /*for (int a: Amap.keySet()) {
            if (! Amap.get(a).equals(Bmap.get(a))) return false;
        } */
        return true;

    }

    private static void GenGlobalStates() {
        // https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
        GenGlobalRec(new ArrayList<Integer>(), n);
    }

    private static void GenGlobalRec(ArrayList<Integer> prefix, int k) {
        if (k == 0) {
            GlobalStates.add(prefix);
            return;
        }
        for (int i = 0; i < n; ++i) {
            ArrayList<Integer> newPrefix = new ArrayList<>(prefix);
            newPrefix.add(i+1);
            GenGlobalRec(newPrefix, k - 1);
        }
    }

    private static void GenJs() {
        // https://www.geeksforgeeks.org/finding-all-subsets-of-a-given-set-in-java/
        for (int i = 0; i < (1<<n); i++) {
            Set<Integer> J = new HashSet<>();
            for (int j = 0; j < n; j++)
                if ((i & (1 << j)) > 0) {
                    J.add(j);
                }
            Js.add(J);
        }
    }

    private static boolean geq(ArrayList<Integer> F) {
        for (int i=0; i<n; i++) {
            if (F.get(i) < G[i]) return false;
        }
        return true;
    }

    private static boolean eq(ArrayList<Integer> F, Set<Integer> J) {
        for (int i: J) {
            if (F.get(i) != G[i]) return false;
        }
        return true;
    }
}

