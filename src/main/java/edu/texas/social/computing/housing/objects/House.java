package edu.texas.social.computing.housing.objects;

public class House {
    public int index;
    public House(int n) {
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

}
