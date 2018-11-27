package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.initialization.OwnersInitialization;
import edu.texas.social.computing.housing.matching.DefaultMatching;
import edu.texas.social.computing.housing.matching.FairOwnerMatching;
import edu.texas.social.computing.housing.matching.PoorestAgentWinsMatching;
import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.Allocation;
import edu.texas.social.computing.housing.objects.House;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Assignment
{

    private static String DEFAULT_OPTION = "1";
    private static String FAIR_HOUSING_OPTION = "2";
    private static String POOREST_HOUSING_OPTION = "3";
    private static String AGENT_PRIORITY_HOUSING_OPTION = "4";
    private static Set<Allocation> allocations;


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

            allocations = new HashSet<>();
            housing = new FairHousing(filename, new NoOwnersInitialization(), new DefaultMatching(), null);
            List<List<Agent>> agentPriorities = AgentOrdering.GenOrderings(housing);
            Map<House, Agent> owners;
            // Loop through the list of all agent priority lists and solve the housing allocation problem given each one
            for (List<Agent> agentPrior: agentPriorities) {

                //housing = new FairHousing(filename, new NoOwnersInitialization(), new DefaultMatching(), agentPrior);
                housing.initialize();
                housing.setAgentPriority(agentPrior);
                owners = housing.solve();

                // Add the allocation to the list of allocations
                Allocation allocation = new Allocation(owners);
                allocations.add(allocation);

            }
            // Get the most fair allocation
            double minFairScore = Double.MAX_VALUE;
            Allocation best = null;
            for (Allocation a: allocations)
            {
                double fairness = a.getFairnessScore();
                if(fairness < minFairScore)
                {
                    minFairScore = fairness;
                    best = a;
                }
            }
            PrintOwners(best.getMapping());
            double avgRank = best.getAvgRank();
            System.out.println("Average rank of allocated Houses to Agents is " + avgRank+"\n");

            return;
        }
        if(housing == null)return;

        Map<House, Agent> owners = housing.solve();
        Allocation a = new Allocation(owners);
        PrintOwners(owners);

        double avgRank = a.getAvgRank();
        System.out.println("Average rank of allocated Houses to Agents is " + avgRank+"\n");
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
