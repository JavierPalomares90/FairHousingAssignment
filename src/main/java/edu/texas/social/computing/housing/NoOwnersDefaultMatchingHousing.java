package edu.texas.social.computing.housing;


public class HousingNoOwnersDefaultMatching extends Housing{

    HousingNoOwnersDefaultMatching(int n) {
        super(n, new NoOwnersInitialization(), new DefaultMatching());
    }

    public static void main(String[] args) {
        int n = 3; // TODO: pass n as argument to program
        HousingNoOwnersDefaultMatching housing = new HousingNoOwnersDefaultMatching(n);
        housing.solve();
    }







}

