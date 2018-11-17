package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DefaultMatching implements MatchingStrategy {
    List<Agent> agents;
    List<Set<Integer>> Js = new ArrayList<>();
    private int n;
    private int[][] pref;
    private int[] G;

    @Override
    public Map<House, Agent> findMatching(List<Agent> agents, Map<House, Agent> initialOwners) {
        this.agents = agents;
        pref = new int[n][n];
        G = new int[n];
        return initialOwners; // TODO: actually find owners.
    }

    private void initializeState() {
        generateJs();
        //System.out.println(Js.toString());
        //System.out.println(agents.toString());
        for (int i = 0; i < n; i++) {
            G[i] = 0;
        }

    }

    private void solve() {
        Stack<Agent> S = new Stack<>();
        for (int i = 0; i < agents.size(); i++) {
            S.push(new Agent(i)); //add all agents to stack
        }

        while (!S.empty()) {
            Agent agent = S.pop();

            // pick best house
            House house = agent.preferences.get(0);

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

    private boolean B() {
        if (!matching()) {
            return false;
        }

        for (Agent F : agents) {
            if (geq(F)) {
                continue;
            }
            for (Set<Integer> J : Js) {
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

    private int wish(int i) {
        return pref[i][G[i]];
    }

    private ArrayList<Integer> wish(Set<Integer> J) {
        ArrayList<Integer> wishArr = new ArrayList<>();
        for (int i : J) {
            wishArr.add(wish(i));
        }
        return wishArr;
    }

    private boolean matching() {
        for (int i = 0; i < n; i++) {
            int iWish = wish(i);
            for (int j = i + 1; j < n; j++) {
                if (iWish == wish(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean submatching(Set<Integer> J) {
        ArrayList<Integer> wishJ = wish(J);
        return isPermutation(J, wishJ);
    }

    private boolean isPermutation(Set<Integer> A, ArrayList<Integer> B) {
        Map<Integer, Integer> Amap = new HashMap<>();
        Map<Integer, Integer> Bmap = new HashMap<>();

        if (A.size() != B.size()) {
            return false;
        }

        for (int a : A) {
            Amap.put(a, 1);
        }
        for (int b : B) {
            if (Bmap.containsKey(b)) {
                //Bmap.put(b, Bmap.get(b) + 1);
                return false;
            } else {
                Bmap.put(b, 1);
            }
        }

        if (!Amap.keySet().equals(Bmap.keySet())) {
            return false;
        }

        /*for (int a: Amap.keySet()) {
            if (! Amap.get(a).equals(Bmap.get(a))) return false;
        } */
        return true;

    }

    private void generateJs() {
        // https://www.geeksforgeeks.org/finding-all-subsets-of-a-given-set-in-java/
        for (int i = 0; i < (1 << n); i++) {
            Set<Integer> J = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    J.add(j);
                }
            }
            Js.add(J);
        }
    }

    private boolean geq(Agent F) {
        for (int i = 0; i < n; i++) {
            if (F.preferences.get(i).index < G[i]) { // TODO: this is wrong
                return false;
            }
        }
        return true;
    }

    private boolean eq(Agent F, Set<Integer> J) {
        for (int i : J) {
            if (F.preferences.get(i).index != G[i]) { // TODO: this is wrong
                return false;
            }
        }
        return true;
    }
}
