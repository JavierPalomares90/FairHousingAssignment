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

    public static void main(String[] args) {
        if (args.length < 1) return;
        String filename = args[0];

        NoOwnersDefaultMatchingHousing housing = new NoOwnersDefaultMatchingHousing(filename);
        Map<House, Agent> owners = housing.solve();
        PrintOwners(owners);
    }

    private static void PrintOwners(Map<House, Agent> owners) {
        for (House h: owners.keySet()) {
            int hIndex = h.index + 1;
            Agent a = owners.get(h);
            int aIndex = a.index + 1;
            System.out.println("House " + hIndex + " is owned by agent " + aIndex);
        }
    }


}

