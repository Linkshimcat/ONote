package model;

public class KoreanSubject extends Subject {
    public KoreanSubject() {
        super("국어");
    }
    @Override
    public String[] getWrongReasons() {
        return new String[] {"지문이해", "어휘", "문법", "시간부족"};
    }
}
