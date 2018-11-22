package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes all houses with no owners
 */
public class NoOwnersInitialization implements InitializationStrategy {
    int n;

    @Override
    public Map<House, Agent> initializeOwners(int N) {
        n = N;
        Map<House, Agent> owners = new HashMap<>();
        for (int i=0; i<n; i++) {
            House h = new House(i);
            owners.put(h, null);
        }
        return owners;
    }

    /*@Override
    public List<Agent> initializeAgents(int N, String file) {
        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            agents.add(new Agent(i)); // TODO: add the preferences for each agent
        }
        return agents;
    } */

}
