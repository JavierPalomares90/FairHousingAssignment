package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

public class DefaultMatching extends Matching
{
    @Override
    void BreakTies(Map<House, Set<Agent>> conflicts) {
        for (House h: conflicts.keySet()) {
            Set<Agent> ties = conflicts.get(h);

            // agent with lowest number wins
            int min = -1;
            for (Agent a: ties) {
                if (min == -1 || a.index < min) {
                    min = a.index;
                }
            }
            for (Agent a: ties) {
                if (a.index != min) {
                    G[a.index] = G[a.index] + 1;
                }
            }
        }
    }
}
