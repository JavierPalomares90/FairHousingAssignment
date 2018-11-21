package edu.texas.social.computing.housing;


import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.matching.DefaultMatching;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.Map;

public class NoOwnersDefaultMatchingHousing extends Housing{

    NoOwnersDefaultMatchingHousing(String filename) {
        super(filename, new NoOwnersInitialization(), new DefaultMatching());
    }

}

