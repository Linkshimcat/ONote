import model.*;

public class Main {
    public static void main(String[] args) {
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
