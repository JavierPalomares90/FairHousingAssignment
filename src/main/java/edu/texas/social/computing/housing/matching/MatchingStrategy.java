package edu.texas.social.computing.housing.matching;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.List;
import java.util.Map;

public interface MatchingStrategy {

    public Map<House, Agent> findMatching(List<Agent> agents, Map<House, Agent> initialOwners);

}
