package pairmatching.service;

import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Level;
import pairmatching.domain.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static pairmatching.utils.FileReaderUtils.readMarkdownFile;
import static pairmatching.utils.InputValidationUtils.validateAndSplitInput;
import static pairmatching.utils.InputValidationUtils.validateInput;

public class PairMatchService {

    private final Scanner scanner;
    private final Map<Course, Map<Level, Set<Pair>>> matchingHistory;

    private final List<String> backendCrewNames;
    private final List<String> frontendCrewNames;
    private final List<Crew> crewList = new ArrayList<>();

    public PairMatchService(Scanner scanner, Map<Course, Map<Level, Set<Pair>>> matchingHistory) {
        this.scanner = scanner;
        this.matchingHistory = matchingHistory;
        this.backendCrewNames = readMarkdownFile("backend-crew.md");
        this.frontendCrewNames = readMarkdownFile("frontend-crew.md");
    }

    public void matchPair() {
        initCrewData();

        String matchingOption = """
                #############################################
                과정: 백엔드 | 프론트엔드
                미션:
                  - 레벨1: 자동차경주 | 로또 | 숫자야구게임
                  - 레벨2: 장바구니 | 결제 | 지하철노선도
                  - 레벨3:\s
                  - 레벨4: 성능개선 | 배포
                  - 레벨5:\s
                ############################################
                """;
        System.out.println(matchingOption);

        while (true) {
            System.out.println("과정, 레벨, 미션을 선택하세요.");
            System.out.println("ex) 백엔드, 레벨1, 자동차경주");
            System.out.println();

            String input = scanner.nextLine().trim();
            String[] inputData = validateAndSplitInput(input, 3);

            Course course = Course.fromString(inputData[0]);
            Level level = Level.fromString(inputData[1]);

            //매칭 정보가 있는지 여부 파악
            if (hasExistingPairs(course, level)) {
                System.out.println("매칭 정보가 있습니다. 다시 매칭하시겠습니까?");
                System.out.println("네 | 아니오");
                input = scanner.nextLine().trim();

                validateInput(input);

                if (input.equals("아니오")) {
                    continue;
                }
            }

            //매칭 정보가 있어도 다시 매칭하는 경우 or 매칭 정보가 없는 경우
            createPairs(course, level);
            printMatchingResult(course, level);
            break;
        }
    }

    private void initCrewData() {
        createAndAddCrew(Course.BACKEND, backendCrewNames);
        createAndAddCrew(Course.FRONTEND, frontendCrewNames);
    }

    private void createAndAddCrew(Course course, List<String> crewNames) {
        crewNames.forEach(crewName -> crewList.add(new Crew(course, crewName)));
    }

    private boolean hasExistingPairs(Course course, Level level) {
        return matchingHistory.containsKey(course) && matchingHistory.get(course).containsKey(level);
    }

    private void createPairs(Course course, Level level) {
        Set<Pair> previousPairs = getPreviousPairs(course, level);
        int attempt = 0;

        List<Crew> crewListForCourse = crewList.stream()
                .filter(crew -> crew.course().equals(course))
                .collect(Collectors.toList());

        while (attempt < 3) {
            attempt++;
            Collections.shuffle(crewListForCourse);

            Set<Pair> pairs = generateNewPairs(previousPairs, crewListForCourse);
            if (!pairs.isEmpty()) {
                matchingHistory.put(course, Map.of(level, pairs));
                return;
            }
        }

        throw new IllegalStateException("매칭 경우의 수가 부족합니다.");
    }

    private Set<Pair> getPreviousPairs(Course course, Level level) {
        return matchingHistory.getOrDefault(course, Map.of()).getOrDefault(level, new HashSet<>());
    }

    private Set<Pair> generateNewPairs(Set<Pair> previousPairs, List<Crew> crewListForCourse) {
        int startIndex = 0;
        Set<Pair> pairs = new HashSet<>();

        for (int i = startIndex; i < crewListForCourse.size(); i++) {
            Pair pair = new Pair();
            pair.formPairsOfTwo(crewListForCourse.get(i), crewListForCourse.get(i + 1));
            startIndex += 2;

            //3명씩 짝 짓는 경우
            if (i + 2 == crewListForCourse.size() - 1) {
                pair.addThirdCrew(crewListForCourse.get(i + 2));
                startIndex += 1;
            }

            if (!previousPairs.isEmpty() && previousPairs.contains(pair)) {
                return Collections.emptySet();
            }

            pairs.add(pair);

            if (startIndex == crewListForCourse.size()) {
                break;
            }
        }

        return pairs;
    }

    public void printMatchingResult(Course course, Level level) {
        System.out.println("페어 매칭 결과입니다.");
        matchingHistory.get(course).get(level).forEach(System.out::println);
        System.out.println();
    }
}
