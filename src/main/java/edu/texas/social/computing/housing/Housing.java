package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Objects that extend from Housing have an initialization and matching strategy to allow for a solution
 * to the housing allocation problem
 */
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
