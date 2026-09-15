package model;

public class MathSubject extends Subject {
    public MathSubject() {
        super("수학");
    }
    @Override
    public String[] getWrongReasons() {
        return new String[]{"계산실수", "개념오류", "시간부족", "문제이해부족"};
    }
}
