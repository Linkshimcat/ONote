package service;

import java.util.ArrayList;

import model.Subject;
import model.WrongAnswer;

public class WrongAnswerManager {
    private ArrayList<WrongAnswer> list = new ArrayList<>();

    public void add(WrongAnswer wa) { // 값 추가하기
        list.add(wa);
    }

    public ArrayList<WrongAnswer> getAll() { // 값 조회하기
        return list;
    }

    public void delete(int index) { // 값 삭제하기
        if(index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }

    public void update(int index, Subject subject, String content, String reason, String answer) { // 값 수정하기
        if(index >= 0 && index < list.size()) {
            WrongAnswer wa = list.get(index);
            wa.setSubject(subject);
            wa.setContent(content);
            wa.setReason(reason);
            wa.setAnswer(answer);
        }
    }

    // 과목 이름 찾기
    public ArrayList<WrongAnswer> searchBySubject(String subjectName) {
        ArrayList<WrongAnswer> result = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            WrongAnswer wa = list.get(i);
            if (wa.getSubject().getName().equals(subjectName)) {
               result.add(wa);
            }
        }
        return result;
    }

    // 키워드 검색
    public ArrayList<WrongAnswer> searchByKeyword(String keyword) {
        ArrayList<WrongAnswer> searchKeyword = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            WrongAnswer wa = list.get(i);
            if(wa.getContent().contains(keyword)) {
                searchKeyword.add(wa);
            }
        }
        return searchKeyword;
    }

    public ArrayList<WrongAnswer> search(String subjectName, String keyword) {
        if (subjectName.equals("전체")) {
            return searchByKeyword(keyword);
        }

        ArrayList<WrongAnswer> result = new ArrayList<>();
        for (WrongAnswer wa : searchBySubject(subjectName)) {
            if (wa.getContent().contains(keyword)) {
                result.add(wa);
            }
        }
        return result;
    }
}
