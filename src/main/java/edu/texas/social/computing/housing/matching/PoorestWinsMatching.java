package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

/**
 * Matching implementation.
 * In this implementation, tie breakers are broken so that the agent whose current assignment is the worst wins the tie.
 * If there is still a tie after this, assign randomly
 */
public class PoorestWinsMatching implements MatchingStrategy
{
    private List<Agent> agents;
    private List<House> houses;
    private List<Set<House>> Js = new ArrayList<>();
    private Map<House, Agent> owners;
    private List<List<House>> globalStates = new ArrayList<>();
    private int NUM_AGENTS;
    private int[] GLOBAL_STATE;

    @Override
    public Map<House, Agent> findMatching(List<Agent> agents, Map<House, Agent> initialOwners, List<House> houses)
    {
        this.agents = agents;
        this.owners = initialOwners;
        this.houses = houses;
        NUM_AGENTS = agents.size();
        GLOBAL_STATE = new int[NUM_AGENTS];
        initializeGlobalState();
        solve();
        return owners; // TODO: actually find owners.
    }

    /**
     * Assign agents to a house
     */
    private void solve() {
        Map<House, Set<Agent>> conflicts = matching();
        while (conflicts.size() > 0) {
            BreakTies(conflicts);
            conflicts = matching();
        }
        SetOwners();
    }

    private void SetOwners() {
        for (int i = 0; i< NUM_AGENTS; i++) {
            Agent a = agents.get(i);
            House h = wish(i);
            owners.put(h, a);
        }
    }

    private House wish(int i) {
        return agents.get(i).preferences.get(GLOBAL_STATE[i]);
    }


    private void initializeGlobalState()
    {
        // Everyone starts out with their worst choice
        for (int i=0; i < NUM_AGENTS; i++)
        {
            GLOBAL_STATE[i] = NUM_AGENTS - 1;
        }
        generateJs();
        genGlobalStates();

    }

    /** Copied from FairOwnerMatching.java */
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

    private List<House> wish(Set<House> J) {
        ArrayList<House> wishArr = new ArrayList<>();
        for (House i : J) {
            wishArr.add(wish(i.index));
        }
        return wishArr;
    }

    private Map<House, Set<Agent>> matching() {
        Map<House, Set<Agent>> unmatched = new HashMap<>();
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
                    unmatched.put(iWish, conflicts);
                }
            }
        }
        return unmatched;
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
