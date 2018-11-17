package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Housing {
    private int n;
    InitializationStrategy initializationStrategy;
    MatchingStrategy matchingStrategy;

    List<Agent> agents;
    Map<House, Agent> owners = new HashMap<>();

    public Housing(int N, InitializationStrategy init, MatchingStrategy match) {
        n = N;
        initializationStrategy = init;
        matchingStrategy = match;
        initialize();
    }

    public void initialize() {
        agents = initializationStrategy.initializeAgents(n);
        owners = initializationStrategy.initializeOwners(n);
    }

    public Map<House, Agent> solve() {
       return this.matchingStrategy.findMatching(agents, owners);
    }
}
