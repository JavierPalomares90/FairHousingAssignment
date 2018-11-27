package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

public class DefaultMatching extends Matching
{
    @Override
    void BreakTies(Map<House, Set<Agent>> conflicts, List<Agent> agentPriority) {
        for (House h: conflicts.keySet()) {
            Set<Agent> ties = conflicts.get(h);

            int highPrior = -1;
            if (agentPriority == null) {
                // Default case: agent with lowest index wins
                for (Agent a : ties) {
                    if (highPrior == -1 || a.index < highPrior) {
                        highPrior = a.index;
                    }
                }
            } // end Default case
            else {
                // Use agent Priority to determine how to break ties
                // Agent with lowest index in agent priority list wins
                int min = -1;
                for (Agent a : ties) {
                    for (int i = 0; i<agentPriority.size(); i++ ) {
                        if (agentPriority.get(i).index == a.index) {
                            if ((min == -1) || (i < min)) {
                                min = i;
                                highPrior = a.index;
                            }
                        }
                    } // End loop through agent priority list
                } // End loop over tied agents
            }
            for (Agent a: ties) {
                if (a.index != highPrior) {
                    G[a.index] = G[a.index] + 1;
                }
            }
        }
    }
}
