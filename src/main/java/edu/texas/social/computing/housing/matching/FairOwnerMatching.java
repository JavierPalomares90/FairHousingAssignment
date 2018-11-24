package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

public class FairOwnerMatching implements MatchingStrategy {
    List<Agent> agents;
    List<House> houses;
    List<Set<House>> Js = new ArrayList<>();
    Map<House, Agent> owners;
    private List<List<House>> globalStates = new ArrayList<>();
    private int NUM_AGENTS;
    private int[] G;

    @Override
    public Map<House, Agent> findMatching(List<Agent> agents, Map<House, Agent> initialOwners, List<House> houses) {
        this.agents = agents;
        this.owners = initialOwners;
        this.houses = houses;
        initializeGlobalState();
        solve();
        return owners; // TODO: actually find owners.
    }

    private void initializeGlobalState() {
        NUM_AGENTS = agents.size();
        G = new int[NUM_AGENTS];

        //System.out.println(Js.toString());
        //System.out.println(agents.toString());
        for (int i = 0; i < NUM_AGENTS; i++) {
            G[i] = 0; // everyone will start with first choice
        }

        generateJs();
        genGlobalStates();
    }

    /**
     * This should update owners with correct matching.
     */
    private void solve() {
        Map<House, Set<Agent>> conflicts = matching();
        while (conflicts.size() > 0) {
            BreakTies(conflicts);
            conflicts = matching();
        }
        SetOwners();
    }

    private void BreakTies(Map<House, Set<Agent>> conflicts) {
        for (House h: conflicts.keySet()) {
            Set<Agent> ties = conflicts.get(h);
            Agent o = owners.get(h);

            Agent curOwner = null;
            int worstPref = -1;
            List<Agent> worstCurHouse = new ArrayList<>();
            for (Agent a: ties) {
                if (a.equals(o)) { // this agent currently owns house so they win
                    curOwner = a;
                    break;
                }
                // figure out index in prefs of agent's current house
                int pref = NUM_AGENTS - 1;
                for (int i=0; i<NUM_AGENTS; i++) {
                    House p = a.preferences.get(i);
                    if (a.equals(owners.get(p))) { // agent owns this house
                        pref = i;
                        break;
                    }
                }
                if (pref > worstPref) {
                    worstPref = pref;
                    worstCurHouse.clear();
                    worstCurHouse.add(a);
                } else if (pref == worstPref) { // still have tie
                    worstCurHouse.add(a);
                }
            }
            Agent winner = curOwner;
            if (curOwner == null) {
                Random rand = new Random();

                int index = rand.nextInt(worstCurHouse.size()) + 1;
                winner = worstCurHouse.get(index);
            }
            for (Agent a: ties) {
                if (! a.equals(winner)) {
                    G[a.index] = G[a.index] + 1;
                }
            }
        }
    }

    private void SetOwners() {
        for (int i = 0; i< NUM_AGENTS; i++) {
            Agent a = agents.get(i);
            House h = wish(i);
            owners.put(h, a);
        }
    }

   /* private Set<Conflict> B() {
        Set<Conflict> unmatched = matching();
        if (unmatched.size() > 0) {
            return unmatched;
        }
        return null;

        for (Agent F : agents) {
            if (geq(F)) {
                continue;
            }
            for (Set<House> J : Js) {
                //System.out.println(J.toString());
                if (submatching(J)) {
                    if (!eq(F, J)) {
                        return false;
                    }
                }
            }
        }
        return true;
    } */

    private House wish(int i) {
        return agents.get(i).preferences.get(G[i]);
    }

    private List<House> wish(Set<House> J) {
        ArrayList<House> wishArr = new ArrayList<>();
        for (House i : J) {
            wishArr.add(wish(i.index));
        }
        return wishArr;
    }

    private Map<House, Set<Agent>> matching() {
        Map<House, Set<Agent>> unmatched = new HashMap<>();
        /*for (int i=0; i<NUM_AGENTS; i++) {
            House h = houses.get(i);
            Set<Agent> conflicts = new HashSet<>();
            conflicts.add(owners.get(h)); // add current owner to conflicts
        } */
        for (int i = 0; i < NUM_AGENTS; i++) {
            House iWish = wish(i);

            for (int j = i + 1; j < NUM_AGENTS; j++) {
                if (iWish.equals(wish(j))) {
                    Set<Agent> conflicts;
                    if (unmatched.containsKey(iWish)) {
                        conflicts = unmatched.get(iWish);
                    } else {
                        conflicts = new HashSet<>();
                    }
                    conflicts.add(agents.get(i));
                    conflicts.add(agents.get(j));
                }
            }
        }
        return unmatched;
    }

    private boolean submatching(Set<House> J) {
        List<House> wishJ = wish(J);
        return isPermutation(J, wishJ);
    }

    private boolean isPermutation(Set<House> A, List<House> B) {
        Map<House, Integer> Amap = new HashMap<>();
        Map<House, Integer> Bmap = new HashMap<>();

        if (A.size() != B.size()) {
            return false;
        }

        for (House a : A) {
            Amap.put(a, 1);
        }
        for (House b : B) {
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
        for (int i = 0; i < (1 << NUM_AGENTS); i++) {
            Set<House> J = new HashSet<>();
            for (int j = 0; j < NUM_AGENTS; j++) {
                if ((i & (1 << j)) > 0) {
                    J.add(new House(j));
                }
            }
            Js.add(J);
        }
    }

    private boolean geq(Agent F) {
        for (int i = 0; i < NUM_AGENTS; i++) {
            if (F.preferences.get(i).index < G[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean eq(Agent F, Set<House> J) {
        for (House i : J) {
            if (F.preferences.get(i.index).index != G[i.index]) {
                return false;
            }
        }
        return true;
    }

    private void genGlobalStates() {
        // https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
        generateGlobalRec(new ArrayList<House>(), NUM_AGENTS);
    }

    private void generateGlobalRec(List<House> prefix, int k) {
        if (k == 0) {
            // What should be here?
            globalStates.add(prefix);
            return;
        }
        for (int i = 0; i < NUM_AGENTS; ++i) {
            List<House> newPrefix = new ArrayList<>(prefix);
            newPrefix.add(new House(i+1));
            generateGlobalRec(newPrefix, k - 1);
        }
    }
}
