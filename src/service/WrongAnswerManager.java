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
}
