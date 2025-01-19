package pairmatching.domain;

public enum Level {

    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private final String name;

    Level(String name) {
        this.name = name;
    }

    public static Level fromString(String name) {
        for (Level level : values()) {
            if (level.getName().equalsIgnoreCase(name)) {
                return level;
            }
        }
        throw new IllegalArgumentException("잘못된 enum 데이터 입니다.");
    }

    public String getName() {
        return name;
    }
}
