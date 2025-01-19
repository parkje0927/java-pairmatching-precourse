package pairmatching;

import pairmatching.service.PairMatchingManagement;

public class Application {

    public static void main(String[] args) {
        PairMatchingManagement pairMatchingManagement = new PairMatchingManagement();
        pairMatchingManagement.run();
    }
}
