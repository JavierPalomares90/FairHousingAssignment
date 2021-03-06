package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Objects that extend from Housing have an initialization and matching strategy to allow for a solution
 * to the housing allocation problem
 */
abstract class Housing {
    private int n;
    private InitializationStrategy initializationStrategy;
    private MatchingStrategy matchingStrategy;

    List<Agent> agents;
    private Map<House, Agent> owners;
    private List<House> houses;
    private List<Agent> agentPriority;

    Housing(String filename, InitializationStrategy init, MatchingStrategy match, List<Agent> agentPrior) {
        n = ParseFile(filename); // will init agents as well since it's not dependent on strategy
        initializationStrategy = init;
        matchingStrategy = match;
        this.agentPriority = agentPrior;
        initialize();
    }

    void initialize() {
        owners = initializationStrategy.initializeOwners(n, agents);
    }

    Map<House, Agent> solve() {
        return this.matchingStrategy.findMatching(agents, owners, houses, agentPriority);
    }

    void setAgentPriority(List<Agent> agentPriority) {
        this.agentPriority = agentPriority;
    }

    private int ParseFile(String filename) {

        int n = 0;
        BufferedReader reader;
        String text;

        try {
            reader = new BufferedReader(new FileReader(filename));
            if ((text = reader.readLine()) != null) {
                n = Integer.parseInt(text);
            }

            // create Houses
            houses = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                House h = new House(i);
                houses.add(h);
            }

            agents = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((text = reader.readLine()) != null) {
                    ArrayList<House> line = new ArrayList<>();
                    String[] ints = text.split(" ");
                    for (int j = 0; j < n; j++) {
                        int index = Integer.parseInt(ints[j]);
                        line.add(houses.get(index - 1));
                    }
                    Agent a = new Agent(i);
                    a.preferences = line;
                    agents.add(a);
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return n;

    }
}
