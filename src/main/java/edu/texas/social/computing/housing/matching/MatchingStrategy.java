package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.List;
import java.util.Map;

/**
 * Matching strategy provides a technique to solve for matching with the given list of agents and house owners
 */
public interface MatchingStrategy {

    Map<House, Agent> findMatching(List<Agent> agents, Map<House, Agent> initialOwners, List<House> houses, List<Agent> agentPriority);

}
