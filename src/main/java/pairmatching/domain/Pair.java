package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private final List<Crew> crews;

    public Pair() {
        this.crews = new ArrayList<>();
    }

    public void formPairsOfTwo(Crew fistCrew, Crew secondCrew) {
        crews.add(fistCrew);
        crews.add(secondCrew);
    }

    public void addThirdCrew(Crew crew) {
        crews.add(crew);
    }

    @Override
    public String toString() {
        if (crews.size() == 2) {
            return crews.get(0).name() + " : " + crews.get(1).name();
        }

        return crews.get(0).name() + " : " + crews.get(1).name() + " : " + crews.get(2).name();
    }
}
