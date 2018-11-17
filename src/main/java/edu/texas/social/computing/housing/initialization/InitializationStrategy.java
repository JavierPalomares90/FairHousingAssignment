package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.List;
import java.util.Map;

public interface InitializationStrategy {

    public Map<House, Agent> initializeOwners(int N);

    public List<Agent> initializeAgents(int N);
}
