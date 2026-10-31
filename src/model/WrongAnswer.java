package model;

import java.time.LocalDate; // 자바 표준 날짜 클래스 api

public class WrongAnswer {
    private Subject subject; // 과목
    private String content; // 문제 내용
    private String Qnumbers; // 문항 번호
    private String reason; // 틀린 이유
    private String answer; // 정답
    private LocalDate createdDate; // 생성 등록일
    private int stage; // 복습 단계 (0단계부터 4단계 까지)
    private LocalDate nextReviewDate; // 다음 복습일
    private boolean archived; // 보관함 여부 (망각곡선 주기를 모두 마치면 true)

    // 생성자
    public WrongAnswer(Subject subject, String Qnumbers, String content, String reason, String answer) {
        this.subject = subject;
        this.content = content;
        this.Qnumbers = Qnumbers;
        this.reason = reason;
        this.answer = answer;
        this.createdDate = LocalDate.now();
        this.stage = 0;
        this.nextReviewDate = this.createdDate.plusDays(1); //plusDays의 특징은 월말,연말을 알아서 처리해준다. (예: 1월 31일에 plusDays(1)을 해주면 2월 1일 이렇게 나옴)
    }

    // 오버로딩 : CSV 복원용 (보관 여부까지 받음)
    public WrongAnswer(Subject subject, String Qnumbers, String content, String reason, String answer, LocalDate createdDate, int stage, LocalDate nextReviewDate, boolean archived) {
        this.archived = archived;
        this.subject = subject;
        this.content = content;
        this.Qnumbers = Qnumbers;
        this.reason = reason;
        this.answer = answer;
        this.createdDate = createdDate;
        this.stage = stage;
        this.nextReviewDate = nextReviewDate;
    }

    public Subject getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
    public String getReason() {
        return reason;
    }
    public String getAnswer() {
        return answer;
    }
    public String getQNumbers() {
        return Qnumbers;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }
    public int getStage() {
        return stage;
    }
    public boolean isArchived() {
        return archived;
    }
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }
    public void setSubject(Subject subject) {this.subject = subject;}
    public void setContent(String content) {this.content = content;}
    public void setQNumbers(String Qnumbers) {this.Qnumbers = Qnumbers;}
    public void setReason(String reason) {this.reason = reason;}
    public void setAnswer(String answer) {this.answer = answer;}

}
