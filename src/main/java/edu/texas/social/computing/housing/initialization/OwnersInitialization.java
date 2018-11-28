package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initializes all houses with no owners
 */
public class OwnersInitialization implements InitializationStrategy {
    int n;

    @Override
    public Map<House, Agent> initializeOwners(int N, List<Agent> agents) {
        n = N;
        Map<House, Agent> owners = new HashMap<>();
        for (int i=0; i<n; i++) {
            House h = new House(i);
            Agent a = agents.get(i);
            owners.put(h, a);
        }
        return owners;
    }
}
