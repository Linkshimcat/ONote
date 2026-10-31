import model.*;
import service.*;
import ioCSV.*;
import viewUI.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*testSubjects();
        csvIo();*/
        new MainFrame();
    }

    public static void csvIo () {
        WrongAnswerManager manager = new WrongAnswerManager();
        CsvManager csv = new CsvManager();
        ReviewScheduler scheduler = new ReviewScheduler();

        for (WrongAnswer wa : manager.getAll()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage());
        }

        System.out.println("=== 등록 직후 ===");
        for (WrongAnswer wa : manager.getAll()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage() + ", 다음복습:" + wa.getNextReviewDate());
        }

//        scheduler.completeReview(wa1);

        csv.save(manager.getAll());

        System.out.println("\n=== 불러온 결과 ===");
        for (WrongAnswer wa : csv.load()) {
            System.out.println(wa.getSubject().getName() + ", " + wa.getContent() + ", 단계:" + wa.getStage() + ", 다음복습:" + wa.getNextReviewDate());
        }
    }

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
