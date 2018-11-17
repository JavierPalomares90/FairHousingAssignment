package edu.texas.social.computing.housing;


import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.matching.DefaultMatching;

public class NoOwnersDefaultMatchingHousing extends Housing{

    NoOwnersDefaultMatchingHousing(int n) {
        super(n, new NoOwnersInitialization(), new DefaultMatching());
    }

    public static void main(String[] args) {
        int n = 3; // TODO: pass n as argument to program
        NoOwnersDefaultMatchingHousing housing = new NoOwnersDefaultMatchingHousing(n);
        housing.solve();
    }







}

