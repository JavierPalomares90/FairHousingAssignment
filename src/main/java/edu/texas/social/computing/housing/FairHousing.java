package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.InitializationStrategy;
import edu.texas.social.computing.housing.matching.MatchingStrategy;

public class FairHousing extends Housing{

    // TODO: What InitStrategy and MatchingStrategy are considered "fair"?
    public FairHousing(String filename, InitializationStrategy init, MatchingStrategy match) {
        super(filename, init, match);
    }
}
