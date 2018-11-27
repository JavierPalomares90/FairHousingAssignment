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

	@Override
	public int hashCode()
	{
		return house2AgentMap.hashCode();
	}



	public int getFairnessScore()
	{
		int fairness = 0;
		Set<Agent> agents = agent2HouseMap.keySet();
		for(Agent agent: agents)
		{
			List<House> preferences = agent.preferences;
			House assignedHouse = agent2HouseMap.get(agent);

			int midPoint = preferences.size() / 2;
			int preferenceIndex = getPreferenceIndex(assignedHouse,preferences);
			fairness += Math.abs(preferenceIndex - midPoint);
		}
		return fairness;
	}

	private int getPreferenceIndex(House house, List<House> houses)
	{
		for(int i= 0; i< houses.size(); i++)
		{
			if(house.equals(houses))
			{
				return i;
			}
		}
		return houses.size();
	}
}
