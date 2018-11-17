package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;

public class FairHousing extends Housing{

    // TODO: What InitStrategy and MatchingStrategy are considered "fair"?
    public FairHousing(int N, InitializationStrategy init, MatchingStrategy match) {
        super(N, init, match);
    }
}
