package ioCSV;

import model.*;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class CsvManager {
    private Subject createSubject(String name) {
        Subject subject;
        switch (name) {
            case "국어":
                subject = new KoreanSubject();
                break;
            case "수학":
                subject = new MathSubject();
                break;
            case "영어":
                subject = new EnglishSubject();
                break;
            case "한국사":
                subject = new HistorySubject();
                break;
            case "프로그래밍":
                subject = new ProgrammingSubject();
                break;
            default:
                subject = null;
        }
        return subject;
    }

    public void save(ArrayList<WrongAnswer> list) {
        try {
            FileWriter fw = new FileWriter("data.csv");

            for (int i = 0; i < list.size(); i++) {
                WrongAnswer wa = list.get(i);
                String line = wa.getSubject().getName() + ","
                        + wa.getContent() + ","
                        + wa.getReason() + ","
                        + wa.getAnswer() + ","
                        + wa.getCreatedDate() + ","
                        + wa.getStage() + ","
                        + wa.getNextReviewDate() + "\n";
                fw.write(line);
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("저장 실패:" + e.getMessage());
        }
    }

    public ArrayList<WrongAnswer> load() {
        ArrayList<WrongAnswer> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("data.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) continue;

                Subject subject = createSubject(parts[0]);
                if (subject == null) continue;

                try {
                    WrongAnswer wa = new WrongAnswer(subject, parts[1], parts[2], parts[3],
                            LocalDate.parse(parts[4]),
                            Integer.parseInt(parts[5]),
                            LocalDate.parse(parts[6]));
                    list.add(wa);
                } catch (Exception ex) {
                    System.out.println("형식이 잘못된 줄을 건너뜁니다: " + line);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println();
        }

        return list;
    }
}
