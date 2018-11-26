package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.initialization.OwnersInitialization;
import edu.texas.social.computing.housing.matching.FairOwnerMatching;
import edu.texas.social.computing.housing.matching.PoorestAgentWinsMatching;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;
import edu.texas.social.computing.housing.AgentOrdering;

import java.util.Map;
import java.util.List;

public class Assignment
{

    private static String DEFAULT_OPTION = "1";
    private static String FAIR_HOUSING_OPTION = "2";
    private static String POOREST_HOUSING_OPTION = "3";
    private static String AGENT_PRIORITY_HOUSING_OPTION = "4";


    public static void main(String[] args)
    {
        if (args.length < 2) return;
        String filename = args[0];
        String housingSelection= args[1];

        List<Agent> DEFAULT_AGENT_PRIOR = null;

        Housing housing = null;
        if(FAIR_HOUSING_OPTION.equals(housingSelection))
        {
            housing = new FairHousing(filename, new OwnersInitialization(), new FairOwnerMatching(), DEFAULT_AGENT_PRIOR);
        }else if (DEFAULT_OPTION.equals(housingSelection))
        {
            housing = new NoOwnersDefaultMatchingHousing(filename);
        }else if (POOREST_HOUSING_OPTION.equals(housingSelection))
        {
            housing = new FairHousing(filename, new NoOwnersInitialization(), new PoorestAgentWinsMatching(), DEFAULT_AGENT_PRIOR);
        }else if (AGENT_PRIORITY_HOUSING_OPTION.equals(housingSelection))
        {

            List<List<Agent>> agentPriorities = AgentOrdering.GenOrderings(filename);
            // Loop through the list of all agent priority lists and solve the housing allocation problem given each one
            for (List<Agent> agentPrior: agentPriorities) {
                System.out.println(agentPrior.toString());

                housing = new FairHousing(filename, new NoOwnersInitialization(), new FairOwnerMatching(), agentPrior);
                Map<House, Agent> owners = housing.solve();

                // TODO: #3 - Save off maps of owners to some data structure
                PrintOwners(owners);
                PrintAvgRank(owners);

                System.out.println();
            }
            // TODO: #4 - Compare all allocations and determine which one is best/fair
            return;
        }
        if(housing == null)return;

        Map<House, Agent> owners = housing.solve();
        PrintOwners(owners);
        PrintAvgRank(owners);
    }

    private static void PrintOwners(Map<House, Agent> owners) {
        for (House h: owners.keySet()) {
            int hIndex = h.index + 1;
            Agent a = owners.get(h);
            int aIndex = a.index + 1;
            System.out.println("House " + hIndex + " is owned by agent " + aIndex);
        }
    }

    private static void PrintAvgRank(Map<House, Agent> owners) {
        int numRanks = 0;
        double sumRanks = 0.0;
        for (House h: owners.keySet()) {
            int rank = 0;
            Agent a = owners.get(h);
            for (int i = 0; i<a.preferences.size(); i++ ) {
                if (a.preferences.get(i).index == h.index) {
                    rank = i + 1;
                    numRanks++;
                    sumRanks += rank;
                    break;
                }
            }
            //System.out.println("Agent " + (a.index+1) + " owns House " + (h.index+1) + " with rank " + rank);
        }
        double avgRank = sumRanks / numRanks;
        System.out.println("Average rank of allocated Houses to Agents is " + avgRank);
    } // End PrintAvgRank()
}
