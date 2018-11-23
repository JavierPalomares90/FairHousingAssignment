package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.matching.DefaultMatching;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.Map;

public class Assignment
{

    private static String FAIR_HOUSING_OPTION = "1";
    private static String DEFAULT_OPTION = "2";

    public static void main(String[] args)
    {
        if (args.length < 2) return;
        String filename = args[0];
        String housingSelection= args[1];

        Housing housing = null;
        if(FAIR_HOUSING_OPTION.equals(housingSelection))
        {
            housing = new FairHousing(filename, new NoOwnersInitialization(), new DefaultMatching());
        }else if (DEFAULT_OPTION.equals(housingSelection))
        {
            housing = new NoOwnersDefaultMatchingHousing(filename);
        }
        if(housing == null)return;

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
