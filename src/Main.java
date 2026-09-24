import model.*;
import service.*;
import ioCSV.*;
import viewUI.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//      상속 테스트
        testSubjects();


        WrongAnswerManager manager = new WrongAnswerManager();
        CsvManager csv = new CsvManager();
        ReviewScheduler scheduler = new ReviewScheduler();

        WrongAnswer wa1 = new WrongAnswer(new MathSubject(), "도형의 방정식", "계산 실수", "x=6");
        manager.add(wa1);
        WrongAnswer wa2 = new WrongAnswer(new ProgrammingSubject(), "익명 함수", "개념 부족", "() -> {})");
        manager.add(wa2);
        WrongAnswer wa3 = new WrongAnswer(new EnglishSubject(), "본문 B 형식의 특정 단어", "단어 암기 부족", "words: Empty");
        manager.add(wa3);


        // 테스팅
        for (WrongAnswer wa : manager.getAll()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage());
        }

        // 1. 등록한 것 확인
        System.out.println("=== 등록 직후 ===");
        for (WrongAnswer wa : manager.getAll()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage() + ", 다음복습:" + wa.getNextReviewDate());
        }

        // 2. 복습 완료 한 번 시켜보기 (단계 변하는지)
        scheduler.completeReview(wa1);

        // 3. 저장
        csv.save(manager.getAll());

        // 4. 다시 불러오기
        System.out.println("\n=== 불러온 결과 ===");
        for (WrongAnswer wa : csv.load()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage() + ", 다음복습:" + wa.getNextReviewDate());
        }
    }


    //    상속-다형성 테스트
    public static void testSubjects() {
        Subject[] subjects = {
                new KoreanSubject(),
                new EnglishSubject(),
                new MathSubject(),
                new HistorySubject(),
                new ProgrammingSubject()
        };

        for (Subject s : subjects) {
            System.out.println(s.getName());
            for (String reasons : s.getWrongReasons()) {
                System.out.println("    - " + reasons);
            }
        }
    }

}
