package pairmatching.service;

import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PairMatchingManagement {

    private final Scanner scanner = new Scanner(System.in);

    private final PairMatchService pairMatchService;
    private final PairSelectService pairSelectService;
    private final PairResetService pairResetService;

    public PairMatchingManagement() {
        Map<Course, Map<Level, Set<Pair>>> matchingHistory = new HashMap<>();
        this.pairMatchService = new PairMatchService(scanner, matchingHistory);
        this.pairSelectService = new PairSelectService(scanner, matchingHistory);
        this.pairResetService = new PairResetService(matchingHistory);
    }

    public void run() {
        String programOption = """
                기능을 선택하세요.
                1. 페어 매칭
                2. 페어 조회
                3. 페어 초기화
                Q. 종료
                """;

        while (true) {
            System.out.println(programOption);
            System.out.print("입력 : ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> pairMatchService.matchPair();
                case "2" -> pairSelectService.getPairMatchingResult();
                case "3" -> pairResetService.resetPairMatchingData();
                case "Q", "q" -> {
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;
                }
                default -> throw new IllegalArgumentException("잘못된 입력값 입니다.");
            }
        }
    }
}
