package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initialization strategy where everyone gets their "middle" choice
 */
public class MiddleInitialization implements InitializationStrategy
{
    int n;
    @Override
    public Map<House, Agent> initializeOwners(int N, List<Agent> agents)
    {
        n = N;
        Map<House, Agent> owners = new HashMap<>();
        for (int i = 0; i < n; i++)
        {
            Agent agent = agents.get(i);
            List<House> preferences = agent.preferences;
            House middleChoice = preferences.get(preferences.size() / 2);
        }
        return owners;

    }
}
