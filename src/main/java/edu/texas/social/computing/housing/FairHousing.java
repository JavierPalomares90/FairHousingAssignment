package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;
import edu.texas.social.computing.housing.objects.Agent;

import java.util.List;

public class FairHousing extends Housing{

    // TODO: What InitStrategy and MatchingStrategy are considered "fair"?
    public FairHousing(String filename, InitializationStrategy init, MatchingStrategy match, List<Agent> agentPriority) {
        super(filename, init, match, agentPriority);
    }
}
