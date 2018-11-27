package edu.texas.social.computing.housing.objects;

import java.util.List;

public class Agent {
    public List<House> preferences;
    public int index;
    public Agent(int n) {
        index = n;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof House)) return false;
        House other = (House) o;
        return this.index == other.index;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long temp1 = Double.doubleToLongBits(this.index);
        result = (result*PRIME) + (int)(temp1 ^ (temp1 >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "agent: " + index;
    }
}
