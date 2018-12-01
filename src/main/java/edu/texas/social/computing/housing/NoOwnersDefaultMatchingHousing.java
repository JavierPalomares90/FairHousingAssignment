package edu.texas.social.computing.housing;


import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.matching.DefaultMatching;

class NoOwnersDefaultMatchingHousing extends Housing {

    NoOwnersDefaultMatchingHousing(String filename) {
        super(filename, new NoOwnersInitialization(), new DefaultMatching(), null);
    }

}

