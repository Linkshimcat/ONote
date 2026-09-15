package model;

public class ProgrammingSubject  extends Subject  {
    public ProgrammingSubject() {
        super("프로그래밍");
    }
    @Override
    public String[] getWrongReasons() {
        return new String[] {"문법오류", "로직오류", "개념이해부족", "시간부족"};
    }

}
