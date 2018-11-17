package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoOwnersInitialization implements InitializationStrategy {
    int n;

    @Override
    public Map<House, Agent> initializeOwners(int N) {
        n = N;
        return new HashMap<>(); // TODO: add all the houses with no owners
    }

    @Override
    public List<Agent> initializeAgents(int N) {
        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            agents.add(new Agent(i)); // TODO: add the preferences for each agent
        }
        return agents;
    }

    private void generateAgents() {
        // https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
        if (n == 0) {
            return;
        }
        for (int i = 0; i < n; i++) {
            Agent newAgent = new Agent(i);

            ArrayList<House> preferences = new ArrayList<>();
            preferences.add(new House(i + 1));
//            generateGlobalRec(newPrefix, k - 1);
        }
    }
}
