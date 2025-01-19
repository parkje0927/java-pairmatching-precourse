package pairmatching.domain;

public enum Course {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String name;

    Course(String name) {
        this.name = name;
    }

    public static Course fromString(String name) {
        for (Course course : values()) {
            if (course.getName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        throw new IllegalArgumentException("잘못된 enum 데이터 입니다.");
    }

    public String getName() {
        return name;
    }
}
