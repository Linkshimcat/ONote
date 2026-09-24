package ioCSV;

import model.*;

import java.util.ArrayList;
// 쓰기용
import java.io.FileWriter; // 파일 쓰기
import java.io.IOException; // 파일 예외 처리
// 읽기용
import java.io.BufferedReader; // 파일 한 줄씩 읽기
import java.io.FileReader; // 파일 읽기
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
                subject = null; // 과목에서 없을경우 null 처리
        }
        return subject;
    }

    // 파일 저장하기
    public void save(ArrayList<WrongAnswer> list) {
        // 예외 처리 try catch
        try { // 발생한 오류 코드
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
            fw.close(); // 파일 닫기 (안 닫으면 내용이 파일에 안들어갈 수 있음.)
        } catch (IOException e) { // 예외 처리 (실패)가 발생 할 경우
            System.out.println("저장 실패:" + e.getMessage());
        }
    }

    // 파일 불러오기
    public ArrayList<WrongAnswer> load() {
        ArrayList<WrongAnswer> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("data.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length < 7) continue;
                Subject subject = createSubject(parts[0]);
                WrongAnswer wa = new WrongAnswer(subject, parts[1], parts[2], parts[3],
                        (LocalDate.parse(parts[4])),
                        (Integer.parseInt(parts[5])),
                        LocalDate.parse(parts[6]));
                list.add(wa);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("불러오기 실패: " + e.getMessage());
        }

        return list;
    }
}
