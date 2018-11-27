package edu.texas.social.computing.housing.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Allocation {

	private Map<House,Agent> house2AgentMap;
	private Map<Agent,House> agent2HouseMap;

	public Allocation(Map<House, Agent> owners)
	{
		this.house2AgentMap = owners;
		// get a map of agents->house for convenience when computing the fairness score
		this.agent2HouseMap = getAgentHouseMap(owners);
	}

	public Map<House,Agent> getMapping()
	{
		return house2AgentMap;
	}

	private Map<Agent,House> getAgentHouseMap(Map<House,Agent> map)
	{
		Map<Agent,House> flipped = new HashMap<>();
		for(House key: map.keySet())
		{
			Agent value = map.get(key);
			flipped.put(value,key);
		}
		return flipped;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) return true;
		if ((o instanceof Allocation) == false) return false;
		if(o == null) return false;
		// Allocations are equals if they have they map the same owners

		Allocation a = (Allocation) o;
		Map<House,Agent> mapping = a.getMapping();
		return house2AgentMap.equals(mapping);
	}

	public double getFairnessScore()
	{
		int fairness = 0;
		Set<Agent> agents = agent2HouseMap.keySet();
		for(Agent agent: agents)
		{
			List<House> preferences = agent.preferences;
			House assignedHouse = agent2HouseMap.get(agent);

			double avgRank = getAvgRank();
			int preferenceIndex = getPreferenceIndex(assignedHouse,preferences);
			fairness += Math.abs(preferenceIndex - avgRank);
		}
		return fairness;
	}

	public double getAvgRank(){
		int numRanks = 0;
		double sumRanks = 0.0;
		for (House h: house2AgentMap.keySet()) {
			int rank = 0;
			Agent a = house2AgentMap.get(h);
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
		return avgRank;
	} // End PrintAvgRank()


	private int getPreferenceIndex(House house, List<House> houses)
	{
		for(int i= 0; i< houses.size(); i++)
		{
			if(house.equals(houses.get(i)))
			{
				return i;
			}
		}
		return houses.size();
	}
}
