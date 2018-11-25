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
    void BreakTies(Map<House, Set<Agent>> conflicts)
    {
        Set<House> houses = conflicts.keySet();
        for (House house:houses)
        {
            //TODO: Finish implementation

        }

    }
}
