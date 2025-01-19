package pairmatching.service;

import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Pair;

import java.util.Map;
import java.util.Set;

public class PairResetService {

    private final Map<Course, Map<Level, Set<Pair>>> matchingHistory;

    public PairResetService(Map<Course, Map<Level, Set<Pair>>> matchingHistory) {
        this.matchingHistory = matchingHistory;
    }

    public void resetPairMatchingData() {
        matchingHistory.clear();
        System.out.println("초기화 되었습니다.");
    }
}
