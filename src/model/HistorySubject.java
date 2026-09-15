package model;

public class HistorySubject extends Subject {
    public HistorySubject() {
        super("한국사");
    }
    @Override
    public String[] getWrongReasons() {
        return new String[] {"연대혼동", "암기부족", "사건이해부족", "시간부족"};
    }
}
