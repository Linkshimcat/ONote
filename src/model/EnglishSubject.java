package model;

public class EnglishSubject extends Subject {
    public EnglishSubject() {
        super("영어");
    }
    @Override
    public String[] getWrongReasons() {
        return new String[] {"단어", "문법", "독해", "시간부족"};
    }
}
