package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.*;

/**
 * Matching implementation.
 * In this implementation, tie breakers are broken so that the agent whose current assignment is the worst wins the tie.
 * If there is still a tie after this, assign randomly
 */
public class PoorestAgentWinsMatching extends Matching
{
    // The agent with the worst current house wins the tie
    @Override
    void BreakTies(Map<House, Set<Agent>> conflicts, List<Agent> agentPriority)
    {
        Set<House> houses = conflicts.keySet();
        for (House house:houses)
        {
            Set<Agent> agents = conflicts.get(house);
            Agent poorestAgent =  getPoorestAgent(agents);
            // Poorest agent gets the house
            owners.put(house,poorestAgent);
            // Increase the global state for the agents that didn't win
            for (Agent a: agents) {
                if (! a.equals(poorestAgent)) {
                    G[a.index] = G[a.index] + 1;
                }
            }

        }
    }

    @Override
    House wish(int i)
    {
        Agent agent = agents.get(i);
        List<House> preferences = agent.preferences;
        int mid = preferences.size() / 2;
        int numProposals = G[i];
        // First proposal is mid, then mid+1,mid-1,mid+2,mid-2 and so on
        // Want the proposal to be as close to the middle
        int proposal = mid + (int)Math.pow(-1.0,numProposals) * ((numProposals+1) / 2);

        House house = preferences.get(proposal);
        return house;
    }

    // Find the agent with the worst current assignment.
    // Give the house to this agent
    private Agent getPoorestAgent(Set<Agent> agents)
    {
        Agent poorestAgent = null;
        int worstAssignment = -1;
        for(Agent agent: agents)
        {
            int currAssignment = getCurrentAssignment(agent);
            // Agent has the worst current assignment
            if(currAssignment > worstAssignment)
            {
                poorestAgent = agent;
                worstAssignment = currAssignment;
            }
        }

        return poorestAgent;
    }

    // Return the index of the agent's currently assigned house.
    // Larger index is a worse preference.
    // Return the number of agents if no house is currently assigned
    private int getCurrentAssignment(Agent agent)
    {
        List<House> preferences = agent.preferences;
        House currentHouse = null;
        for(House house: houses)
        {
            if(agent.equals(owners.get(house)))
            {
                currentHouse = house;
                break;
            }
        }
        // The agent has no current house
        if(currentHouse == null)
        {
            return NUM_AGENTS;
        }
        // Find out the preference index of the owner's currently assigned house
        for(int i = 0; i < preferences.size();i++)
        {
            if(currentHouse.equals(preferences.get(i)))
            {
                return i;
            }
        }

        return NUM_AGENTS;
    }
}
