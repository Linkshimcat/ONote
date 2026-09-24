package model;

import java.time.LocalDate; // 자바 표준 날짜 클래스 api

public class WrongAnswer {
    private Subject subject; // 과목
    private String content; // 문제 내용
    private String reason; // 틀린 이유
    private String answer; // 정답
    private LocalDate createdDate; // 생성 등록일
    private int stage; // 복습 단계 (0단계부터 4단계 까지)
    private LocalDate nextReviewDate; // 다음 복습일

    // 생성자
    public WrongAnswer(Subject subject, String content, String reason, String answer) {
        this.subject = subject;
        this.content = content;
        this.reason = reason;
        this.answer = answer;
        this.createdDate = LocalDate.now();
        this.stage = 0;
        this.nextReviewDate = this.createdDate.plusDays(1); //plusDays의 특징은 월말,연말을 알아서 처리해준다. (예: 1월 31일에 plusDays(1)을 해주면 2월 1일 이렇게 나옴)
    }

    // 오버로딩
    public WrongAnswer(Subject subject, String content, String reason, String answer, LocalDate createdDate, int stage, LocalDate nextReviewDate) {
        this.subject = subject;
        this.content = content;
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
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public int getStage() {
        return stage;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }
    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

}
