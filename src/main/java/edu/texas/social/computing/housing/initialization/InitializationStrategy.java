package edu.texas.social.computing.housing.initialization;

import edu.texas.social.computing.housing.objects.Agent;
import edu.texas.social.computing.housing.objects.House;

import java.util.List;
import java.util.Map;

/**
 * Initialization strategy provides a technique for initializing agents and owners
 */
public interface InitializationStrategy {

    Map<House, Agent> initializeOwners(int N, List<Agent> agents);

}
