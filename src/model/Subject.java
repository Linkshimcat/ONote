package model;

public abstract class Subject {
    private String name;
    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String[] getWrongReasons();
}
