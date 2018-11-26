package edu.texas.social.computing.housing;

import edu.texas.social.computing.housing.initialization.NoOwnersInitialization;
import edu.texas.social.computing.housing.matching.PoorestAgentWinsMatching;
import edu.texas.social.computing.housing.objects.Agent;

import java.util.*;

/**
 * Created by tiffanytillett on 11/25/18.
 */
public class AgentOrdering {

    public static void main(String[] args) {
        int n = 4;

        Housing housing = new FairHousing("src/main/resources/test1.txt", new NoOwnersInitialization(), new PoorestAgentWinsMatching());
        if(housing == null)return;

        List<Set<Agent>> Js = GenJs(n, housing);

        for (Set<Agent> subset: Js) {
            List<List<Agent>> orderings = GenOrderings(subset);
            PrintOrderings(subset, orderings);
        }
    }


    private static void PrintOrderings(Set<Agent> subset, List<List<Agent>> orderings) {
        System.out.println("For subset: " + subset.toString() + " we have the below possible orderings: ");
        for (List<Agent> ordering: orderings) {
            System.out.println(ordering.toString());
        }

    }

    private static List<List<Agent>> GenOrderings(Set<Agent> agentSet) {
        List<Agent> agents = new ArrayList<>();
        for (Agent a: agentSet) {
            agents.add(a);
        }
        int m = agents.size();
        List<List<Agent>> orderings = new ArrayList<>();
        // https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
        GenOrderRec(agents, orderings, new ArrayList<>(), new HashSet<>(), m, m);
        return orderings;
    }



    private static void GenOrderRec(List<Agent> agents, List<List<Agent>> orderings, List<Agent> prefix, Set<Agent> added, int m, int k) {
        if (k == 0) {
            orderings.add(prefix);
            return;
        }
        for (int i = 0; i < m; ++i) {
            List<Agent> newPrefix = new ArrayList<>(prefix);
            Agent a = agents.get(i);
            if (! added.contains(a)) {
                newPrefix.add(a);
                added.add(a);
                GenOrderRec(agents, orderings, newPrefix, added, m,k - 1);
                added.remove(a);
            }
        }
    }

    private static List<Set<Agent>> GenJs(int n, Housing housing) {
        // https://www.geeksforgeeks.org/finding-all-subsets-of-a-given-set-in-java/
        List<Set<Agent>> Js = new ArrayList<>();
        for (int i = 0; i < (1<<n); i++) {
            Set<Agent> J = new HashSet<>();
            for (int j = 0; j < n; j++)
                if ((i & (1 << j)) > 0) {
                    J.add(housing.agents.get(j));
                }
            Js.add(J);
        }
        return Js;
    }
}
