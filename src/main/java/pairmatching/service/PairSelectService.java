package pairmatching.service;

import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Pair;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static pairmatching.utils.InputValidationUtils.validateAndSplitInput;

public class PairSelectService {

    private final Scanner scanner;
    private final Map<Course, Map<Level, Set<Pair>>> matchingHistory;

    public PairSelectService(Scanner scanner, Map<Course, Map<Level, Set<Pair>>> matchingHistory) {
        this.scanner = scanner;
        this.matchingHistory = matchingHistory;
    }

    public void getPairMatchingResult() {
        String fetchingOption = """
                #############################################
                과정: 백엔드 | 프론트엔드
                미션:
                  - 레벨1: 자동차경주 | 로또 | 숫자야구게임
                  - 레벨2: 장바구니 | 결제 | 지하철노선도
                  - 레벨3:\s
                  - 레벨4: 성능개선 | 배포
                  - 레벨5:\s
                ############################################
                과정, 레벨, 미션을 선택하세요.
                ex) 백엔드, 레벨1, 자동차경주
                """;
        System.out.println(fetchingOption);

        String input = scanner.nextLine().trim();
        String[] inputData = validateAndSplitInput(input, 3);

        Course course = Course.fromString(inputData[0]);
        Level level = Level.fromString(inputData[1]);

        if (hasNoPairs(course, level)) {
            System.out.println("조건에 맞는 매칭 결과가 없습니다.");
            return;
        }

        printMatchingResult(course, level);
    }

    private boolean hasNoPairs(Course course, Level level) {
        return !matchingHistory.containsKey(course) || !matchingHistory.get(course).containsKey(level);
    }

    public void printMatchingResult(Course course, Level level) {
        System.out.println("페어 매칭 결과입니다.");
        matchingHistory.get(course).get(level).forEach(System.out::println);
    }
}
