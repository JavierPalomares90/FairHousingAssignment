package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

public class FairOwnerMatching extends Matching
{

    @Override
    void BreakTies(Map<House, Set<Agent>> conflicts) {
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
            if (winner == null) {
                Random rand = new Random();

                int index = rand.nextInt(worstCurHouse.size());
                winner = worstCurHouse.get(index);
            }
            for (Agent a: ties) {
                if (! a.equals(winner)) {
                    G[a.index] = G[a.index] + 1;
                }
            }
        }
    }
}
