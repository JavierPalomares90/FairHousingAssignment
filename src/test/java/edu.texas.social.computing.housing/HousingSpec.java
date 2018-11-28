package edu.texas.social.computing.housing;
import org.junit.Test;
public class HousingSpec {

    @Test public void NoOwnersDefaultMatching() {
        NoOwnersDefaultMatchingHousing housing = new NoOwnersDefaultMatchingHousing("src/test/resources/test1.txt");

        assert housing.agents.isEmpty() == false;
        assert housing.solve().isEmpty() == false;
    }



}
